package com.luxlunaris.cincia.frontend.parser.test;

import java.util.Arrays;
import java.util.Collections;

import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.ListType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.UnionType;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

public class DeclarationTester extends AbstractTester{

	public DeclarationTester() {

		// primitive type variable 
		VariableDeclaration pr = new VariableDeclaration();
		pr.name = new Identifier("x");
		pr.type = new PrimitiveType(PrimitiveType.INT);
		add("x:int", pr.toString());
		
		// primitive type variable w/ modifiers
		pr.modifiers = Arrays.asList(new Modifier(Modifiers.STATIC), new Modifier(Modifiers.FINAL));
		add("static final x:int", pr.toString());
		
		// union type
		VariableDeclaration un = new VariableDeclaration();
		un.name = new Identifier("x");
		UnionType uT = new UnionType();
		uT.addType(new PrimitiveType(PrimitiveType.INT));
		uT.addType(new PrimitiveType(PrimitiveType.FLOAT));
		uT.addType(new IdentifierType(new Identifier("Object")));
		un.type = uT;
		add("x:int|float|Object", un.toString());
		
		// list-type variable
		VariableDeclaration lD = new VariableDeclaration();
		lD.name = new Identifier("x");
		ListType lT = new ListType();
		lT.value = new PrimitiveType(PrimitiveType.INT);
		lD.type = lT;
		add("x:int[]", lD.toString());

		// dict type variable
		
		add("x:{int:int}", "");
		
		
		
		add("f:\\x:int:int;", "");
	}

}
