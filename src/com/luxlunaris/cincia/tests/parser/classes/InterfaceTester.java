package com.luxlunaris.cincia.tests.parser.classes;

import com.luxlunaris.cincia.frontend.ast.declarations.FunctionDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ClassExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.InterfaceExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
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

		// other variable declaration
		VariableDeclaration vD2 = new VariableDeclaration();
		vD2.name = new Identifier("y");
		vD2.type = new PrimitiveType(PrimitiveType.INT);
		iE.addDeclaration(vD2);

		// method declaration
		FunctionDeclaration fD = new FunctionDeclaration();
		fD.name = new Identifier("f");
		Signature sg = new Signature();
		sg.params =  vD;
		sg.returnType = new PrimitiveType(PrimitiveType.INT);
		fD.signature = sg;
		iE.addDeclaration(fD);
		
		
		// interface 
		add("interface { x:int; y:int; f:\\x:int:int;  };", iE.toString());



	}

}
