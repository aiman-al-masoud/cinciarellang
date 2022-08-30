package com.luxlunaris.cincia.backend;

import java.util.Stack;

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
import com.luxlunaris.cincia.frontend.ast.interfaces.Constant;
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

public class Interpreter extends AbstractTraversal {

	Stack<Enviro> scopes;

	public Interpreter() {
		scopes = new Stack<Enviro>();
		scopes.push(new Enviro(null));
	}

	public void enterEnv(Enviro env) {
		scopes.push(env);
	}

	public void exitEnv() {

		if(scopes.size() > 1) {
			scopes.pop();
		}
	}

	public Enviro getEnv() {
		return scopes.peek();
	}

	@Override
	public Object evalInt(Int intex, Enviro enviro) {
		return intex.getValue();
	}

	@Override
	public Object evalFloat(Float floatex, Enviro enviro) {
		return floatex.getValue();
	}

	@Override
	public Object evalStr(Str strex, Enviro enviro) {
		return strex.getValue();
	}

	@Override
	public Object evalBool(Bool boolex, Enviro enviro) {
		return boolex.getValue();		
	}
	
	@Override
	public Object evalIdentifier(Identifier identex, Enviro enviro) {
		return enviro.get(identex.value);
	}


	@Override
	public Object evalTernaryExpression(TernaryExpression terex, Enviro enviro) {

		if((boolean)eval(terex.cond, enviro)) {
			return eval(terex.thenExpression, enviro);
		}else {
			return eval(terex.elseExpression, enviro);
		}

	}

	@Override
	public Object evalIfStatement(IfStatement ifStatement, Enviro enviro) {

		if((boolean)eval(ifStatement.cond, enviro)) {
			return eval(ifStatement.thenBlock, enviro);
		}else {
			return eval(ifStatement.elseBlock, enviro);
		}

	}

	@Override
	public Object evalMatchStatement(MatchStatement ifStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalBreakStatement(BreakStatement breakStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalContinueStatement(ContinueStatement continueStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalForStatement(ForStatement forStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalWhileStatement(WhileStatement whileStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalImportStatement(ImportStatement importStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalCompoundStatement(CompoundStatement compoundStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalTryStatement(TryStatement tryStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalThrowStatement(ThrowStatement throwStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalDefaultStatement(DefaultStatement defaultStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalReturnStatement(ReturnStatement returnStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalCaseStatement(CaseStatement caseStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalMultiExpression(MultiExpression multex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalRangeExpression(RangeExpression rangex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalFunctionDeclaration(FunctionDeclaration fD, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalVariableDeclaration(VariableDeclaration vD, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalMultiDeclaration(MultiDeclaration mD, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalMulExpression(MulExpression mulex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalAddExpression(AddExpression addex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalComparisonExpression(ComparisonExpression compex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalOrExpression(OrExpression orex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalAndExpression(AndExpression andex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalAssignmentExpression(AssignmentExpression assex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalClassExpression(ClassExpression classex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalDictExpression(DictExpression dictex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalDictComprehension(DictComprehension dictcompex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalInterfaceExpression(InterfaceExpression interex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalLambdaExpression(LambdaExpression lambdex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalListComprehension(ListComprehension listcompex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalListExpression(ListExpression listex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalCalledExpression(CalledExpression callex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalDotExpression(DotExpression dotex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalIndexedExpression(IndexedExpression indexex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalReassignmentExpression(ReassignmentExpression reassex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalBracketedExpression(BracketedExpression brackex, Enviro enviro) {
		return eval(brackex.expression, enviro);
	}

	@Override
	public Object evalDestructuringExpression(DestructuringExpression destex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalMinusExpression(MinusExpression minex, Enviro enviro) {
		return -(double)eval(minex.arg, enviro);
	}

	@Override
	public Object evalNegationExpression(NegationExpression negex, Enviro enviro) {
		return !(boolean)eval(negex.arg, enviro);
	}

	@Override
	public Object evalIdentifierType(IdentifierType idtype, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalPrimitiveType(PrimitiveType idtype, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalListType(ListType idtype, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalDictType(DictType idtype, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalUnionType(UnionType idtype, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
