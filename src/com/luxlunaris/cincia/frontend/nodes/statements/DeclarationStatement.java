package com.luxlunaris.cincia.frontend.nodes.statements;

import com.luxlunaris.cincia.frontend.nodes.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.nodes.interfaces.Statement;

public class DeclarationStatement implements Statement{
	
	public MultiDeclaration multiDeclaration;
	
	public DeclarationStatement(MultiDeclaration multiDeclaration) {
		this.multiDeclaration = multiDeclaration;
	}
	
}
