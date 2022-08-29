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

public abstract class AbstractTraversal {

	public Object eval(Ast ast, Enviro enviro) {

		if(ast instanceof Expression) {
			return evalExpression((Expression)ast, enviro);

		}else if (ast instanceof Declaration) {
			return evalDeclaration((Declaration)ast, enviro);

		}else if( ast instanceof IfStatement ) {
			return evalIfStatement((IfStatement)ast, enviro);

		}else if(ast instanceof MatchStatement) {
			return evalMatchStatement((MatchStatement)ast, enviro);

		}

		throw new RuntimeException("No such AST class!");
		
	}


	public Object evalExpression(Expression expression, Enviro enviro) {


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
			
		}else if(expression instanceof Type) {
			return evalType((Type)expression, enviro);

		}else if(expression instanceof Constant) {
			return evalConstant((Constant)expression, enviro);
			
		}else if(expression instanceof RangeExpression) {
			return evalRangeExpression((RangeExpression)expression, enviro);
			
		}
		
		throw new RuntimeException("No such expression class!");

	}


	public Object evalDeclaration(Declaration declaration, Enviro enviro) {

		if(declaration instanceof FunctionDeclaration) {
			return evalFunctionDeclaration((FunctionDeclaration)declaration, enviro);
			
		}else if (declaration instanceof VariableDeclaration) {
			return evalVariableDeclaration((VariableDeclaration)declaration, enviro);
					
		}else if (declaration instanceof MultiDeclaration) {
			return evalMultiDeclaration((MultiDeclaration)declaration, enviro);
					
		}
		
		throw new RuntimeException("No such declaration class!");
	}
	
	
	public Object evalBinaryExpression(BinaryExpression binexp, Enviro enviro) {
		
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
	
	public Object evalObjectExpression(ObjectExpression objex, Enviro enviro) {
		
	}
	
	
	public Object evalPostfixExpression(PostfixExpression posex, Enviro enviro) {
		
	}
	
	public Object evalPrimaryExpression(PrimaryExpression primex, Enviro enviro) {
		
	}
	
	public Object evalUnaryExpression(UnaryExpression unex, Enviro enviro) {
		
	}
	
	public Object evalType(Type typex, Enviro enviro) {
		
	}
	
	

	
	
	public abstract Object evalTernaryExpression(TernaryExpression terex, Enviro enviro);
	public abstract Object evalConstant(Constant constant, Enviro enviro);
	public abstract Object evalIfStatement(IfStatement ifStatement, Enviro enviro);
	public abstract Object evalMatchStatement(MatchStatement ifStatement, Enviro enviro);
	public abstract Object evalBreakStatement(BreakStatement breakStatement, Enviro enviro);
	public abstract Object evalContinueStatement(ContinueStatement continueStatement, Enviro enviro);
	public abstract Object evalForStatement(ForStatement forStatement, Enviro enviro);
	public abstract Object evalWhileStatement(WhileStatement whileStatement, Enviro enviro);
	public abstract Object evalImportStatement(ImportStatement importStatement, Enviro enviro);
	public abstract Object evalCompoundStatement(CompoundStatement compoundStatement, Enviro enviro);
	public abstract Object evalTryStatement(TryStatement tryStatement, Enviro enviro);
	public abstract Object evalThrowStatement(ThrowStatement throwStatement, Enviro enviro);	
	public abstract Object evalDefaultStatement(DefaultStatement defaultStatement, Enviro enviro);
	public abstract Object evalReturnStatement(ReturnStatement returnStatement, Enviro enviro);
	public abstract Object evalCaseStatement(CaseStatement caseStatement, Enviro enviro);
	public abstract Object evalMultiExpression(MultiExpression multex, Enviro enviro);
	public abstract Object evalRangeExpression(RangeExpression rangex, Enviro enviro);
	public abstract Object evalFunctionDeclaration(FunctionDeclaration fD, Enviro enviro);
	public abstract Object evalVariableDeclaration(VariableDeclaration vD, Enviro enviro);
	public abstract Object evalMultiDeclaration(MultiDeclaration mD, Enviro enviro);
	public abstract Object evalMulExpression(MulExpression mulex, Enviro enviro);
	public abstract Object evalAddExpression(AddExpression addex, Enviro enviro);
	public abstract Object evalComparisonExpression(ComparisonExpression compex, Enviro enviro);
	public abstract Object evalOrExpression(OrExpression orex, Enviro enviro);
	public abstract Object evalAndExpression(AndExpression andex, Enviro enviro);
	public abstract Object evalAssignmentExpression(AssignmentExpression assex, Enviro enviro);
	public abstract Object evalClassExpression(ClassExpression classex, Enviro enviro);
	public abstract Object evalDictExpression(DictExpression dictex, Enviro enviro);
	public abstract Object evalDictComprehension(DictComprehension dictcompex, Enviro enviro);
	public abstract Object evalInterfaceExpression(InterfaceExpression interex, Enviro enviro);
	public abstract Object evalLambdaExpression(LambdaExpression lambdex, Enviro enviro);
	public abstract Object evalListComprehension(ListComprehension listcompex, Enviro enviro);
	public abstract Object evalListExpression(ListExpression listex, Enviro enviro);
	public abstract Object evalCalledExpression(CalledExpression callex, Enviro enviro);
	public abstract Object evalDotExpression(DotExpression dotex, Enviro enviro);
	public abstract Object evalIndexedExpression(IndexedExpression indexex, Enviro enviro);
	public abstract Object evalReassignmentExpression(ReassignmentExpression reassex, Enviro enviro);
	public abstract Object evalBracketedExpression(BracketedExpression brackex, Enviro enviro);
	public abstract Object evalDestructuringExpression(DestructuringExpression destex, Enviro enviro);
	public abstract Object evalMinusExpression(MinusExpression minex, Enviro enviro);
	public abstract Object evalNegationExpression(NegationExpression negex, Enviro enviro);
	
	
	public abstract Object evalIdentifierType(IdentifierType idtype, Enviro enviro);
	public abstract Object evalPrimitiveType(PrimitiveType idtype, Enviro enviro);
	public abstract Object evalListType(ListType idtype, Enviro enviro);
	public abstract Object evalDictType(DictType idtype, Enviro enviro);
	public abstract Object evalUnionType(UnionType idtype, Enviro enviro);


}
