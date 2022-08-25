package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PrimaryExpression;

public class ListComprehension implements ObjectExpression{
	
	public Expression element;
	public Expression iterable;
	public Expression where; //optional
	
	@Override
	public Expression simplify() {
		this.element = element.simplify();
		this.iterable = iterable.simplify();
		this.where = where.simplify();
		return this;
	}
	
}
