package com.luxlunaris.cincia.frontend.preprocessor.transformations;

public class AddSemicols {
	
	public static String apply(String source) {
		
		return source
				
				.replace("}\n", "};\n");
	}

}
