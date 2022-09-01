package com.luxlunaris.cincia.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.declarations.FunctionDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.RangeExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.TernaryExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AddExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AndExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AssignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.ComparisonExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.MulExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.OrExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ClassExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.DictComprehension;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.DictExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.InterfaceExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ListComprehension;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ListExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.CalledExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.DotExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.IndexedExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.ReassignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.primary.BracketedExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.DictType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.ListType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.UnionType;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.DestructuringExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.MinusExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.NegationExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.ast.interfaces.Constant;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.ast.statements.ImportStatement;
import com.luxlunaris.cincia.frontend.ast.statements.exception.ThrowStatement;
import com.luxlunaris.cincia.frontend.ast.statements.exception.TryStatement;
import com.luxlunaris.cincia.frontend.ast.statements.iteration.ForStatement;
import com.luxlunaris.cincia.frontend.ast.statements.iteration.WhileStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.BreakStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.ContinueStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.ReturnStatement;
import com.luxlunaris.cincia.frontend.ast.statements.labelled.CaseStatement;
import com.luxlunaris.cincia.frontend.ast.statements.labelled.DefaultStatement;
import com.luxlunaris.cincia.frontend.ast.statements.selection.IfStatement;
import com.luxlunaris.cincia.frontend.ast.statements.selection.MatchStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Bool;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Float;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Int;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Str;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;


public class Interpreter extends AbstractTraversal<CinciaObject> {

	@FunctionalInterface
	interface Eval{
		CinciaObject eval(Ast ast, Enviro enviro);
	}

	@Override
	public CinciaObject evalInt(Int intex, Enviro enviro) {
		return new CinciaObject((int)intex.getValue());
	}

	@Override
	public CinciaObject evalFloat(Float floatex, Enviro enviro) {
		return new CinciaObject((double)floatex.getValue());
	}

	@Override
	public CinciaObject evalStr(Str strex, Enviro enviro) {
		return new CinciaObject((String)strex.getValue());
	}

	@Override
	public CinciaObject evalBool(Bool boolex, Enviro enviro) {
		return new CinciaObject((boolean)boolex.getValue());		
	}

	@Override
	public CinciaObject evalIdentifier(Identifier identex, Enviro enviro) {
		return enviro.get(identex.value);
	}

	@Override
	public CinciaObject evalTernaryExpression(TernaryExpression terex, Enviro enviro) {

		if(eval(terex.cond, enviro).__bool__()) {
			return eval(terex.thenExpression, enviro);
		}else {
			return eval(terex.elseExpression, enviro);
		}

	}

	@Override
	public CinciaObject evalIfStatement(IfStatement ifStatement, Enviro enviro) {

		if(eval(ifStatement.cond, enviro).__bool__()) {
			return eval(ifStatement.thenBlock, enviro);
		}else {
			return eval(ifStatement.elseBlock, enviro);
		}

	}

	@Override
	public CinciaObject evalMatchStatement(MatchStatement ifStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalBreakStatement(BreakStatement breakStatement, Enviro enviro) {
		return null; //useless
	}

	@Override
	public CinciaObject evalContinueStatement(ContinueStatement continueStatement, Enviro enviro) {
		return null; //useless
	}

	@Override
	public CinciaObject evalForStatement(ForStatement forStatement, Enviro enviro) {

		Object iterable = eval(forStatement.iterable, enviro);

		//		for(Object x : iterable) {
		//			
		//		}

		return null;
	}

	@Override
	public CinciaObject evalWhileStatement(WhileStatement whileStatement, Enviro enviro) {

		while(eval(whileStatement.cond, enviro).__bool__()) {

			Object o = eval(whileStatement.block, enviro);

			if(o == null) {
				continue;
			}

			if(o.equals(Keywords.CONTINUE)) {
				continue;
			}

			if(o.equals(Keywords.BREAK)) {
				break;
			}

		}

		return null;
	}

	@Override
	public CinciaObject evalImportStatement(ImportStatement importStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalCompoundStatement(CompoundStatement cS, Enviro enviro) {

		for (Statement s : cS.statements) {

			if(s instanceof ReturnStatement) {
				return eval( (ReturnStatement)s , enviro);

			}else if(s instanceof BreakStatement) {
				return Keywords.BREAK;

			}else if(s instanceof ContinueStatement) {
				return Keywords.CONTINUE;

			}else {
				eval(s, enviro);

			}

		}

		return null;
	}


	@Override
	public CinciaObject evalTryStatement(TryStatement tryStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalThrowStatement(ThrowStatement throwStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalDefaultStatement(DefaultStatement defaultStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalReturnStatement(ReturnStatement returnStatement, Enviro enviro) {
		return eval(returnStatement.expression, enviro);
	}

	@Override
	public CinciaObject evalCaseStatement(CaseStatement caseStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalMultiExpression(MultiExpression multex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalRangeExpression(RangeExpression rangex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalFunctionDeclaration(FunctionDeclaration fD, Enviro enviro) {
		enviro.set(fD.name.value, null, fD.signature);
		return null;
	}

	@Override
	public CinciaObject evalVariableDeclaration(VariableDeclaration vD, Enviro enviro) {
		enviro.set(vD.name.value, null, vD.type);
		return null;
	}

	@Override
	public CinciaObject evalMultiDeclaration(MultiDeclaration mD, Enviro enviro) {

		for(Declaration d : mD.declarations) {
			evalDeclaration(d, enviro);
		}

		return null;
	}


	@Override
	public CinciaObject evalMulExpression(MulExpression mulex, Enviro enviro) {

		CinciaObject left = eval(mulex.left, enviro);
		CinciaObject right = eval(mulex.right, enviro);

		if(mulex.op == Operators.ASTERISK) {
			return left.__mul__(right);
		}else if(mulex.op == Operators.DIV) {
			return left.__div__(right);
		}else if(mulex.op == Operators.MOD) {
			return left.__mod__(right);
		}

		throw new RuntimeException("Unknown multiplication operator!");
	}

	@Override
	public CinciaObject evalAddExpression(AddExpression addex, Enviro enviro) {

		CinciaObject left = eval(addex.left, enviro);
		CinciaObject right = eval(addex.right, enviro);

		if(addex.op == Operators.PLUS) {
			return left.__add__(right);
		}else if(addex.op == Operators.MINUS) {
			return left.__sub__(right);
		}

		throw new RuntimeException("Unknown addition operator!");

	}

	@Override
	public CinciaObject evalComparisonExpression(ComparisonExpression compex, Enviro enviro) {

		CinciaObject left = eval(compex.left, enviro);
		CinciaObject right = eval(compex.right, enviro);

		switch (compex.op) {

		case COMPARE:
			return left.__eq__(right);
		case NE:
			return left.__ne__(right);
		case LT:
			return left.__lt__(right);
		case GT:
			return left.__gt__(right);
		case LTE:
			return left.__lte__(right);
		case GTE:
			return left.__gte__(right);
		default:
			throw new RuntimeException("Unknown comparison operator!");
		}

	}

	@Override
	public CinciaObject evalOrExpression(OrExpression orex, Enviro enviro) {
		CinciaObject left = eval(orex.left, enviro);
		CinciaObject right = eval(orex.right, enviro);
		return left.__or__(right);
	}

	@Override
	public CinciaObject evalAndExpression(AndExpression andex, Enviro enviro) {
		CinciaObject left = eval(andex.left, enviro);
		CinciaObject right = eval(andex.right, enviro);
		return left.__and__(right);
	}

	@Override
	public CinciaObject evalAssignmentExpression(AssignmentExpression assex, Enviro enviro) {

		//		eval(assex.left, enviro);
		//		eval(assex.right, enviro);

		return null;
	}

	@Override
	public CinciaObject evalClassExpression(ClassExpression classex, Enviro enviro) {

		CinciaClass c = new CinciaClass();

		for(Declaration dec : classex.declarations) {	
			eval(dec, c.getEnviro());
		}

		for(AssignmentExpression assign : classex.assignments) {
			eval(assign, c.getEnviro());
		}

		return c;
	}

	@Override
	public CinciaObject evalDictExpression(DictExpression dictex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalDictComprehension(DictComprehension dictcompex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalInterfaceExpression(InterfaceExpression interex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalLambdaExpression(LambdaExpression lambdex, Enviro enviro) {

		// TODO: check if env is class, in that return a method.

		return new CinciaFunction(lambdex, this::eval);
	}

	@Override
	public CinciaObject evalListComprehension(ListComprehension listcompex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalListExpression(ListExpression listex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalCalledExpression(CalledExpression callex, Enviro enviro) {


		// TODO: do some of this stuff in evalMultiExpression ! 
		List<CinciaObject> args = new ArrayList<CinciaObject>();

		try {
			MultiExpression mE = (MultiExpression)callex.args;
			args.addAll(mE.expressions.stream().map(e-> eval(e, enviro)).collect(Collectors.toList()) );
		}catch (ClassCastException e) {
			args.add(eval(callex.args, enviro));
		}


		// get function 
		CinciaFunction f = (CinciaFunction)eval(callex.callable, enviro);

		// if method, call on parent object's ORIGINAL env
		try {
			CinciaMethod cm = (CinciaMethod)f;
			return cm.run(args);
		}catch (ClassCastException e) {

		}

		// else it's a top level function, call on COPY of whatever environment was passed in
		return f.run(args, enviro.newChild());

	}

	@Override
	public CinciaObject evalDotExpression(DotExpression dotex, Enviro enviro) {
		CinciaObject o = eval(dotex.left, enviro);
		return o.get(dotex.right.value);		
	}

	@Override
	public CinciaObject evalIndexedExpression(IndexedExpression indexex, Enviro enviro) {

		CinciaObject o = eval(indexex.indexable, enviro);
		CinciaObject index = eval(indexex.index , enviro);

		// TODO: check if index is int or iterable (fancy index)

		if( index.value instanceof String ) {
			return o.get((String)index.value);
		}

		throw new RuntimeException("Unsupported index type!");
	}

	@Override
	public CinciaObject evalReassignmentExpression(ReassignmentExpression reassex, Enviro enviro) {
		// TODO Auto-generated method stub
		//		CinciaObject o = eval(reassex.left, enviro);

		return null;
	}

	@Override
	public CinciaObject evalBracketedExpression(BracketedExpression brackex, Enviro enviro) {
		return eval(brackex.expression, enviro);
	}

	@Override
	public CinciaObject evalDestructuringExpression(DestructuringExpression destex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalMinusExpression(MinusExpression minex, Enviro enviro) {
		return eval(minex.arg, enviro).__neg__();
	}

	@Override
	public CinciaObject evalNegationExpression(NegationExpression negex, Enviro enviro) {
		return eval(negex.arg, enviro).__not__();
	}


}
