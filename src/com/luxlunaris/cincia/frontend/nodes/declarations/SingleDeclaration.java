package com.luxlunaris.cincia.frontend.nodes.declarations;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.tokens.Identifier;
import com.luxlunaris.cincia.frontend.tokens.Modifier;

public class SingleDeclaration implements Declaration{
	
	public List<Modifier> modifiersList;
	public Identifier identifier;
	public Identifier type; //TODO: turn primitive types into identifiers
	
	public SingleDeclaration() {
		modifiersList = new ArrayList<Modifier>();
	}
	
}
