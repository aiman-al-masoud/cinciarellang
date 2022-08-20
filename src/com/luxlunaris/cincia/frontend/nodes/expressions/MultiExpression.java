package com.luxlunaris.cincia.frontend.nodes.expressions;

import java.util.ArrayList;
import java.util.List;

public class MultiExpression implements Expression{
	
	public List<Expression> expressionsList;
	
	public MultiExpression() {
		expressionsList = new ArrayList<Expression>();
	}
	
}
