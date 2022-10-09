package com.luxlunaris.cincia.tests.interpreter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.callables.CinciaFunction;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interpreter.Interpreter;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.primitives.CinciaBool;
import com.luxlunaris.cincia.backend.primitives.CinciaString;
import com.luxlunaris.cincia.backend.stdlib.concurrency.Promise;
import com.luxlunaris.cincia.frontend.Compiler;
import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.tests.AnsiColors;
import com.luxlunaris.cincia.tests.ListDir;
import com.luxlunaris.cincia.tests.ReadFile;

/**
 * 
 * These tests operate on a set of cinciarellang files, and assume that the last 
 * statement of each file contains a boolean expression, which evaluates to true 
 * iff that test is considered passed.
 * 
 * @author aiman
 */
public class Tester {

	final static boolean ONLY_FIRST_BROKEN = true; // only show the first failing stacktrace and stop
	final static String ROOT = "./tests";
	final static String ALL = "*"; 
	final static List<String> tags = Arrays.asList(ALL);
//			final static List<String> tags = Arrays.asList("immutable-object");

	public static void main(String[] args) throws IOException{

		ListDir.listDir(ROOT)
		.stream()
		.map(f->new SingleTest(f, Tester.readFile(f)))
		.filter(t -> hasTag(tags, t.filename) )
		.map(t->Tester.runTest(t))
		.sorted((t1,t2)->t1.outcome -t2.outcome) //BROKEN tests first
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
			Enviro enviro  = Enviro.getTopLevelEnviro();
			var workingDir = new File(test.filename).getParent();
			enviro.set(Enviro.WORKING_DIR, new CinciaString(workingDir));			
			enviro.set("print", new CinciaFunction(e->{System.out.print(e  ); return null;}));
			Interpreter interpreter = new Interpreter();

			for(Ast stm : compiler.compile(test.source)) {
				out = interpreter.eval(stm, enviro);
			}

		} catch (Exception e) {
			return new SingleTestResult(test.filename, SingleTestResult.BROKEN, e);
		}

		boolean success = out!=null && out.__eq__(new CinciaBool(true)).__bool__().toJava();
		return new SingleTestResult(test.filename, success? SingleTestResult.SUCCESS : SingleTestResult.FAIL);

	}

	public static SingleTestResult printResult(SingleTestResult result) {

		if( ONLY_FIRST_BROKEN && (result.outcome == SingleTestResult.BROKEN) ) {
			System.out.println(AnsiColors.warn("broken: "+result.filename));
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
