package com.luxlunaris.cincia.frontend.nodes.expressions.binary;

import com.luxlunaris.cincia.frontend.nodes.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.nodes.expressions.Expression;
import com.luxlunaris.cincia.frontend.nodes.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.nodes.interfaces.LeftValue;

public class AssignmentExpression implements Expression{
	
	
	public LeftValue left;
	public MultiExpression right;
	
}
