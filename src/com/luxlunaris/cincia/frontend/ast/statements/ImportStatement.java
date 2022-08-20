package com.luxlunaris.cincia.frontend.ast.statements;

import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.Str;

public class ImportStatement implements Statement{
	
	public Identifier identifier;
	public Str fromPath;
	public Identifier alias;
	
	public ImportStatement() {
		
	}
	
}
