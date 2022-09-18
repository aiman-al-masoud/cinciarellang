package com.luxlunaris.cincia.backend.shell;

import java.util.List;
import java.util.Scanner;
import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.backend.callables.CinciaFunction;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interpreter.Interpreter;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.primitives.CinciaString;
import com.luxlunaris.cincia.backend.throwables.CinciaException;
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
		enviro = Enviro.getTopLevelEnviro();
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
		// TODO: more elegant solution to removing "s when printing strings!
		args.forEach(e->{System.out.print(   e instanceof CinciaString ? ((CinciaString)e).toJava()   :e ); System.out.print(" ");  });
		System.out.println();
		return null;
	}

	protected void eval(String source, Enviro enviro) {

		try {

			List<Ast> statements = new Compiler().compile(source);

			statements.forEach(s -> {

				CinciaObject out = interpreter.eval(s, enviro);	

				if(out!=null) {
					System.out.println("type: "+out.getType());
					System.out.println(out);
				}

			});

		}catch (CinciaException e) {
//			System.out.println(e.getClass().getSimpleName()+": "+e.getMessage());
//			e.printStackTrace();
			System.out.println(e.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
