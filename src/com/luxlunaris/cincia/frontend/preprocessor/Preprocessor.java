package com.luxlunaris.cincia.frontend.preprocessor;

import java.util.Arrays;

import com.luxlunaris.cincia.frontend.preprocessor.transformations.AddDecKeyword;
import com.luxlunaris.cincia.frontend.preprocessor.transformations.AddSemicols;

public class Preprocessor {
	
	protected String source;
	
	public Preprocessor(String source) {
		this.source = source;
	}
	
	public String process() {
		
		// add semicols automatically where possible
		String processedSource = AddSemicols.apply(source);
		
		// split statements
		String[] statements = processedSource.split(";");
		
		// apply per-statement transformation
		String t = Arrays.stream(statements)
				.map(s->s+";")
				.map(AddDecKeyword::apply)
				.reduce((s1, s2)->s1+s2).get();  
		
		return t;
	}
	
}
