package com.luxlunaris.cincia.backend;

import java.util.List;
import java.util.Scanner;
import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.Compiler;

public class Repl {

	protected Enviro enviro;
	protected Interpreter interpreter;
	protected Scanner scanner;

	public static void main(String[] args) {
		Repl r = new Repl();
		r.mainLoop();
	}

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

	protected static CinciaObject printWrapper(List<CinciaObject> args) {
		args.forEach(e->{System.out.print(e);});
		System.out.println();
		return null;
	}

	protected void eval(String source, Enviro enviro) {

		List<Ast> statements = new Compiler().compile(source);

		statements.forEach(s -> {

			try {

				CinciaObject out = interpreter.eval(s, enviro);	

				if(out!=null) {
					System.out.println(out);
				}

			}catch (Exception e) {
				System.out.println(e.getClass().getSimpleName()+": "+e.getMessage());
				e.printStackTrace();
			}

		});

	}

}
