package com.luxlunaris.cincia.backend.shell;

import java.util.List;
import java.util.Scanner;

import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.backend.callables.CinciaFunction;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interpreter.Interpreter;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.primitives.CinciaString;
import com.luxlunaris.cincia.backend.stdlib.io.Print;
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
		enviro.set("print", new Print());
	}

	public void mainLoop() {

		while(true) {
			System.out.print("cincia> ");
			String source = scanner.nextLine()+";";
			eval(source, enviro);
		}

	}

	protected void eval(String source, Enviro enviro) {

		try {

			List<Ast> statements = new Compiler().compile(source);

			statements.forEach(s -> {

				CinciaObject out = interpreter.eval(s, enviro);	

				if(out!=null) {
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
