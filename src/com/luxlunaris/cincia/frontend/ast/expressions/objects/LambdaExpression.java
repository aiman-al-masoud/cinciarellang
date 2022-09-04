package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.backend.PureCinciaFunction;
import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

//example: // public \ x:int:int -> 2*x
public class LambdaExpression implements ObjectExpression{
	
	public List<Modifiers> modifiers;
	public Signature signature;
	
	//either block or expression:
	public CompoundStatement block;
	public Expression expression;
	
	
	public LambdaExpression() {
		modifiers = new ArrayList<Modifiers>();
	}


	@Override
	public Expression simplify() {
		
		if(expression != null) {
			this.expression = expression.simplify();
		}
		
		if(block != null) {
			this.block = (CompoundStatement) block.simplify();
		}
		
		return this;
	}
	
	@Override
	public String toString() {
		return modifiers+" "+signature+"->"+(block==null? expression :block);
	}
	
	
	public static LambdaExpression fromExpression(Identifier input, Expression body, Type returnType) {
		
		LambdaExpression lambdex = new LambdaExpression();
		lambdex.expression = body; // body
		lambdex.modifiers.add(Modifiers.PURE); 
		Signature s1 = new Signature();
		VariableDeclaration i = new VariableDeclaration();
		i.name = input; // input
		s1.params = i;
		s1.returnType = returnType; //return type
		lambdex.signature = s1;
		
		return lambdex;
	}
	
	
	
}
