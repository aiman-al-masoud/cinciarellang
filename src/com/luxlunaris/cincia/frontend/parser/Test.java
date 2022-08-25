package com.luxlunaris.cincia.frontend.parser;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.ExpressionStatement;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;

public class Test {
	
	public static void main(String[] args) {
		
		String source = "1;";
		source = "f  = \\x:int -> 1;";
		source = "f  = \\x -> 1;";
		source = "dec get final static private foo:int, x:float;";
		source = "dec get final static private foo:int;";
		source = "x == 1 ? 3 : 4;";
		source = "a = b = c = 1;";
		source = "x = z = {'y' : 222, 'capra' : 1, 'buruf' : 'hallo123' };";
		source = "x = [1,2,3];";
		source = "x[0] = z = a = [1,2,3];";
		source = "x = z = {1:2 , * x, 'capra': 3212, };";
		source = "x = { 1 : 2 };";
		source = "x = { 1-1*1 : 2 };";

		

		CharStream cS = new CharStream(source);
		TokenStream tS = new TokenStream(cS);		
		Parser p  = new Parser(tS);
		List<Statement> statements = p.parse();
		Statement s = statements.get(0);
		System.out.println(((ExpressionStatement)s).expression.simplify());
	}

	
	
}
