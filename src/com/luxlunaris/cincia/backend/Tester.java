package com.luxlunaris.cincia.backend;

import java.io.IOException;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.Compiler;
import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;

public class Tester {

	final static boolean ONLY_FIRST_BROKEN = false; // only show the first failing stacktrace and stop
	final static String ROOT = "./tests";

	public static void main(String[] args) throws IOException{


		ListDir.listDir(ROOT)
		.stream()
		.map(f->ROOT+"/"+f)
		.map(f->new SingleTest(f, Tester.readFile(f)))
		.map(t->Tester.runTest(t))
		.sorted((t1,t2)->t1.outcome -t2.outcome) //BROKEN first
		.map(r->Tester.printResult(r))
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

	public static SingleTestResult runTest(SingleTest test) {

		CinciaObject out = null;

		try {
			Compiler compiler = new Compiler();
			Enviro enviro = new Enviro(null);
			Interpreter interpreter = new Interpreter();


			for(Ast stm : compiler.compile(test.source)) {

				out = interpreter.eval(stm, enviro);

			}

		} catch (Exception e) {

			return new SingleTestResult(test.filename, SingleTestResult.BROKEN, e);
		}

		boolean success = out!=null && out.__eq__(new CinciaBool(true)).__bool__();
		return new SingleTestResult(test.filename, success? SingleTestResult.SUCCESS : SingleTestResult.FAIL);

	}

	public static SingleTestResult printResult(SingleTestResult result) {
		
		if( ONLY_FIRST_BROKEN && (result.outcome == SingleTestResult.BROKEN) ) {
			System.out.println(result.filename);
			result.exception.printStackTrace();
			System.exit(1);
		}

		System.out.println(result);
		return result;
	}








}
