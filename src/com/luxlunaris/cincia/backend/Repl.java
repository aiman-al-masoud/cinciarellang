package com.luxlunaris.cincia.backend;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.parser.Parser;
import com.luxlunaris.cincia.frontend.preprocessor.Preprocessor;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;
import com.luxlunaris.cincia.frontend.Compiler;

public class Repl {

	Enviro enviro;
	Interpreter interpreter;
	Scanner scanner;

	public Repl() {
		enviro = new Enviro(null);	
		interpreter = new Interpreter();
		scanner = new Scanner(System.in);
		enviro.set("print", new CinciaFunction(Repl::printWrapper));
	}

	public void mainLoop() {

		while(true) {
			System.out.print(">");
			String source = scanner.nextLine()+";";
			eval(source, enviro);
		}

	}


	public static void main(String[] args) {

		Repl r = new Repl();
		r.mainLoop();

	}

	protected static CinciaObject printWrapper(List<CinciaObject> args) {
		args.forEach(e->{System.out.print(e);});
		System.out.println();
		return null;
	}

	protected void eval(String source, Enviro enviro) {

//		Preprocessor preprocessor = new Preprocessor(source);
//		CharStream charStream = new CharStream(preprocessor.process());
//		TokenStream tokenStream = new TokenStream(charStream);
//		Parser parser = new Parser(tokenStream);
//		List<Statement> statements = parser.parse();
//		statements = statements.stream().map(s->s.simplify()).collect(Collectors.toList());
		
		
		List<Ast> statements = new Compiler().compile(source);
		
		statements.forEach(s -> {
			CinciaObject out = interpreter.eval(s, enviro);	

			if(out!=null) {
				System.out.println(out);
			}

		});

	}

}
