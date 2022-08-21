package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PrimaryExpression;

public class ListExpression implements ObjectExpression{
	
	public List<Expression> elements; // could include DestructuringExpression
	
	public ListExpression() {
		elements = new ArrayList<Expression>();
	}
	
}
