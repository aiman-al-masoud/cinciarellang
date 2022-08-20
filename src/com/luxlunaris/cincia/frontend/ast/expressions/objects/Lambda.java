package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import com.luxlunaris.cincia.frontend.ast.declarations.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

//example: // public \ x:int:int -> 2*x
public class Lambda implements Expression{
	
	public Signature signature;
	
	//either block or expression:
	public CompoundStatement block;
	public Expression expression;
	
	
	
}
