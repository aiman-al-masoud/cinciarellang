package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.backend.callables.PureCinciaFunction;
import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.SingleExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

public class LambdaExpression extends SingleExpression implements ObjectExpression{

	public List<Modifiers> modifiers;
	public Signature signature;
	public Ast runnable; //either block or expression
	public boolean explicitParams; // explicit like: \a,b->a+b implicit like: \a+b

	public LambdaExpression() {
		modifiers = new ArrayList<Modifiers>();
		explicitParams = true;
	}


	@Override
	public Expression simplify() {

		if(runnable != null) {
			this.runnable = runnable.simplify();
		}

		if(signature !=null) {
			this.signature  = signature.simplify();
		}

		return this;
	}

	@Override
	public String toString() {
		return signature.toString();
	}

	/**
	 * Factory method to convert an expression into a lambda 
	 * expression (anonymous function).
	 * @param input
	 * @param body
	 * @param returnType
	 * @return
	 */
	public static LambdaExpression fromExpression(Identifier input, Expression body, Type returnType) {
		//TODO: multiple input params!

		LambdaExpression lambdex = new LambdaExpression();
		lambdex.runnable = body; // body
		Signature s1 = new Signature();
		VariableDeclaration i = new VariableDeclaration();
		i.name = input; // input
		s1.params = i;
		s1.returnType = returnType; //return type
		lambdex.signature = s1;
		
		return lambdex;
	}



}
