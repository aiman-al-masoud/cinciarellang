package com.luxlunaris.cincia.frontend.ast.expressions.binary;

//import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
//import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.LeftValue;

/**
 * Right associative
 *
 */
public class AssignmentExpression implements Expression{
	
	//TODO: fix this
//	public LeftValue left;
	public Expression left;
	public Expression right; //other assignment expression or conditional expression or object
	
	@Override
	public String toString() {
		return "("+left+" = "+right+")";
	}
	
	@Override
	public Expression simplify() {
		
		if(left==null) {
			return right.simplify();
		}
		
		this.left = left.simplify();
		this.right = right.simplify();
		return this;
	}
	
	
	
	
	
}
