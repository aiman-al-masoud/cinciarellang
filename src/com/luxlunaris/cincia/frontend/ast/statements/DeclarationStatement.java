package com.luxlunaris.cincia.frontend.ast.statements;

//import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;

public class DeclarationStatement implements Statement{
	
	public Declaration declaration;
	
	public DeclarationStatement(Declaration declaration) {
		this.declaration = declaration;
	}
	
	@Override
	public String toString() {
		return declaration.toString();
	}

	@Override
	public Statement simplify() {
		return this;
	}
	
}
