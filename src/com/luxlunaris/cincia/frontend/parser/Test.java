package com.luxlunaris.cincia.frontend.parser;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;

public class Test {
	
	public static void main(String[] args) {
		
		String source = "1;";
		source = "x == 1 ? 3 : 4;";
		source = "a = b = c = 1;";
		source = "x = [1,2,3];";
		source = "f  = \\x -> 1;";
//		source = "x = {'y' : 222 };";
		source = "dec x:int;";

		

		CharStream cS = new CharStream(source);
		TokenStream tS = new TokenStream(cS);		
		Parser p  = new Parser(tS);
		List<Statement> statements = p.parse();
		System.out.println(statements.get(0));
		
		
	}

	
	
}
