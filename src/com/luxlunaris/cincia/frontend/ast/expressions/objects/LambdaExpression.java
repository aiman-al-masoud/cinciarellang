package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;

//example: // public \ x:int:int -> 2*x
public class LambdaExpression implements ObjectExpression{
	
	public List<Modifier> modifiers;
	public Signature signature;
	
	//either block or expression:
	public CompoundStatement block;
	public Expression expression;
	
	
	public LambdaExpression() {
		modifiers = new ArrayList<Modifier>();
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
	
	
}
