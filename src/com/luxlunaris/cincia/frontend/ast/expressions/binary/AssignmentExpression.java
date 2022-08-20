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
	
	
	public LeftValue left;
	public Expression right; //other assignment expression or conditional expression or object
	
	@Override
	public String toString() {
		return "("+left+" = "+right+")";
	}
	
}
