package com.luxlunaris.cincia.frontend.nodes.expressions.binary;

import java.util.List;

import com.luxlunaris.cincia.frontend.nodes.interfaces.Expression;

public class OrExpression implements Expression{


	public Expression left;
	public AndExpression right;
	
// or other solution
//	public OrExpression left;
//	OrExpression e = new OrExpression();
//	OrExpression e2 = new OrExpression();
//	e2.left = null;
//	e2.right = new AndExpression();
//	e.left = e2;
//	e.right = new AndExpression();
	
}
