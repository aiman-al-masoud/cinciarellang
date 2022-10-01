package com.luxlunaris.cincia.frontend.ast.expressions.binary;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

/**
 * Right associative
 *
 */
public class AssignmentExpression extends AbstractBinaryExpression{

	/**
	 * A comment describing the assigned variable.
	 */
	public String comment;
	
	/**
	 * Assignment specific modifiers.
	 */
	public List<Modifiers> modifiers;

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
