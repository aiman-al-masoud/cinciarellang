package com.luxlunaris.cincia.backend;

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
import com.luxlunaris.cincia.frontend.ast.interfaces.BinaryExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Constant;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PostfixExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PrimaryExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.interfaces.UnaryExpression;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.ast.statements.ExpressionStatement;
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
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Int;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Str;


public abstract class AbstractTraversal<T> {

	public T eval(Ast ast, Enviro enviro) {

		if(ast instanceof Expression) {
			return evalExpression((Expression)ast, enviro);

		}else if (ast instanceof ExpressionStatement) {
			return evalExpression(((ExpressionStatement)ast).expression, enviro);

		}else if (ast instanceof Declaration) {
			return evalDeclaration((Declaration)ast, enviro);

		}else if( ast instanceof IfStatement ) {
			return evalIfStatement((IfStatement)ast, enviro);

		}else if(ast instanceof MatchStatement) {
			return evalMatchStatement((MatchStatement)ast, enviro);
		
		}else if(ast instanceof TryStatement) {
			return evalTryStatement((TryStatement)ast, enviro);
		
		}else if(ast instanceof CompoundStatement) {
			return evalCompoundStatement((CompoundStatement)ast, enviro);
		
		}else if(ast instanceof ForStatement) {
			return evalForStatement((ForStatement)ast, enviro);
			
		}else if(ast instanceof ImportStatement) {
			return evalImportStatement((ImportStatement)ast, enviro);
			
		}else if(ast instanceof WhileStatement) {
			return evalWhileStatement((WhileStatement)ast, enviro);
			
		}else if(ast instanceof ThrowStatement) {
			return evalThrowStatement((ThrowStatement)ast, enviro);
			
		}else if (ast == null) { //TODO: really?
			return null;
		}
		
		System.out.println(ast.getClass());
		throw new RuntimeException("No such AST class!");

	}


	public T evalExpression(Expression expression, Enviro enviro) {


		if(expression instanceof BinaryExpression) {
			return evalBinaryExpression((BinaryExpression)expression, enviro);

		}else if(expression instanceof TernaryExpression) {
			return evalTernaryExpression((TernaryExpression)expression, enviro);

		}else if(expression instanceof MultiExpression) {
			return evalMultiExpression((MultiExpression)expression, enviro);

		}else if(expression instanceof ObjectExpression) {
			return evalObjectExpression((ObjectExpression)expression, enviro);

		}else if(expression instanceof PostfixExpression) {
			return evalPostfixExpression((PostfixExpression)expression, enviro);

		}else if(expression instanceof PrimaryExpression) {
			return evalPrimaryExpression((PrimaryExpression)expression, enviro);

		}else if(expression instanceof UnaryExpression) {
			return evalUnaryExpression((UnaryExpression)expression, enviro);

		}else if(expression instanceof Constant) {
			return evalConstant((Constant)expression, enviro);

		}else if(expression instanceof RangeExpression) {
			return evalRangeExpression((RangeExpression)expression, enviro);

		}

		throw new RuntimeException("No such expression class!");

	}


	public T evalDeclaration(Declaration declaration, Enviro enviro) {

		if(declaration instanceof FunctionDeclaration) {
			return evalFunctionDeclaration((FunctionDeclaration)declaration, enviro);

		}else if (declaration instanceof VariableDeclaration) {
			return evalVariableDeclaration((VariableDeclaration)declaration, enviro);

		}else if (declaration instanceof MultiDeclaration) {
			return evalMultiDeclaration((MultiDeclaration)declaration, enviro);

		}

		throw new RuntimeException("No such declaration class!");
	}


	public T evalBinaryExpression(BinaryExpression binexp, Enviro enviro) {

		if(binexp instanceof MulExpression) {
			return evalMulExpression((MulExpression)binexp, enviro);
		}else if(binexp instanceof AddExpression) {
			return evalAddExpression((AddExpression)binexp, enviro);
		}else if(binexp instanceof ComparisonExpression) {
			return evalComparisonExpression((ComparisonExpression)binexp, enviro);
		}else if(binexp instanceof AndExpression) {
			return evalAndExpression((AndExpression)binexp, enviro);
		}else if(binexp instanceof OrExpression) {
			return evalOrExpression((OrExpression)binexp, enviro);
		}else if(binexp instanceof AssignmentExpression) {
			return evalAssignmentExpression((AssignmentExpression)binexp, enviro);
		}

		throw new RuntimeException("No such binary expression class!");

	}

	public T evalObjectExpression(ObjectExpression objex, Enviro enviro) {

		if(objex instanceof ListExpression) {
			return evalListExpression((ListExpression)objex, enviro);
		}else if (objex instanceof DictExpression) {
			return evalDictExpression((DictExpression)objex, enviro);
		}else if (objex instanceof ListComprehension) {
			return evalListComprehension((ListComprehension)objex, enviro);
		}else if (objex instanceof DictComprehension) {
			return evalDictComprehension((DictComprehension)objex, enviro);
		}else if (objex instanceof InterfaceExpression) {
			return evalInterfaceExpression((InterfaceExpression)objex, enviro);
		}else if (objex instanceof ClassExpression) {
			return evalClassExpression((ClassExpression)objex, enviro);
		}else if (objex instanceof LambdaExpression) {
			return evalLambdaExpression((LambdaExpression)objex, enviro);
		}

		throw new RuntimeException("No such object expression!");
	}


	public T evalPostfixExpression(PostfixExpression posex, Enviro enviro) {

		if(posex instanceof CalledExpression) {
			return evalCalledExpression((CalledExpression)posex, enviro);
		}else if(posex instanceof DotExpression) {
			return evalDotExpression((DotExpression)posex, enviro);
		}else if(posex instanceof IndexedExpression) {
			return evalIndexedExpression((IndexedExpression)posex, enviro);
		}else if(posex instanceof ReassignmentExpression) {
			return evalReassignmentExpression((ReassignmentExpression)posex, enviro);
		}else if(posex instanceof PrimaryExpression) {
			return evalPrimaryExpression((PrimaryExpression)posex, enviro);
		}

		throw new RuntimeException("No such postfix expression!");

	}

	public T evalUnaryExpression(UnaryExpression unex, Enviro enviro) {

		if(unex instanceof DestructuringExpression) {
			return evalDestructuringExpression((DestructuringExpression)unex, enviro);
		}else if(unex instanceof MinusExpression) {
			return evalMinusExpression((MinusExpression)unex, enviro);
		}else if(unex instanceof NegationExpression) {
			return evalNegationExpression((NegationExpression)unex, enviro);
		}

		throw new RuntimeException("No such unary expression!");

	}


	public T evalPrimaryExpression(PrimaryExpression primex, Enviro enviro) {

		if(primex instanceof BracketedExpression) {
			return evalBracketedExpression((BracketedExpression)primex, enviro);
		}else if(primex instanceof Identifier) {
			return evalIdentifier((Identifier)primex, enviro);
		}else if(primex instanceof Constant) {
			return evalConstant((Constant)primex, enviro);
		}

		throw new RuntimeException("No such primary expression!");

	}

	public T evalConstant(Constant constant, Enviro enviro) {

		if(constant instanceof Int) {
			return evalInt((Int)constant, enviro);
		}else if(constant instanceof com.luxlunaris.cincia.frontend.ast.tokens.constant.Float) {
			return evalFloat((com.luxlunaris.cincia.frontend.ast.tokens.constant.Float)constant, enviro);
		}else if(constant instanceof Str) {
			return evalStr((Str)constant, enviro);
		}else if(constant instanceof Bool) {
			return evalBool((Bool)constant, enviro);
		}

		throw new RuntimeException("No such constant expression!");
	}

	public abstract T evalIdentifier(Identifier identex, Enviro enviro);
	public abstract T evalInt(Int intex, Enviro enviro);
	public abstract T evalFloat(com.luxlunaris.cincia.frontend.ast.tokens.constant.Float floatex, Enviro enviro);
	public abstract T evalStr(Str strex, Enviro enviro);
	public abstract T evalBool(Bool boolex, Enviro enviro);	
	public abstract T evalTernaryExpression(TernaryExpression terex, Enviro enviro);
	public abstract T evalIfStatement(IfStatement ifStatement, Enviro enviro);
	public abstract T evalMatchStatement(MatchStatement ifStatement, Enviro enviro);
	public abstract T evalBreakStatement(BreakStatement breakStatement, Enviro enviro);
	public abstract T evalContinueStatement(ContinueStatement continueStatement, Enviro enviro);
	public abstract T evalForStatement(ForStatement forStatement, Enviro enviro);
	public abstract T evalWhileStatement(WhileStatement whileStatement, Enviro enviro);
	public abstract T evalImportStatement(ImportStatement importStatement, Enviro enviro);
	public abstract T evalCompoundStatement(CompoundStatement compoundStatement, Enviro enviro);
	public abstract T evalTryStatement(TryStatement tryStatement, Enviro enviro);
	public abstract T evalThrowStatement(ThrowStatement throwStatement, Enviro enviro);	
	public abstract T evalDefaultStatement(DefaultStatement defaultStatement, Enviro enviro);
	public abstract T evalReturnStatement(ReturnStatement returnStatement, Enviro enviro);
	public abstract T evalCaseStatement(CaseStatement caseStatement, Enviro enviro);
	public abstract T evalMultiExpression(MultiExpression multex, Enviro enviro);
	public abstract T evalRangeExpression(RangeExpression rangex, Enviro enviro);
	public abstract T evalFunctionDeclaration(FunctionDeclaration fD, Enviro enviro);
	public abstract T evalVariableDeclaration(VariableDeclaration vD, Enviro enviro);
	public abstract T evalMultiDeclaration(MultiDeclaration mD, Enviro enviro);
	public abstract T evalMulExpression(MulExpression mulex, Enviro enviro);
	public abstract T evalAddExpression(AddExpression addex, Enviro enviro);
	public abstract T evalComparisonExpression(ComparisonExpression compex, Enviro enviro);
	public abstract T evalOrExpression(OrExpression orex, Enviro enviro);
	public abstract T evalAndExpression(AndExpression andex, Enviro enviro);
	public abstract T evalAssignmentExpression(AssignmentExpression assex, Enviro enviro);
	public abstract T evalClassExpression(ClassExpression classex, Enviro enviro);
	public abstract T evalDictExpression(DictExpression dictex, Enviro enviro);
	public abstract T evalDictComprehension(DictComprehension dictcompex, Enviro enviro);
	public abstract T evalInterfaceExpression(InterfaceExpression interex, Enviro enviro);
	public abstract T evalLambdaExpression(LambdaExpression lambdex, Enviro enviro);
	public abstract T evalListComprehension(ListComprehension listcompex, Enviro enviro);
	public abstract T evalListExpression(ListExpression listex, Enviro enviro);
	public abstract T evalCalledExpression(CalledExpression callex, Enviro enviro);
	public abstract T evalDotExpression(DotExpression dotex, Enviro enviro);
	public abstract T evalIndexedExpression(IndexedExpression indexex, Enviro enviro);
	public abstract T evalReassignmentExpression(ReassignmentExpression reassex, Enviro enviro);
	public abstract T evalBracketedExpression(BracketedExpression brackex, Enviro enviro);
	public abstract T evalDestructuringExpression(DestructuringExpression destex, Enviro enviro);
	public abstract T evalMinusExpression(MinusExpression minex, Enviro enviro);
	public abstract T evalNegationExpression(NegationExpression negex, Enviro enviro);
	

}
