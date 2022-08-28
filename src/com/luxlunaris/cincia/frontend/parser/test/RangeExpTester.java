package com.luxlunaris.cincia.frontend.parser.test;

import com.luxlunaris.cincia.frontend.ast.expressions.RangeExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Int;

public class RangeExpTester extends AbstractTester {
	
	public RangeExpTester() {
		
		RangeExpression rE  = new RangeExpression();
		rE.from = new Int(1);
		rE.to = new Int(10);
		
		add("1 to 10;", rE.toString());
		
		
		
		
	}
	
}
