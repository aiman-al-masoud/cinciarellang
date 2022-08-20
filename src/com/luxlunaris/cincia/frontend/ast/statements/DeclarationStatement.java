package com.luxlunaris.cincia.frontend.ast.statements;

import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;

public class DeclarationStatement implements Statement{
	
	public MultiDeclaration multiDeclaration;
	
	public DeclarationStatement(MultiDeclaration multiDeclaration) {
		this.multiDeclaration = multiDeclaration;
	}
	
}
