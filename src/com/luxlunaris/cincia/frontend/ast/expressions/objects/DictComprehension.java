package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
//import com.luxlunaris.cincia.frontend.ast.interfaces.PrimaryExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;

public class DictComprehension implements ObjectExpression{
	
	// {key : val `for` source `in` iterable `where` where}
	public Expression key;
	public Expression val;
	public Expression source;
	public Expression iterable;
	public Expression where; //optional
	
	
	@Override
	public Expression simplify() {
		this.key = key.simplify();
		this.val = val.simplify();
		this.source = source.simplify();
		this.iterable = iterable.simplify();
		
		
		if(where!=null) {
			this.where = where.simplify();
		}
		
		return this;
	}
	
	
}
