package com.luxlunaris.cincia.frontend.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.preprocessor.Preprocessor;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;

public class Test {
	
	static final String OKGREEN = "\033[92m";
	static final String WARNING = "\033[93m";
	static final String FAIL = "\033[91m";
	static final String ENDC = "\033[0m";
	
	
	public static void main(String[] args) {
		
		
		Map<String, String> tests = new HashMap<String, String>();
		tests.put("1;", "1");
		tests.put("f  = \\x:int -> 1;", "(f = [] \\([] x:INT) : null->1)");
		tests.put("x = z = {'y' : 222, 'capra' : 1, 'buruf' : 'hallo123' };", "(x = (z = {'y' : 222, 'capra' : 1, 'buruf' : 'hallo123'}))");
		tests.put("x = [1,2,3];", "(x = [1, 2, 3])");
		tests.put("x[0] = z = a = [1,2,3];", "(x[0] = (z = (a = [1, 2, 3])))");
		tests.put("x = { 1 : 2 };", "(x = {1 : 2})");
		tests.put("x = { 1-1*1 : 2 };", "(x = {(1 MINUS (1 ASTERISK 1)) : 2})");
		tests.put("f = \\x -> 1;", "(f = [] \\([] x:null) : null->1)");
		tests.put("a = b = c = 1;", "(a = (b = (c = 1)))");
		tests.put("x = z = {1:2 , * x, 'capra': 3212, };", "(x = (z = {1 : 2, 'capra' : 3212, *(x)}))");
		tests.put("for i in x{ x = do(x); x+=1;  }", "for [i] in x{(x = do(x)); (x PLUS_ASSIGN 1)}");
		tests.put("get final static private foo:int, x:float;", "[([GET, FINAL, STATIC, PRIVATE] foo:INT), ([] x:FLOAT)]");
		tests.put("get final static private foo:int;", "([GET, FINAL, STATIC, PRIVATE] foo:INT)");
		tests.put("f:\\x:int:int;", "([] f \\([] x:INT) : INT)");
		tests.put("x:int|float;", "([] x:INT | FLOAT)");
		tests.put("dec x:int|float;", "([] x:INT | FLOAT)");
		tests.put("x == 1 ? 3 : 4;", "((x COMPARE 1)?3:4)");
		tests.put("match x{case 1:\n return 1; case 2: return 2;}", "match [case 1 {return 1;}, case 2 {return 2;}] null");
		tests.put("while true{ x+=1;y+=2; }\n x+=1;", "while true then {(x PLUS_ASSIGN 1); (y PLUS_ASSIGN 2)}");

		
		for(Entry<String, String> e : tests.entrySet()) {
			
			Preprocessor pP = new Preprocessor(e.getKey());
			CharStream cS = new CharStream(pP.process());
			TokenStream tS = new TokenStream(cS);		
			
			try {
				Parser p  = new Parser(tS);
				Statement s = p.parse().get(0).simplify();
				System.out.println(e.getKey()+" "+ ( e.getValue().equals(s.toString()) ? ok("OK") : fail("FAIL") ));
//				System.out.println(s);
			}catch (Exception exception) {
				System.out.println(fail(e.getKey()+" "+exception.getMessage()+" FAIL"));;
//				exception.printStackTrace();
			}
			
		}

		
		
	}
	
	public static String ok(String s) {
		return OKGREEN+s+ENDC;
	}
	
	public static String fail(String s) {
		return FAIL+s+ENDC;
	}

	
	
}
