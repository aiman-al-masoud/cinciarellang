package com.luxlunaris.cincia.frontend.preprocessor.transformations;

public class AddSemicols {
	
	public static String apply(String statement) {
		return statement.replace("}\n", "};\n");
	}

}
