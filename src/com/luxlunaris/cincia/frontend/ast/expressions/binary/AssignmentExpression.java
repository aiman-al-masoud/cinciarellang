package com.luxlunaris.cincia.frontend.ast.expressions.binary;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;

/**
 * Right associative
 *
 */
public class AssignmentExpression extends AbstractBinaryExpression{

	public String comment;

	@Override
	public String toString() {
		return "("+left+" = "+right+")" + " with comment: "+comment;
	}

	@Override
	public Expression simplify() {

		//		System.out.println( "simplifying: "+ System.identityHashCode(this) );

		if(left==null) {
//			System.out.println("left is null!");
//			System.out.println(this);

			Expression exp = right.simplify();

			// re-insert comment after simplifying
			try {
				((AssignmentExpression)exp).comment = this.comment;
			} catch (ClassCastException e) {

			}

			return exp;
		}else {
//			System.out.println("left is not null!");
//			System.out.println(this);
			this.left = left.simplify();
			this.right = right.simplify();
			return this;
		}

	}


}
