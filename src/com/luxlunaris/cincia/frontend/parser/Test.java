package com.luxlunaris.cincia.frontend.parser;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;

public class Test {
	
	public static void main(String[] args) {
		
		String source = "1;";
		source = "x == 1 ? 3 : 4;";
		CharStream cS = new CharStream(source);
		TokenStream tS = new TokenStream(cS);		
		Parser p  = new Parser(tS);
		List<Statement> statements = p.parse();
		System.out.println(statements.get(0));
		
		
	}

	
	
}
