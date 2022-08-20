package com.luxlunaris.cincia.frontend.nodes.statements;

import com.luxlunaris.cincia.frontend.nodes.interfaces.Statement;
import com.luxlunaris.cincia.frontend.nodes.tokens.Identifier;
import com.luxlunaris.cincia.frontend.nodes.tokens.Str;

public class ImportStatement implements Statement{
	
	public Identifier identifier;
	public Str fromPath;
	public Identifier alias;
	
	public ImportStatement() {
		
	}
	
}
