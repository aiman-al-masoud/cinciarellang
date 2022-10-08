package com.luxlunaris.cincia.tests.parser.classes;

import com.luxlunaris.cincia.frontend.ast.expressions.RangeExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.IndexedExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.IntToken;

public class RangeExpTester extends AbstractTester {
	
	public RangeExpTester() {
		
		// simple range from 1 to 10
		RangeExpression rE  = new RangeExpression();
		rE.from = new IntToken(1);
		rE.to = new IntToken(10);
		add("1 to 10;", rE.toString());
		
		
		// used as a fancy index in an array
		IndexedExpression iE = new IndexedExpression();
		iE.indexable = new Identifier("arr");
		iE.index  = rE;
		add("arr[1 to 10]", iE.toString());
		
	}
	
}
