package com.luxlunaris.cincia.frontend.ast.expressions.type;

import com.luxlunaris.cincia.backend.interfaces.Eval;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.AbstractToken;

//TODO: add in EBNF
public class ListType  extends CollectionType{

	public Type value;

	public ListType() {

	}

	public ListType(Type value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value+"[]";
	}

	@Override
	public boolean matches(Type other) {


		try {

			ListType otherl = (ListType)other;
			return value.matches(otherl.value);

		} catch (ClassCastException e) {

		}

		return false;
	}
	
	@Override
	public Type unwrap() {
		return this;
	}
	
	

	//TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@Override
	public Type resolve(Eval eval, Enviro enviro) {
		//TODO!!!!!!!!!!!!!!
		return this;
	}

}


