package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.luxlunaris.cincia.frontend.ast.expressions.unary.DestructuringExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import java.util.Map.Entry;

public class DictExpression implements ObjectExpression{
	
	//order doesn't matter
	public List<Entry<Expression, Expression>> entries;
	public List<DestructuringExpression> destructs;
	
	
	public DictExpression() {
		entries = new ArrayList<Map.Entry<Expression, Expression>>();
		destructs = new ArrayList<DestructuringExpression>();
	}
	
	public void addEntry(Expression key, Expression val) {
		entries.add(Map.entry(key, val));
	}
	
	public void addDestruct(DestructuringExpression exp) {
		destructs.add(exp);
	}

	@Override
	public Expression simplify() {
		return this;
	}
	
	
}
