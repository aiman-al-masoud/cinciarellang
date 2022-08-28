package com.luxlunaris.cincia.frontend.parser.test;

import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ClassExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.InterfaceExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.statements.DeclarationStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;

public class InterfaceTester extends AbstractTester {

	public InterfaceTester() {

		InterfaceExpression iE = new InterfaceExpression();

		// variable declaration
		VariableDeclaration vD = new VariableDeclaration();
		vD.name = new Identifier("x");
		vD.type = new PrimitiveType(PrimitiveType.INT);
		iE.addDeclaration(vD);
		
		
		// other var declaration
		VariableDeclaration vD2 = new VariableDeclaration();
		vD.name = new Identifier("y");
		vD.type = new PrimitiveType(PrimitiveType.INT);
		iE.addDeclaration(vD2);
		
		
		
		
		// interface 
		add("interface { x:int; y:int; f:\\x:int:int;  };", "");






	}

}
