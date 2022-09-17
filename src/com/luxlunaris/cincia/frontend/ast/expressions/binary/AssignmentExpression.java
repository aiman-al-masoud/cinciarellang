package com.luxlunaris.cincia.frontend.ast.expressions.binary;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;

/**
 * Right associative
 *
 */
public class AssignmentExpression extends AbstractBinaryExpression{

	/**
	 * A comment describing the assigned variable.
	 */
	public String comment;

	@Override
	public String toString() {
		return "("+left+" = "+right+")";
	}

	@Override
	public Expression simplify() {


		if(left==null) {

			Expression exp = right.simplify();

			// re-insert comment string after simplifying
			try {
				((AssignmentExpression)exp).comment = this.comment;
			} catch (ClassCastException e) {

			}

			return exp;
		}else {

			this.left = left.simplify();
			this.right = right.simplify();
			return this;
		}

	}


}
