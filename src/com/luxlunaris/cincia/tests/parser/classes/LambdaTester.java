package com.luxlunaris.cincia.tests.parser.classes;

import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AddExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AssignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.ast.statements.ExpressionStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.ReturnStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Int;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

public class LambdaTester extends AbstractTester {

	public LambdaTester() {

		// lambda expression
		LambdaExpression lex = new LambdaExpression();
		lex.expression = new Int(1);
		Signature sg = new Signature();
		VariableDeclaration vD = new VariableDeclaration();
		vD.name = new Identifier("x");
		sg.params = vD;
		lex.signature = sg;
		add("\\x->1;", lex.toString());


		// lambda expression with code block
		lex.expression = null;
		CompoundStatement co = new CompoundStatement();
		AssignmentExpression one = new AssignmentExpression();
		AssignmentExpression two = new AssignmentExpression();
		ReturnStatement three = new ReturnStatement();
		one.left = new Identifier("x");
		one.right = new Int(1);
		AddExpression aex = new AddExpression();
		aex.left = new Identifier("x");
		aex.right  = new Int(1);
		aex.op = Operators.PLUS;
		two.left = new Identifier("y");
		two.right = aex;
		three.expression = new Identifier("y");
		co.add(new ExpressionStatement(one));
		co.add(new ExpressionStatement(two));
		co.add(three);
		lex.block =co;
//		lex.modifiers.add(Modifiers.PURE);
		add("rdout \\x->{ x = 1;y=x+1;return y; };", lex.toString());

	}

}
