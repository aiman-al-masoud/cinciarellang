package com.luxlunaris.cincia.frontend.preprocessor;

import java.util.Arrays;
import java.util.Optional;

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
		Optional<String> processedSource2 = Arrays.stream(statements)
				.filter(s->!s.isBlank()) // remove blank statements
				.map(s->s+";") // re-add semicols
				.map(AddDecKeyword::apply) // auto-add dec keyword
				.reduce((s1, s2)->s1+s2); // rejoin into a string
				
		
		return processedSource2.isPresent()? processedSource2.get() :"";
	}
	
}
