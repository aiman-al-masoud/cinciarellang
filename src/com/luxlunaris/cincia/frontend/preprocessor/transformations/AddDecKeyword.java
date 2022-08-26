package com.luxlunaris.cincia.frontend.preprocessor.transformations;


import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.parser.Parser;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;


public class AddDecKeyword {
	
	
	
	public static String applyLine(final String statement) {
		
//		String DEC = "dec";
//		String output = statement;		
//		String identifier = "([a-zA-Z_][a-zA-Z0-9_]*)";
//		String modifiers = "("+identifier+"\\s*)*";
//		String type = ":\\s*"+"(\\\\)*\\s*"+identifier+"\\s*(:"+identifier+")*";
//		String singleDec = modifiers+"\\s*"+identifier+"\\s*"+type;
//		String multiDec = "("+singleDec+"\\s*,"+singleDec+")*";
//		
//		if(  statement.matches(singleDec+";") || statement.matches(multiDec+";") ) {
//			output = statement.replaceFirst("\\s*"+DEC+"\\s*", "");
//			output = "dec "+output;
//		}

		
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
