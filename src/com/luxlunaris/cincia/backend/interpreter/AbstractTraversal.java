package com.luxlunaris.cincia.backend.interpreter;

import java.util.Arrays;

import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.primitives.CinciaBool;
import com.luxlunaris.cincia.backend.primitives.CinciaFloat;
import com.luxlunaris.cincia.backend.primitives.CinciaInt;
import com.luxlunaris.cincia.backend.primitives.CinciaString;
import com.luxlunaris.cincia.backend.types.TypeWrapper;
import com.luxlunaris.cincia.frontend.ast.declarations.FunctionDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.IfExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.MatchExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.PipeExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.RangeExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AddExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AndExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AssignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.ComparisonExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.MulExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.OrExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.forexp.ForExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ClassExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.DictExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ListExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.CalledExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.DotExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.IndexedExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.ReassignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.primary.BracketedExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
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
import com.luxlunaris.cincia.frontend.ast.statements.DeclarationStatement;
import com.luxlunaris.cincia.frontend.ast.statements.ExpressionStatement;
import com.luxlunaris.cincia.frontend.ast.statements.ImportStatement;
import com.luxlunaris.cincia.frontend.ast.statements.exception.ThrowStatement;
import com.luxlunaris.cincia.frontend.ast.statements.exception.TryStatement;
import com.luxlunaris.cincia.frontend.ast.statements.iteration.WhileStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.BreakStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.ContinueStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.ReturnStatement;
import com.luxlunaris.cincia.frontend.ast.statements.labelled.CaseStatement;
import com.luxlunaris.cincia.frontend.ast.statements.labelled.DefaultStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.BoolToken;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.IntToken;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.StrToken;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keyword;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;


public abstract class AbstractTraversal<T> {

	public T eval(Ast ast, Enviro enviro) {

		if(ast instanceof Expression) {
			return evalExpression((Expression)ast, enviro);

		}else if (ast instanceof ExpressionStatement) {
			return evalExpression(((ExpressionStatement)ast).expression, enviro);

		}else if (ast instanceof Declaration) {
			return evalDeclaration((Declaration)ast, enviro);

		}else if (ast instanceof DeclarationStatement) {
			return evalDeclaration(((DeclarationStatement)ast).declaration, enviro);

		}else if(ast instanceof MatchExpression) {
			return evalMatchExpression((MatchExpression)ast, enviro);

		}else if(ast instanceof TryStatement) {
			return evalTryStatement((TryStatement)ast, enviro);

		}else if(ast instanceof CompoundStatement) {
			return evalCompoundStatement((CompoundStatement)ast, enviro);
			
		}else if(ast instanceof ImportStatement) {
			return evalImportStatement((ImportStatement)ast, enviro);

		}else if(ast instanceof WhileStatement) {
			return evalWhileStatement((WhileStatement)ast, enviro);

		}else if(ast instanceof ThrowStatement) {
			return evalThrowStatement((ThrowStatement)ast, enviro);
			
		}else if (ast == null) { //TODO: really?
			return null;
			
		}else if (ast instanceof ReturnStatement) {
			return evalReturnStatement((ReturnStatement)ast, enviro);
			
		}else if ( ast instanceof BreakStatement ) {
			return evalBreakStatement((BreakStatement)ast, enviro);
			
		}else if ( ast instanceof ContinueStatement ) {
			return evalContinueStatement((ContinueStatement)ast, enviro);
			
		}

		throw new RuntimeException("No such AST class!");
	}


	public T evalExpression(Expression expression, Enviro enviro) {


		if(expression instanceof BinaryExpression) {
			return evalBinaryExpression((BinaryExpression)expression, enviro);
			
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

		}else if(expression instanceof PipeExpression) {
			return evalPipeExpression((PipeExpression)expression, enviro);

		}else if (expression instanceof MatchExpression) {
			return evalMatchExpression( (MatchExpression)expression, enviro);
			
		}else if (expression instanceof ForExpression) {
			return evalForExpression((ForExpression)expression, enviro);
			
		}else if (expression instanceof Type) {
//			System.out.println(expression);
			return evalTypeExpression((Type)expression, enviro);
		}else if(expression instanceof IfExpression) {
			return evalIfExpression((IfExpression)expression, enviro);
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

		}else if(primex instanceof Keyword) {
			
			Keywords kw = ((Keyword)primex).value;
			
			
			if(Arrays.asList( PrimitiveType.ANY, PrimitiveType.INT, PrimitiveType.FLOAT, PrimitiveType.STRING, PrimitiveType.BOOL, PrimitiveType.MODULE  ).contains(kw)) {
//			System.out.println("found this keyword: "+kw);
				
				if(kw.equals(PrimitiveType.BOOL)) {
					return (T) CinciaBool.myClass;
				}
				

				if(kw.equals(PrimitiveType.INT)) {
					return (T) CinciaInt.myClass;
				}
				
				if(kw.equals(PrimitiveType.FLOAT)) {
					return (T) CinciaFloat.myClass;
				}
				
				if(kw.equals(PrimitiveType.STRING)) {
					return (T) CinciaString.myClass;
				}
				
				return   (T) new TypeWrapper(new PrimitiveType(kw)); 
			}
			
			
//			return (T) new CinciaKeyword( ((Keyword)primex).value );
			//			return (T) new CinciaClass();
		}

		throw new RuntimeException("No such primary expression!");

	}

	public T evalConstant(Constant constant, Enviro enviro) {

		if(constant instanceof IntToken) {
			return evalInt((IntToken)constant, enviro);
		}else if(constant instanceof com.luxlunaris.cincia.frontend.ast.tokens.constant.FloatToken) {
			return evalFloat((com.luxlunaris.cincia.frontend.ast.tokens.constant.FloatToken)constant, enviro);
		}else if(constant instanceof StrToken) {
			return evalStr((StrToken)constant, enviro);
		}else if(constant instanceof BoolToken) {
			return evalBool((BoolToken)constant, enviro);
		}

		throw new RuntimeException("No such constant expression!");
	}

	public abstract T evalIdentifier(Identifier identex, Enviro enviro);
	public abstract T evalInt(IntToken intex, Enviro enviro);
	public abstract T evalFloat(com.luxlunaris.cincia.frontend.ast.tokens.constant.FloatToken floatex, Enviro enviro);
	public abstract T evalStr(StrToken strex, Enviro enviro);
	public abstract T evalBool(BoolToken boolex, Enviro enviro);	
	public abstract T evalIfExpression(IfExpression ifStatement, Enviro enviro);
	public abstract T evalMatchExpression(MatchExpression ifStatement, Enviro enviro);
	public abstract T evalBreakStatement(BreakStatement breakStatement, Enviro enviro);
	public abstract T evalContinueStatement(ContinueStatement continueStatement, Enviro enviro);
	public abstract T evalForExpression(ForExpression forStatement, Enviro enviro);
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
	public abstract T evalLambdaExpression(LambdaExpression lambdex, Enviro enviro);
	public abstract T evalListExpression(ListExpression listex, Enviro enviro);
	public abstract T evalCalledExpression(CalledExpression callex, Enviro enviro);
	public abstract T evalDotExpression(DotExpression dotex, Enviro enviro);
	public abstract T evalIndexedExpression(IndexedExpression indexex, Enviro enviro);
	public abstract T evalReassignmentExpression(ReassignmentExpression reassex, Enviro enviro);
	public abstract T evalBracketedExpression(BracketedExpression brackex, Enviro enviro);
	public abstract T evalDestructuringExpression(DestructuringExpression destex, Enviro enviro);
	public abstract T evalMinusExpression(MinusExpression minex, Enviro enviro);
	public abstract T evalNegationExpression(NegationExpression negex, Enviro enviro);
	public abstract T evalPipeExpression(PipeExpression expression, Enviro enviro);
	public abstract T evalTypeExpression(Type type, Enviro enviro);
	
}
