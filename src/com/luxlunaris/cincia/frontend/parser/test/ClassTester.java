package com.luxlunaris.cincia.frontend.parser.test;

import com.luxlunaris.cincia.frontend.ast.declarations.FunctionDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.Signature;
import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AssignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ClassExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.statements.DeclarationStatement;
import com.luxlunaris.cincia.frontend.ast.statements.ExpressionStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Int;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;

public class ClassTester extends AbstractTester {

	public ClassTester() {
		
		// class with declarations, methods and assignments
		
		ClassExpression cE = new ClassExpression();
		
		VariableDeclaration vD = new VariableDeclaration();
		vD.name = new Identifier("x");
		vD.type = new PrimitiveType(PrimitiveType.INT);
		cE.addStatement(new DeclarationStatement(vD));
		
		FunctionDeclaration fD = new FunctionDeclaration();
		fD.name = new Identifier("f");
		Signature sg = new Signature();
		sg.params =  vD;
		sg.returnType = new PrimitiveType(PrimitiveType.INT);
		fD.signature = sg;
		cE.addStatement(new DeclarationStatement(fD));
		
		AssignmentExpression ase = new AssignmentExpression();
		ase.left = new Identifier("f");
		LambdaExpression lE = new LambdaExpression();
		lE.expression = new Int(1);
		Signature sg2 = new Signature();
		VariableDeclaration vD2 = new VariableDeclaration();
		vD2.name = new Identifier("x");
		sg2.params = vD2;
		lE.signature = sg2;
		ase.right = lE;
		cE.addStatement(new ExpressionStatement(ase));
 		
		add("class { x:int; f:\\x:int:int; f = \\x->1; x = 1;  };", cE.toString());

	}
}
