package com.luxlunaris.cincia.frontend.ast.expressions.binary;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;

/**
 * Right associative
 *
 */
public class AssignmentExpression extends AbstractBinaryExpression{
	

	@Override
	public String toString() {
		return "("+left+" = "+right+")";
	}
	
	@Override
	public Expression simplify() {
		
		if(left==null) {
			return right.simplify();
		}else {
			this.left = left.simplify();
			this.right = right.simplify();
			return this;
		}
				
	}
	

}
