package com.luxlunaris.cincia.frontend.ast.interfaces;

//TODO: only identifier (variable) or const can be a Primary Expression (NOT kw, op, punc...)
public interface Token extends PrimaryExpression{

	Object getValue();
//	 getType();
}
