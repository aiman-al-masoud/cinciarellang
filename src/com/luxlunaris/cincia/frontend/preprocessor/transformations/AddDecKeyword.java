package com.luxlunaris.cincia.frontend.preprocessor.transformations;


import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.parser.Parser;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;

/**
 * Automatically add the DEC keyword to a statement if it's found 
 * to be a declaration statement.
 *
 */
public class AddDecKeyword {
	
	public static String apply(final String statement) {
		
		String dec = Keywords.DEC.toString();
		String ifDec = statement.replaceFirst("\\s*"+dec+"\\s*", "");
		ifDec  = dec+" "+ifDec ;
		
		try {
			// try forcing the compiler to parse this statement as a declaration ...
			Parser parser = new Parser(new TokenStream(new CharStream(ifDec)));
			parser.parse();
			return ifDec;
		}catch (Exception e) {
			// if it fails, then it's not a declaration!
			return statement;
		}
		
		
	}
	
}
