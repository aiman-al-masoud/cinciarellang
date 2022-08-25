package com.luxlunaris.cincia.frontend.preprocessor.transformations;

public class AddDecKeyword {
	
	public static String applyLine(String statement) {
		
		String output = statement;
		
//		statement.replace("|", "pipe").
		String identifier = "([a-zA-Z_][a-zA-Z0-9_]*)";
		String modifiers = "("+identifier+"\\s*)*";
		String type = ":"+identifier;//+"([]|{})*";
		
		
		String singleDec = modifiers+"\\s+"+identifier+"\\s*"+type;
		String multiDec = "("+singleDec+"\\s*,"+singleDec+")*";
		
		if(  statement.matches(singleDec+";") || statement.matches(multiDec+";") ) {
			output = statement.replaceFirst("\\s*dec\\s*", "");
			output = "dec "+output;
		}
		
		return output;
		
	}
	
}
