package com.luxlunaris.cincia.frontend.parser.test;

import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ClassExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.statements.DeclarationStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;

public class ClassTester extends AbstractTester {

	public ClassTester() {
		
		// class with declarations, methods and assignments
		
		ClassExpression cE = new ClassExpression();
		
		VariableDeclaration vD = new VariableDeclaration();
		vD.name = new Identifier("x");
		vD.type = new PrimitiveType(PrimitiveType.INT);
		cE.addStatement(new DeclarationStatement(vD));
		
		
		
		add("class { x:int; f:\\x:int:int; f = \\x->1; x = 1;  };", "");

	}
}
