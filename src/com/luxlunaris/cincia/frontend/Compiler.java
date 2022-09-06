package com.luxlunaris.cincia.frontend;

import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.charstream.CinciaSytnaxException;
import com.luxlunaris.cincia.frontend.parser.Parser;
import com.luxlunaris.cincia.frontend.preprocessor.Preprocessor;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;

public class Compiler {

	/**
	 * Convert a string of source code into a list of simplified ASTs, 
	 * one for each statement in order.
	 * @param source
	 * @return
	 */
	public List<Ast> compile(String source) throws CinciaSytnaxException{
		
		Preprocessor preprocessor = new Preprocessor(source);
		CharStream charStream = new CharStream(preprocessor.process());
		TokenStream tokenStream = new TokenStream(charStream);
		Parser parser = new Parser(tokenStream);
		List<Statement> statements = parser.parse();
		List<Ast> simplifiedStatements = statements.stream().map(s->s.simplify()).collect(Collectors.toList());
		return simplifiedStatements;
		
	}
	
}
