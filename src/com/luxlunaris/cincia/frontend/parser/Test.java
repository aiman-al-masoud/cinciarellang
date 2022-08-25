package com.luxlunaris.cincia.frontend.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;

public class Test {
	
	public static void main(String[] args) {
		
		Map<String, String> tests = new HashMap<String, String>();
		tests.put("1;", "1;");
		tests.put("f  = \\x:int -> 1;", "");
		tests.put("x = z = {'y' : 222, 'capra' : 1, 'buruf' : 'hallo123' };", "");
		tests.put("x = [1,2,3];", "");
		tests.put("x[0] = z = a = [1,2,3];", "");
		tests.put("x = { 1 : 2 };", "");
		tests.put("x = { 1-1*1 : 2 };", "");
		tests.put("f = \\x -> 1;", "");
		tests.put("a = b = c = 1;", "");
		tests.put("x = z = {1:2 , * x, 'capra': 3212, };", "");
		tests.put("for i in x{ x = do(x); x+=1;  }", "");
		tests.put("dec get final static private foo:int, x:float;", "");
		tests.put("dec get final static private foo:int;", "");
		tests.put("dec f:\\x:int:int;", "");
		tests.put("x == 1 ? 3 : 4;", "");
		tests.put("match x{case 1: return 1; case 2: return 2;}", "");
		
		
		for(Entry<String, String> e : tests.entrySet()) {
			
			CharStream cS = new CharStream(e.getKey());
			TokenStream tS = new TokenStream(cS);		
			Parser p  = new Parser(tS);
			List<Statement> statements = p.parse();
			Statement s = statements.get(0);
			System.out.println(s.simplify());
		}

		
		
	}

	
	
}
