package com.luxlunaris.cincia.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.Compiler;

public class Test {
	
	List<Entry<String, CinciaObject>> tests;
	
	public Test() {
		tests = new ArrayList<Entry<String,CinciaObject>>();
	}


	public static void main(String[] args) {
		
		Test t = new Test();
		t.addTest("1;", new CinciaInt(1));
		t.addTest("1==1;", new CinciaBool(true));
		t.addTest("1!=1;", new CinciaBool(false));
		t.addTest("1| \\x->2*x | \\x->3*x;", new CinciaInt(6));
		
		
		
		t.runAll();
		
	}


	public CinciaObject run(String source) {
		
		Compiler compiler = new Compiler();
		Enviro enviro = new Enviro(null);
		Interpreter interpreter = new Interpreter();
		CinciaObject out = null;

		for(Ast stm : compiler.compile(source)) {
			out = interpreter.eval(stm, enviro);
		}

		return out;
	}
	
	public boolean assertEquals(String source, CinciaObject result) {
		return run(source).toString().equals(result.toString());				
	}
	
	public void addTest(String source, CinciaObject result) {
		tests.add(Map.entry(source, result));
	}
	
	public void runAll() {
		
		tests.forEach(e->{
			
			try {
				
				boolean res = assertEquals(e.getKey(), e.getValue());
				System.out.println(e.getKey()+" "+(res?"PASS!":"FAIL!"));
				
			} catch (Exception e2) {
				System.out.println(e.getKey()+" BROKEN! ");
//				e2.printStackTrace();
			}
			
		});
	}
	
	
	
	









}
