package com.luxlunaris.cincia.tests.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AddExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AssignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.ComparisonExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.MulExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.DictComprehension;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.DictExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ListComprehension;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ListExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.CalledExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.DotExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.IndexedExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.ReassignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.ast.statements.DeclarationStatement;
import com.luxlunaris.cincia.frontend.ast.statements.ExpressionStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.ReturnStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Int;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.parser.Parser;
import com.luxlunaris.cincia.frontend.preprocessor.Preprocessor;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;
import com.luxlunaris.cincia.tests.AnsiColors;
import com.luxlunaris.cincia.tests.parser.classes.AbstractTester;
import com.luxlunaris.cincia.tests.parser.classes.BinExpTester;
import com.luxlunaris.cincia.tests.parser.classes.ClassTester;
import com.luxlunaris.cincia.tests.parser.classes.DeclarationTester;
import com.luxlunaris.cincia.tests.parser.classes.DictTester;
import com.luxlunaris.cincia.tests.parser.classes.ImportTester;
import com.luxlunaris.cincia.tests.parser.classes.InterfaceTester;
import com.luxlunaris.cincia.tests.parser.classes.IterationTester;
import com.luxlunaris.cincia.tests.parser.classes.JumpTester;
import com.luxlunaris.cincia.tests.parser.classes.LambdaTester;
import com.luxlunaris.cincia.tests.parser.classes.ListTester;
import com.luxlunaris.cincia.tests.parser.classes.PostfixExpTester;
import com.luxlunaris.cincia.tests.parser.classes.RangeExpTester;
import com.luxlunaris.cincia.tests.parser.classes.SelectionTester;


public class Test {


	static final List<Entry<String, String>> tests = new ArrayList<Map.Entry<String,String>>();



	public static void main(String[] args) {

		//TODO: don't rely on string reprs for tests!!! (right now results are correct but some 'fails' crop up due to literal string comparison)


		//		add("1;", "1");
		//		add("f  = \\x:int -> 1;", "(f = [] \\([] x:INT) : null->1)");
		//		add("x = z = {'y' : 222, 'capra' : 1, 'buruf' : 'hallo123' };", "(x = (z = {'y' : 222, 'capra' : 1, 'buruf' : 'hallo123'}))");
		//		add("x = [1,2,3];", "(x = [1, 2, 3])");
		//		add("x[0] = z = a = [1,2,3];", "(x[0] = (z = (a = [1, 2, 3])))");
		//		add("x = { 1-1*1 : 2 };", "(x = {(1 MINUS (1 ASTERISK 1)) : 2})");
		//		add("f = \\x -> 1;", "(f = [] \\([] x:null) : null->1)");
		//		add("a = b = c = 1;", "(a = (b = (c = 1)))");
		//		add("x = z = {1:2 , * x, 'capra': 3212, };", "(x = (z = {1 : 2, 'capra' : 3212, *(x)}))");
		//		add("for i in x{ x = do(x); x+=1;  }", "for [i] in x{(x = do(x)); (x PLUS_ASSIGN 1)}");
		//		add("get final static private foo:int, x:float;", "[([GET, FINAL, STATIC, PRIVATE] foo:INT), ([] x:FLOAT)]");
		//		add("get final static private foo:int;", "([GET, FINAL, STATIC, PRIVATE] foo:INT)");
		//		add("f:\\x:int:int;", "([] f \\([] x:INT) : INT)");
		//		add("x:int|float;", "([] x:INT | FLOAT)");
		//		add("dec x:int|float;", "([] x:INT | FLOAT)");
		//		add("x == 1 ? 3 : 4;", "((x COMPARE 1)?3:4)");
		//		add("while true{ x+=1;y+=2; }\n x+=1;", "while true then {(x PLUS_ASSIGN 1); (y PLUS_ASSIGN 2)}");
		//		add("true && false;", "");
		//		add("true || false;", "");
		
		add(new BinExpTester());
		add(new DeclarationTester());
		add(new DictTester());
		add(new ListTester());
		add(new LambdaTester());
		add(new PostfixExpTester());
		add(new ClassTester());
		add(new InterfaceTester());
		add(new SelectionTester());
		add(new IterationTester());
		add(new JumpTester());
		add(new ImportTester());
		add(new RangeExpTester());
		add("f | g | h", "[f, g, h]");
		add(" (\\x -> 1) | g | h", "");

		for(Entry<String, String> e : tests) {

			Preprocessor pP = new Preprocessor(e.getKey());
			CharStream cS = new CharStream(pP.process());
			TokenStream tS = new TokenStream(cS);		
			Parser p  = new Parser(tS);

			try {
				Statement s = p.parse().get(0).simplify();
				System.out.println(e.getKey()+" "+ ( e.getValue().equals(s.toString()) ? AnsiColors.ok("OK") : AnsiColors.fail("FAIL") ) );
				System.out.println(s+"");
			}catch (Exception exception) {
				System.out.println( AnsiColors.fail(e.getKey()+" "+exception.getMessage()+" FAIL"));;
				exception.printStackTrace();
			}

		}





	}
	
	public static void add(String s1, String s2) {
		tests.add(Map.entry(s1, s2));
	}

	public static void add(AbstractTester tester) {
		tests.addAll(tester.tests);
	}



}