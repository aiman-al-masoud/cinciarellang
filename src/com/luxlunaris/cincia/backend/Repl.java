package com.luxlunaris.cincia.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.parser.Parser;
import com.luxlunaris.cincia.frontend.preprocessor.Preprocessor;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;

public class Repl {

	public static void main(String[] args) {

		Enviro enviro = new Enviro(null);	
		Interpreter interpreter = new Interpreter();
		Scanner scanner;
		enviro.set("print", new CinciaFunction(Repl::printWrapper));
		
	
		
		while(true) {
			System.out.print(">");
			scanner = new Scanner(System.in);
			String source = scanner.nextLine()+";";
			Preprocessor preprocessor = new Preprocessor(source);
			CharStream charStream = new CharStream(preprocessor.process());
			TokenStream tokenStream = new TokenStream(charStream);
			Parser parser = new Parser(tokenStream);
			List<Statement> statements = parser.parse();
			statements = statements.stream().map(s->s.simplify()).collect(Collectors.toList());
			statements.forEach(s -> {
				CinciaObject out = interpreter.eval(s, enviro);	
				
				if(out!=null) {
					System.out.println(out);
				}
				
			});
		}
		
	}
	
	protected static CinciaObject printWrapper(List<CinciaObject> args) {
		args.forEach(e->{System.out.print(e);});
		System.out.println();
//		System.out.println(args.get(0));
		return null;
	}
	

}
