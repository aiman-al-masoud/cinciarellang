package com.luxlunaris.cincia.frontend.nodes.expressions.objects;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.nodes.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.nodes.declarations.Signature;
import com.luxlunaris.cincia.frontend.nodes.expressions.Expression;
import com.luxlunaris.cincia.frontend.nodes.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.tokens.Modifier;

//example: // public \ x:int:int -> 2*x
public class Lambda implements Expression{
	
	public Signature signature;
	
	//either block or expression:
	public CompoundStatement block;
	public Expression expression;
	
	
	
}
