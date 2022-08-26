package com.luxlunaris.cincia.frontend.preprocessor.transformations;

import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;

public class AddDecKeyword {
	
	
	
	public static String applyLine(String statement) {
		
		String DEC = "dec";
		String output = statement;		
		String identifier = "([a-zA-Z_][a-zA-Z0-9_]*)";
		String modifiers = "("+identifier+"\\s*)*";
		String type = ":"+"(\\\\)*\\s*"+identifier+"\\s*(:"+identifier+")*";
		String singleDec = modifiers+"\\s*"+identifier+"\\s*"+type;
		String multiDec = "("+singleDec+"\\s*,"+singleDec+")*";
		
		if(  statement.matches(singleDec+";") || statement.matches(multiDec+";") ) {
			output = statement.replaceFirst("\\s*"+DEC+"\\s*", "");
			output = "dec "+output;
		}
		
		
		return output;
		
	}
	
}
