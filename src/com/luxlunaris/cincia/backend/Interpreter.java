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
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;


public class Interpreter extends AbstractTraversal<CinciaObject> {

	@FunctionalInterface
	interface Eval{
		CinciaObject eval(Ast ast, Enviro enviro);
	}

	@Override
	public CinciaObject evalInt(Int intex, Enviro enviro) {
		return CinciaObject.create(intex.getValue());
	}

	@Override
	public CinciaObject evalFloat(Float floatex, Enviro enviro) {
		return CinciaObject.create(floatex.getValue());
	}

	@Override
	public CinciaObject evalStr(Str strex, Enviro enviro) {
		return CinciaObject.create(strex.getValue());
	}

	@Override
	public CinciaObject evalBool(Bool boolex, Enviro enviro) {
		return CinciaObject.create(boolex.getValue());
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

			// run one iteration
			CinciaObject o = eval(whileStatement.block, enviro); 

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
				return new CinciaKeyword(Keywords.BREAK);
			}else if(s instanceof ContinueStatement) {
				return new CinciaKeyword(Keywords.CONTINUE);
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
		enviro.set(fD.name.value, null, fD.signature); //TODO: check if already exists!!
		return null;
	}

	@Override
	public CinciaObject evalVariableDeclaration(VariableDeclaration vD, Enviro enviro) {
		enviro.set(vD.name.value, null, vD.type); //TODO: check if already exists!!
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
		
//		System.out.println(mulex);

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

		
		
		CinciaObject rval =  eval(assex.right, enviro);

		if(assex.left instanceof Identifier) {
			enviro.set(((Identifier)assex.left).value, rval, rval.getType());
		}

		// if dot expression
		try {
			DotExpression dotex = (DotExpression)assex.left;
			CinciaObject dottable = eval(dotex.left, enviro);
			dottable.set(dotex.right.value, rval);
		}catch (ClassCastException e) {

		}

		//TODO: if indexed expresson  
		

		return rval;
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

		// Check if env is class, in that case return a method.
		CinciaBool b = (CinciaBool)enviro.get(CinciaClass.IS_CLASS);
		if(b!=null && b.__bool__()) {
			return new CinciaMethod(lambdex, this::eval);
		}
		
		// Check if function is pure, in that case return a pure function.
		if(lambdex.modifiers.contains(Modifiers.PURE)) {
			return new PureCinciaFunction(lambdex, this::eval);
		}

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
		// get arguments 
		List<CinciaObject> args = new ArrayList<CinciaObject>();

		if(callex.args!=null) {
			try {
				MultiExpression mE = (MultiExpression)callex.args;
				args.addAll(mE.expressions.stream().map(e-> eval(e, enviro)).collect(Collectors.toList()) );
			}catch (ClassCastException e) {
				args.add(eval(callex.args, enviro));
			}
		}

		// get called expression
		CinciaObject f = eval(callex.callable, enviro);


		// if class, call constructor and return reference to new object
		try {
			CinciaClass c = (CinciaClass)f;
			return c.constructor(args);
		}catch (ClassCastException e) {
//			e.printStackTrace();
//			System.exit(1); //TODO: remove
		}

		// if method, call on parent object's ORIGINAL env
		try {
			CinciaMethod cm = (CinciaMethod)f;
			return cm.run(args);
		}catch (ClassCastException e) {

		}
		
		// if pure function, run with args as ONLY input, don't let it even read the global env.
		try {
			PureCinciaFunction cm = (PureCinciaFunction)f;
			return cm.run(args);
		}catch (ClassCastException e) {

		}

		// else it's a regular top level function, call on COPY of whatever environment was passed in		
		try {
			CinciaFunction l = (CinciaFunction)f;
			return l.run(args, enviro.newChild());
		}catch (ClassCastException e) {

		}
		
		
		throw new RuntimeException("Unsupported callable type!");
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
		
		
		if( index instanceof CinciaString ) {
			return o.get((String)index.getValue());
		}
		
		// TODO: check if index is int or iterable (fancy index)

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
