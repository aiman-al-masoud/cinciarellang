package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PrimaryExpression;

public class ListExpression implements ObjectExpression{
	
	public MultiExpression elements; // could include DestructuringExpression
	
}
