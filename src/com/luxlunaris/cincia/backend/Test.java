package com.luxlunaris.cincia.backend;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.parser.Parser;
import com.luxlunaris.cincia.frontend.preprocessor.Preprocessor;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;

public class Test {
	
	
	public static void main(String[] args) {
		
		
		String source = "x = 1+1+2+1*3;";
		source = "x = class { y = 1; };"; //TODO?
		
		source = "x = (\\x->x)(1);";
				
		Preprocessor preprocessor = new Preprocessor(source);
		CharStream charStream = new CharStream(preprocessor.process());
		TokenStream tokenStream = new TokenStream(charStream);
		Parser parser = new Parser(tokenStream);
		List<Statement> statements = parser.parse();
		Enviro enviro = new Enviro(null);
		Interpreter interpreter = new Interpreter();
		
		Ast ast = statements.get(0).simplify();
		
		interpreter.eval(ast, enviro);		
		
		System.out.println(enviro.get("x"));
		
		
	}
	
	
	
	
	
}
