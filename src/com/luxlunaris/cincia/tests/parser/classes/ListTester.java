package com.luxlunaris.cincia.tests.parser.classes;

import java.util.Arrays;

import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AddExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ListComprehension;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ListExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Int;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

public class ListTester extends AbstractTester {

	public ListTester() {

		// list literal expression
		ListExpression lE = new ListExpression();
		MultiExpression muEx = new MultiExpression();
		muEx.expressions = Arrays.asList(new Int(1), new Int(2), new Int(3), new Int(4));
		lE.elements = muEx;
		add("[1,2,3,4];", lE.toString());
		
		// empty list 
		muEx.expressions = Arrays.asList();
		lE.elements = muEx;
		add("[];", lE.toString());
		
		// one element list
		lE.elements = new Int(1);
		add("[1];", lE.toString());
		

		// list comprehension
		ListComprehension lC = new ListComprehension();
		AddExpression adE = new AddExpression();
		adE.left = new Identifier("x");
		adE.right = new Int(1);
		adE.op = Operators.PLUS;
		lC.element = adE;
		lC.source =  new Identifier("x");
		lC.iterable =  new Identifier("l");
		add("[x+1 for x in l];", lC.toString());

	}

}
