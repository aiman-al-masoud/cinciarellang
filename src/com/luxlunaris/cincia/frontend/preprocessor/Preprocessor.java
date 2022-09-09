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
		
		// auto-add semicols (where possible)
		String processedSource = AddSemicols.apply(source);
		
		// split statements
		String[] statements = processedSource.split(";");
				
		// apply per-statement transformation
		processedSource = Arrays.stream(statements)
				.filter(s->!s.isBlank()) // remove blank statements
				.map(s->s+";") // re-add semicols
				.map(AddDecKeyword::apply) // auto-add dec keyword
				.reduce((s1, s2)->s1+s2) // rejoin into a string
				.get();  
		
		return processedSource;
	}
	
}
