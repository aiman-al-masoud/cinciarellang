package com.luxlunaris.cincia.frontend.nodes.expressions.objects;

import com.luxlunaris.cincia.frontend.nodes.declarations.Signature;
import com.luxlunaris.cincia.frontend.nodes.interfaces.Expression;
import com.luxlunaris.cincia.frontend.nodes.statements.CompoundStatement;

//example: // public \ x:int:int -> 2*x
public class Lambda implements Expression{
	
	public Signature signature;
	
	//either block or expression:
	public CompoundStatement block;
	public Expression expression;
	
	
	
}
