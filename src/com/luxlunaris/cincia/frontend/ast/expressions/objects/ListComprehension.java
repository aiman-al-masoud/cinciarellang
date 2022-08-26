package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PrimaryExpression;

public class ListComprehension implements ObjectExpression{
	
	// [element `for` source `in` iterable `where` where]
	public Expression element;
	public Expression source;
	public Expression iterable;
	public Expression where; //optional
	
	@Override
	public Expression simplify() {
		this.element = element.simplify();
		this.source = source.simplify();
		this.iterable = iterable.simplify();
		
		if(where!=null) {
			this.where = where.simplify();
		}
		
		return this;
	}
	
	@Override
	public String toString() {
		return "["+element+" for "+source+" in "+iterable+"]";
	}
	
}
