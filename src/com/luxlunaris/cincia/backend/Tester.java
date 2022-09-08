package com.luxlunaris.cincia.backend;

import java.io.IOException;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.Compiler;
import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;


public class Tester {

	public static void main(String[] args) throws IOException{


		final String ROOT = "./tests";

		ListDir.listDir(ROOT)
		.stream()
		.map(f->ROOT+"/"+f)
		.map(f->new SingleTest(f, Tester.readFile(f)))
		.map(t->Tester.runTest(t))
		.collect(Collectors.toList());

	}


	public static String readFile(String path) {

		try {
			return ReadFile.readTextFile(path);
		} catch (IOException e) {
			System.err.println(path+" file doesn't exist!");
		}

		return null;
	}

	public static boolean runTest(SingleTest test) {

		Compiler compiler = new Compiler();
		Enviro enviro = new Enviro(null);
		Interpreter interpreter = new Interpreter();
		CinciaObject out = null;

		for(Ast stm : compiler.compile(test.source)) {
			out = interpreter.eval(stm, enviro);
		}

		System.out.println(test.filename+" "+out);
		return false;
	}
	
	







}
