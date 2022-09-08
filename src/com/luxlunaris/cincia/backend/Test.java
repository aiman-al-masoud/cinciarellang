package com.luxlunaris.cincia.backend;

import java.util.List;


import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.parser.Parser;
import com.luxlunaris.cincia.frontend.preprocessor.Preprocessor;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;

import com.luxlunaris.cincia.frontend.Compiler;

public class Test {


	public static void main(String[] args) {
		
		System.out.println(assertEquals("1;", new CinciaInt(1)));
		System.out.println(assertEquals("1==1;", new CinciaBool(true)));
//		System.out.println(assertEquals("1!=1;", new CinciaBool(false)));


	}


	public static CinciaObject run(String source) {
		
		Compiler compiler = new Compiler();
		Enviro enviro = new Enviro(null);
		Interpreter interpreter = new Interpreter();
		CinciaObject out = null;

		for(Ast stm : compiler.compile(source)) {
			out = interpreter.eval(stm, enviro);
		}

		return out;
	}
	
	public static boolean assertEquals(String source, CinciaObject result) {
		return run(source).toString().equals(result.toString());				
	}
	
	
	
	









}
