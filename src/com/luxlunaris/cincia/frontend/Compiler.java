package com.luxlunaris.cincia.frontend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.charstream.CinciaSyntaxException;
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
	public List<Ast> compile(String source) throws CinciaSyntaxException{

		Preprocessor preprocessor = new Preprocessor(source);		
		CharStream charStream = new CharStream(preprocessor.process());
		TokenStream tokenStream = new TokenStream(charStream);
		Parser parser = new Parser(tokenStream);
		List<Statement> statements = parser.parse();
		List<Ast> simplifiedStatements = statements.stream().map(s->s.simplify()).collect(Collectors.toList());
		
		return simplifiedStatements;

	}


	public List<Entry<Integer, Integer>> getStatmentBounds(String source){

		List<Entry<Integer, Integer>> results = new ArrayList<Map.Entry<Integer,Integer>>();
		Preprocessor preprocessor = new Preprocessor(source);
		CharStream charStream = new CharStream(preprocessor.process());
		TokenStream tokenStream = new TokenStream(charStream);
		Parser parser = new Parser(tokenStream);

		parser.parseStatement();
		int start = 0;
		int end = charStream.getPos();
		results.add(Map.entry(start, end));

		while (!tokenStream.isEnd()) {
			parser.parseStatement();
			start = end;
			end = charStream.getPos();
			results.add(Map.entry(start, end));
		}

		return results;
	}


}
