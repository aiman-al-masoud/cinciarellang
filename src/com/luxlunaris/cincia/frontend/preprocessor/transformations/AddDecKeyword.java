package com.luxlunaris.cincia.frontend.preprocessor.transformations;


import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.parser.Parser;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;


public class AddDecKeyword {
	
	public static String apply(final String statement) {
		
		String DEC = "dec";
		String ifDec = statement.replaceFirst("\\s*"+DEC+"\\s*", "");
		ifDec  = DEC+" "+ifDec ;
		
		try {
			Parser parser = new Parser(new TokenStream(new CharStream(ifDec)));
			parser.parse();
			return ifDec;
		}catch (Exception e) {
			return statement;
		}
		
		
	}
	
}
