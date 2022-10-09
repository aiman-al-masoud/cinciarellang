package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;

public class ListExpression implements  ObjectExpression{

	public List<Expression> elements; 

	public ListExpression() {
		elements = new ArrayList<Expression>();
	}

	public void add(Expression expression) {
		elements.add(expression);
	}
	public void addAll(List<Expression> expressions) {
		elements.addAll(expressions);
	}

	@Override
	public Expression simplify() {		
		this.elements = elements.stream().map(e->e.simplify()).collect(Collectors.toList());
		return this;
	}

	@Override
	public String toString() {
		return elements.toString();
	}

	@Override
	public List<Expression> toList() {
		return elements;
	}

}
