package com.luxlunaris.cincia.frontend.tokenstream;

import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.tokens.Token;

public class Test {
	
	
	public static void main(String[] args) {
		

		String source = "x = 1 + 1"; 
		source = "//comment\n /*comment*/ x = /*com*/ 1 + 1 \n x=1 \n f=\\x->1;"; 
		source = "x = 'ciao mondo'";
		source = "final x = 'ciao mondo'";
		source = "private x: int = 1 ";
		source = "f = f\"df\";";


		
		CharStream cStream = new CharStream(source);
		TokenStream tStream = new TokenStream(cStream);
		Token token;
		tStream.next();
		
		while(!tStream.isEnd()) {
			token = tStream.peek();
			System.out.println(token);
			tStream.next();
		}
		
		
	}
	
}
