package com.luxlunaris.cincia.frontend.parser.test;

import com.luxlunaris.cincia.frontend.ast.expressions.RangeExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Int;

public class RangeExpTester extends AbstractTester {
	
	public RangeExpTester() {
		
		// simple range from 1 to 10
		RangeExpression rE  = new RangeExpression();
		rE.from = new Int(1);
		rE.to = new Int(10);
		add("1 to 10;", rE.toString());
		
		
		// used as a fancy index in an array
		
		
		
		add("arr[1 to 4]", );
		
		
		
	}
	
}
