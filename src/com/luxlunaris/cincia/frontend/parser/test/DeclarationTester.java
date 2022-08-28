package com.luxlunaris.cincia.frontend.parser.test;

import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.UnionType;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;

public class DeclarationTester extends AbstractTester{

	public DeclarationTester() {

		// primitive type variable declaration
		VariableDeclaration pr = new VariableDeclaration();
		pr.name = new Identifier("x");
		pr.type = new PrimitiveType(PrimitiveType.INT);
		add("x:int", pr.toString());
		
		// union type
		VariableDeclaration un = new VariableDeclaration();
		un.name = new Identifier("x");
		UnionType uT = new UnionType();
		uT.addType(new PrimitiveType(PrimitiveType.INT));
		uT.addType(new PrimitiveType(PrimitiveType.FLOAT));
		uT.addType(new IdentifierType(new Identifier("Object")));
		un.type = uT;
		add("x:int|float|Object", un.toString());
		
		
		add("x:{int:int}", "");
		add("x:int[]", "");
		add("f:\\x:int:int;", "");
	}

}
