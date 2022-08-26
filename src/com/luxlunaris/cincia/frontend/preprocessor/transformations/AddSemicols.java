package com.luxlunaris.cincia.frontend.preprocessor.transformations;

import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;

public class AddSemicols {
	
	public static String apply(String source) {
		
		
		return source
				
				// add null op as first stm of class, otherwise if first statement 
				// was a declaration it wouldn't get recognized by AddDecKeyword.
				.replaceFirst("(.*("+Keywords.CLASS+"|"+Keywords.INTERFACE+").*)\\{", "$1{ 0;")
				
				.replace("}\n", "};\n");
	}

}
