package com.luxlunaris.cincia.tests.parser.classes;

import java.util.Arrays;

import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.CalledExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.DotExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.IndexedExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.ReassignmentExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.IntToken;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

public class PostfixExpTester extends AbstractTester{

	public PostfixExpTester() {

		// call expression
		CalledExpression caE = new CalledExpression();
		caE.callable = new Identifier("f");
		MultiExpression muE = new MultiExpression();
		muE.expressions = Arrays.asList(new Identifier("a"), new Identifier("b"), new IntToken(1));
		caE.args = muE;
		add("f(a, b, 1);", caE.toString());

		// dot expression
		DotExpression dE = new DotExpression();
		dE.left = new Identifier("a");
		dE.right = new Identifier("b");
		DotExpression dE2 = new DotExpression();
		dE2.left = dE;
		dE2.right = new Identifier("c");
		DotExpression dE3 = new DotExpression();
		dE3.left = dE2;
		dE3.right = new Identifier("d");
		add("a.b.c.d", dE3.toString());

		// indexed expression
		IndexedExpression iE = new IndexedExpression();
		iE.indexable = new Identifier("x");
		iE.index = new IntToken(1);
		IndexedExpression iE2 = new IndexedExpression();
		iE2.indexable = iE;
		iE2.index = new IntToken(2);
		add("x[1][2]", iE2.toString());

		// reassignment expression
		ReassignmentExpression rE = new ReassignmentExpression();
		rE.left = new Identifier("x");
		rE.right = new IntToken(1);
		rE.op = Operators.PLUS_ASSIGN;
		add("x+=1", rE.toString());
		rE.op = Operators.DIV_ASSIGN;
		add("x/=1", rE.toString());

	}

}
