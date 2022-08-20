package com.luxlunaris.cincia.frontend.nodes.declarations;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.nodes.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.nodes.interfaces.LeftValue;
import com.luxlunaris.cincia.frontend.nodes.tokens.Identifier;
import com.luxlunaris.cincia.frontend.nodes.tokens.modifier.Modifier;

public class SingleDeclaration implements Declaration{
	
	public List<Modifier> modifiersList;
	public Identifier identifier;
	public Identifier type; //TODO: turn primitive types into identifiers
	
	public SingleDeclaration() {
		modifiersList = new ArrayList<Modifier>();
	}
	
}
