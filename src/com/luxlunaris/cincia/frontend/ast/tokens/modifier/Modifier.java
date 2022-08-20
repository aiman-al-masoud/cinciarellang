package com.luxlunaris.cincia.frontend.ast.tokens.modifier;

import com.luxlunaris.cincia.frontend.ast.tokens.AbstractToken;

public class Modifier extends AbstractToken{

	public final Modifiers value;

	public Modifier(Modifiers value) {
		this.value = value;
	}

}
