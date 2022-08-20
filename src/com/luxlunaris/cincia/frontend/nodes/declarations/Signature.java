package com.luxlunaris.cincia.frontend.nodes.declarations;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.tokens.Identifier;
import com.luxlunaris.cincia.frontend.tokens.Modifier;

//signature ::= modifier* '\' [multi_declaration] [':' type]  
public class Signature implements Declaration{
	
	public List<Modifier> modifiers; // can be empty
	public MultiDeclaration params;// can be null
	public Identifier returnType;
	
	public Signature() {
		modifiers = new ArrayList<Modifier>();
	}
	
}
