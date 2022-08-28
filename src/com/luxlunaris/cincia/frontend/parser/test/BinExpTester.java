package com.luxlunaris.cincia.frontend.parser.test;


import com.luxlunaris.cincia.frontend.ast.expressions.binary.AddExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.ComparisonExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.MulExpression;
import com.luxlunaris.cincia.frontend.ast.statements.ExpressionStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Int;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

public class BinExpTester extends AbstractTester{
	
	
	public BinExpTester() {
		
		// mul expression
		MulExpression mE = new MulExpression();
		mE.left = new Int(1);
		mE.right = new Int(2);
		mE.op = Operators.ASTERISK;
		ExpressionStatement ex = new ExpressionStatement(mE);
		add("1 * 2;", ex.toString());
		mE.op = Operators.DIV;
		add("1 / 2;", mE.toString());
		mE.op = Operators.MOD;
		add("1 % 2;", mE.toString());

		//add expression
		AddExpression aE = new AddExpression();
		aE.left = new Int(1);
		aE.right = new Int(1);
		aE.op = Operators.PLUS;
		add("1 + 1;", aE.toString());
		aE.op = Operators.MINUS;
		add("1 - 1;", aE.toString());

		// comparison expression
		ComparisonExpression cE = new ComparisonExpression();
		cE.left = new Int(2);
		cE.right = new Int(1);
		cE.op = Operators.COMPARE;
		add("2 == 1;", cE.toString());
		cE.op = Operators.LTE;
		add("2 <= 1;", cE.toString());
		cE.op = Operators.GTE;
		add("2 >= 1;", cE.toString());
		cE.op = Operators.NE;
		add("2 != 1;", cE.toString());
		cE.op = Operators.LT;
		add("2 < 1;", cE.toString());
		cE.op = Operators.GT;
		add("2 > 1;", cE.toString());
		ComparisonExpression ccE = new ComparisonExpression();
		ccE.left = cE;
		ccE.right = new Int(3);
		ccE.op = Operators.GT;
		add("2 > 1 > 3;", ccE.toString());

	}
	
	

}
