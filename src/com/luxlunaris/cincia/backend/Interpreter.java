package com.luxlunaris.cincia.backend;

import java.util.ArrayList;
import java.util.List;
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
import com.luxlunaris.cincia.frontend.ast.expressions.unary.DestructuringExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.MinusExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.NegationExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
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


public class Interpreter extends AbstractTraversal<AbstractCinciaObject> {

	@FunctionalInterface
	interface Eval{
		AbstractCinciaObject eval(Ast ast, Enviro enviro);
	}

	@Override
	public AbstractCinciaObject evalInt(Int intex, Enviro enviro) {
		return CinciaObject.create(intex.getValue());
//		return new CinciaObject((int)intex.getValue());
	}

	@Override
	public AbstractCinciaObject evalFloat(Float floatex, Enviro enviro) {
//		return new CinciaObject((double)floatex.getValue());
		return CinciaObject.create(floatex.getValue());
	}

	@Override
	public AbstractCinciaObject evalStr(Str strex, Enviro enviro) {
//		return new CinciaObject((String)strex.getValue());
		return CinciaObject.create(strex.getValue());
	}

	@Override
	public AbstractCinciaObject evalBool(Bool boolex, Enviro enviro) {
//		return new CinciaObject((boolean)boolex.getValue());	
		return CinciaObject.create(boolex.getValue());
	}

	@Override
	public AbstractCinciaObject evalIdentifier(Identifier identex, Enviro enviro) {
		return enviro.get(identex.value);
	}

	@Override
	public AbstractCinciaObject evalTernaryExpression(TernaryExpression terex, Enviro enviro) {

		if(eval(terex.cond, enviro).__bool__()) {
			return eval(terex.thenExpression, enviro);
		}else {
			return eval(terex.elseExpression, enviro);
		}

	}

	@Override
	public AbstractCinciaObject evalIfStatement(IfStatement ifStatement, Enviro enviro) {

		if(eval(ifStatement.cond, enviro).__bool__()) {
			return eval(ifStatement.thenBlock, enviro);
		}else {
			return eval(ifStatement.elseBlock, enviro);
		}

	}

	@Override
	public AbstractCinciaObject evalMatchStatement(MatchStatement ifStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCinciaObject evalBreakStatement(BreakStatement breakStatement, Enviro enviro) {
		return null; //useless
	}

	@Override
	public AbstractCinciaObject evalContinueStatement(ContinueStatement continueStatement, Enviro enviro) {
		return null; //useless
	}

	@Override
	public AbstractCinciaObject evalForStatement(ForStatement forStatement, Enviro enviro) {

		Object iterable = eval(forStatement.iterable, enviro);

		//		for(Object x : iterable) {
		//			
		//		}

		return null;
	}

	@Override
	public AbstractCinciaObject evalWhileStatement(WhileStatement whileStatement, Enviro enviro) {

		while(eval(whileStatement.cond, enviro).__bool__()) {

			// run one iteration
			AbstractCinciaObject o = eval(whileStatement.block, enviro); 

			//check iteration exit value to determine what to do next
			if(o == null) {
				continue;
			}

			if(o.getValue().equals(Keywords.CONTINUE)) {
				continue;
			}

			if(o.getValue().equals(Keywords.BREAK)) {
				break;
			}

			//return statement
			if(o != null) { 
				return o;
			}

		}

		return null;
	}

	@Override
	public AbstractCinciaObject evalImportStatement(ImportStatement importStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCinciaObject evalCompoundStatement(CompoundStatement cS, Enviro enviro) {

		for (Statement s : cS.statements) {

			if(s instanceof ReturnStatement) {
				return eval( (ReturnStatement)s , enviro);
			}else if(s instanceof BreakStatement) {
//				return new CinciaObject(Keywords.BREAK);
				return new CinciaKeyword(Keywords.BREAK);
			}else if(s instanceof ContinueStatement) {
//				return new CinciaObject(Keywords.CONTINUE);
				return new CinciaKeyword(Keywords.CONTINUE);
			}else {
				eval(s, enviro);
			}

		}

		return null;
	}


	@Override
	public AbstractCinciaObject evalTryStatement(TryStatement tryStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCinciaObject evalThrowStatement(ThrowStatement throwStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCinciaObject evalDefaultStatement(DefaultStatement defaultStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCinciaObject evalReturnStatement(ReturnStatement returnStatement, Enviro enviro) {
		return eval(returnStatement.expression, enviro);
	}

	@Override
	public AbstractCinciaObject evalCaseStatement(CaseStatement caseStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCinciaObject evalMultiExpression(MultiExpression multex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCinciaObject evalRangeExpression(RangeExpression rangex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCinciaObject evalFunctionDeclaration(FunctionDeclaration fD, Enviro enviro) {
		enviro.set(fD.name.value, null, fD.signature);
		return null;
	}

	@Override
	public AbstractCinciaObject evalVariableDeclaration(VariableDeclaration vD, Enviro enviro) {
		enviro.set(vD.name.value, null, vD.type);
		return null;
	}

	@Override
	public AbstractCinciaObject evalMultiDeclaration(MultiDeclaration mD, Enviro enviro) {

		for(Declaration d : mD.declarations) {
			evalDeclaration(d, enviro);
		}

		return null;
	}


	@Override
	public AbstractCinciaObject evalMulExpression(MulExpression mulex, Enviro enviro) {

		AbstractCinciaObject left = eval(mulex.left, enviro);
		AbstractCinciaObject right = eval(mulex.right, enviro);

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
	public AbstractCinciaObject evalAddExpression(AddExpression addex, Enviro enviro) {

		AbstractCinciaObject left = eval(addex.left, enviro);
		AbstractCinciaObject right = eval(addex.right, enviro);

		if(addex.op == Operators.PLUS) {
			return left.__add__(right);
		}else if(addex.op == Operators.MINUS) {
			return left.__sub__(right);
		}

		throw new RuntimeException("Unknown addition operator!");

	}

	@Override
	public AbstractCinciaObject evalComparisonExpression(ComparisonExpression compex, Enviro enviro) {

		AbstractCinciaObject left = eval(compex.left, enviro);
		AbstractCinciaObject right = eval(compex.right, enviro);

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
	public AbstractCinciaObject evalOrExpression(OrExpression orex, Enviro enviro) {
		AbstractCinciaObject left = eval(orex.left, enviro);
		AbstractCinciaObject right = eval(orex.right, enviro);
		return left.__or__(right);
	}

	@Override
	public AbstractCinciaObject evalAndExpression(AndExpression andex, Enviro enviro) {
		AbstractCinciaObject left = eval(andex.left, enviro);
		AbstractCinciaObject right = eval(andex.right, enviro);
		return left.__and__(right);
	}

	@Override
	public AbstractCinciaObject evalAssignmentExpression(AssignmentExpression assex, Enviro enviro) {

		AbstractCinciaObject rval =  eval(assex.right, enviro);

		if(assex.left instanceof Identifier) {
			enviro.set(((Identifier)assex.left).value, rval, rval.type);
		}

		// if dot expression
		try {
			DotExpression dotex = (DotExpression)assex.left;
			AbstractCinciaObject dottable = eval(dotex.left, enviro);
			dottable.set(dotex.right.value, rval);
		}catch (ClassCastException e) {

		}

		//TODO: if indexed expresson  
		

		return rval;
	}

	@Override
	public AbstractCinciaObject evalClassExpression(ClassExpression classex, Enviro enviro) {

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
	public AbstractCinciaObject evalDictExpression(DictExpression dictex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCinciaObject evalDictComprehension(DictComprehension dictcompex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCinciaObject evalInterfaceExpression(InterfaceExpression interex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCinciaObject evalLambdaExpression(LambdaExpression lambdex, Enviro enviro) {

		// TODO: check if env is class, in that return a method.

		return new CinciaFunction(lambdex, this::eval);
	}

	@Override
	public AbstractCinciaObject evalListComprehension(ListComprehension listcompex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCinciaObject evalListExpression(ListExpression listex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCinciaObject evalCalledExpression(CalledExpression callex, Enviro enviro) {


		// TODO: do some of this stuff in evalMultiExpression ! 
		// get arguments 
		List<AbstractCinciaObject> args = new ArrayList<AbstractCinciaObject>();

		try {
			MultiExpression mE = (MultiExpression)callex.args;
			args.addAll(mE.expressions.stream().map(e-> eval(e, enviro)).collect(Collectors.toList()) );
		}catch (ClassCastException e) {
			args.add(eval(callex.args, enviro));
		}

		// get called expression
		AbstractCinciaObject f = eval(callex.callable, enviro);


		// if class, call constructor and return reference to new object
		try {
			CinciaClass c = (CinciaClass)f;
			return c.constructor(args);
		}catch (ClassCastException e) {

		}

		// if method, call on parent object's ORIGINAL env
		try {
			CinciaMethod cm = (CinciaMethod)f;
			return cm.run(args);
		}catch (ClassCastException e) {

		}

		// else it's a top level function, call on COPY of whatever environment was passed in		
		try {
			CinciaFunction l = (CinciaFunction)f;
			return l.run(args, enviro.newChild());
		}catch (ClassCastException e) {

		}

		throw new RuntimeException("Unsupported callable type!");
	}

	@Override
	public AbstractCinciaObject evalDotExpression(DotExpression dotex, Enviro enviro) {
		AbstractCinciaObject o = eval(dotex.left, enviro);
		return o.get(dotex.right.value);		
	}

	@Override
	public AbstractCinciaObject evalIndexedExpression(IndexedExpression indexex, Enviro enviro) {

		AbstractCinciaObject o = eval(indexex.indexable, enviro);
		AbstractCinciaObject index = eval(indexex.index , enviro);
		
		
		if( index instanceof CinciaString ) {
			return o.get((String)index.getValue());
		}
		
		// TODO: check if index is int or iterable (fancy index)

		throw new RuntimeException("Unsupported index type!");
	}

	@Override
	public AbstractCinciaObject evalReassignmentExpression(ReassignmentExpression reassex, Enviro enviro) {
		// TODO Auto-generated method stub
		//		CinciaObject o = eval(reassex.left, enviro);

		return null;
	}

	@Override
	public AbstractCinciaObject evalBracketedExpression(BracketedExpression brackex, Enviro enviro) {
		return eval(brackex.expression, enviro);
	}

	@Override
	public AbstractCinciaObject evalDestructuringExpression(DestructuringExpression destex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCinciaObject evalMinusExpression(MinusExpression minex, Enviro enviro) {
		return eval(minex.arg, enviro).__neg__();
	}

	@Override
	public AbstractCinciaObject evalNegationExpression(NegationExpression negex, Enviro enviro) {
		return eval(negex.arg, enviro).__not__();
	}


}
