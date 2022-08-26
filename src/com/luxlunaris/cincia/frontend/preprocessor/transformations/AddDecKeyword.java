package com.luxlunaris.cincia.frontend.preprocessor.transformations;


import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.parser.Parser;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;


public class AddDecKeyword {
	
	public static String apply(final String statement) {
		
		String dec = Keywords.DEC.toString();
		String ifDec = statement.replaceFirst("\\s*"+dec+"\\s*", "");
		ifDec  = dec+" "+ifDec ;
		
		try {
			Parser parser = new Parser(new TokenStream(new CharStream(ifDec)));
			parser.parse();
			return ifDec;
		}catch (Exception e) {
			return statement;
		}
		
		
	}
	
}
