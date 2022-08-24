package com.luxlunaris.cincia.frontend.parser;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;

public class Test {
	
	public static void main(String[] args) {
		
		String source = "x = 10;";
		CharStream cS = new CharStream(source);
		TokenStream tS = new TokenStream(cS);
//		tS.next();
//		while(!tS.isEnd()) {
//			System.out.println(tS.peek());
//			tS.next();
//		}
		
		Parser p  = new Parser(tS);
		
		List<Statement> statements = p.parse();
		
		System.out.println(statements);
		
		
	}

	
	
}
