package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
//import com.luxlunaris.cincia.frontend.ast.interfaces.PrimaryExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;

public class DictComprehension implements ObjectExpression{

	public Expression key;
	public Expression val;
	public Expression iterable;
	public Expression where; //optional
}
