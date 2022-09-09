package com.luxlunaris.cincia.tests.interpreter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interpreter.Interpreter;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.primitives.CinciaBool;
import com.luxlunaris.cincia.frontend.Compiler;
import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.tests.ListDir;
import com.luxlunaris.cincia.tests.ReadFile;

public class Tester {

	final static boolean ONLY_FIRST_BROKEN = false; // only show the first failing stacktrace and stop
	final static String ROOT = "./tests";
	final static String ALL = "*"; 
	final static List<String> tags = Arrays.asList(ALL);//"ref", "pipes", "minus", "immutable");

	public static void main(String[] args) throws IOException{


		ListDir.listDir(ROOT)
		.stream()
		.map(f->ROOT+"/"+f)
		.map(f->new SingleTest(f, Tester.readFile(f)))
		.filter(t -> hasTag(tags, t.filename) )
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
	
	public static boolean hasTag(List<String> tags, String filename) {
		return tags.stream().anyMatch(tag->filename.contains(tag) || tag.equals(ALL));
	}








}
