package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PrimaryExpression;

//could include one or more DestructuringExpression(s)
public class ListExpression implements ObjectExpression{
	
//	public List<Expression> elements; 
	public Expression elements;
	
	public ListExpression() {
//		elements = new ArrayList<Expression>();
	}
	
}
