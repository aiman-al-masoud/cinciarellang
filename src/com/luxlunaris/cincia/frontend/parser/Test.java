package com.luxlunaris.cincia.frontend.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.luxlunaris.cincia.frontend.ast.declarations.Signature;
import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AddExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AssignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.ComparisonExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.MulExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.CalledExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.DeclarationStatement;
import com.luxlunaris.cincia.frontend.ast.statements.ExpressionStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Int;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.preprocessor.Preprocessor;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;


public class Test {
	
	static final String OKGREEN = "\033[92m";
	static final String WARNING = "\033[93m";
	static final String FAIL = "\033[91m";
	static final String ENDC = "\033[0m";
	static final List<Entry<String, String>> tests = new ArrayList<Map.Entry<String,String>>();

	
	
	public static void main(String[] args) {
		
		//TODO: don't rely on string reprs for tests!!! (right now results are correct but some 'fails' crop up due to literal string comparison)
		
		
		add("1;", "1");
		add("f  = \\x:int -> 1;", "(f = [] \\([] x:INT) : null->1)");
		add("x = z = {'y' : 222, 'capra' : 1, 'buruf' : 'hallo123' };", "(x = (z = {'y' : 222, 'capra' : 1, 'buruf' : 'hallo123'}))");
		add("x = [1,2,3];", "(x = [1, 2, 3])");
		add("x[0] = z = a = [1,2,3];", "(x[0] = (z = (a = [1, 2, 3])))");
		add("x = { 1-1*1 : 2 };", "(x = {(1 MINUS (1 ASTERISK 1)) : 2})");
		add("f = \\x -> 1;", "(f = [] \\([] x:null) : null->1)");
		add("a = b = c = 1;", "(a = (b = (c = 1)))");
		add("x = z = {1:2 , * x, 'capra': 3212, };", "(x = (z = {1 : 2, 'capra' : 3212, *(x)}))");
		add("for i in x{ x = do(x); x+=1;  }", "for [i] in x{(x = do(x)); (x PLUS_ASSIGN 1)}");
		add("get final static private foo:int, x:float;", "[([GET, FINAL, STATIC, PRIVATE] foo:INT), ([] x:FLOAT)]");
		add("get final static private foo:int;", "([GET, FINAL, STATIC, PRIVATE] foo:INT)");
		add("f:\\x:int:int;", "([] f \\([] x:INT) : INT)");
		add("x:int|float;", "([] x:INT | FLOAT)");
		add("dec x:int|float;", "([] x:INT | FLOAT)");
		add("x == 1 ? 3 : 4;", "((x COMPARE 1)?3:4)");
		add("while true{ x+=1;y+=2; }\n x+=1;", "while true then {(x PLUS_ASSIGN 1); (y PLUS_ASSIGN 2)}");
		add("true && false;", "");
		add("true || false;", "");
		// binary
		
		// mul expression
		MulExpression mE = new MulExpression();
		mE.left = new Int(1);
		mE.right = new Int(2);
		mE.op = Operators.ASTERISK;
		ExpressionStatement ex = new ExpressionStatement(mE);
		add("1 * 2;", ex.toString());
		mE.op = Operators.DIV;
		add("1 / 2;", mE.toString());
		mE.op = Operators.MOD;
		add("1 % 2;", mE.toString());
		
		//add expression
		AddExpression aE = new AddExpression();
		aE.left = new Int(1);
		aE.right = new Int(1);
		aE.op = Operators.PLUS;
		add("1 + 1;", aE.toString());
		aE.op = Operators.MINUS;
		add("1 - 1;", aE.toString());
		
		// comparison expression
		ComparisonExpression cE = new ComparisonExpression();
		cE.left = new Int(2);
		cE.right = new Int(1);
		cE.op = Operators.COMPARE;
		add("2 == 1;", cE.toString());
		cE.op = Operators.LTE;
		add("2 <= 1;", cE.toString());
		cE.op = Operators.GTE;
		add("2 >= 1;", cE.toString());
		cE.op = Operators.NE;
		add("2 != 1;", cE.toString());
		cE.op = Operators.LT;
		add("2 < 1;", cE.toString());
		cE.op = Operators.GT;
		add("2 > 1;", cE.toString());
		ComparisonExpression ccE = new ComparisonExpression();
		ccE.left = cE;
		ccE.right = new Int(3);
		ccE.op = Operators.GT;
		add("2 > 1 > 3;", ccE.toString());
		
		// postfix
		CalledExpression caE = new CalledExpression();
		caE.callable = new Identifier("f");
		MultiExpression muE = new MultiExpression();
		muE.expressions = Arrays.asList(new Identifier("a"), new Identifier("b"), new Int(1));
		caE.args = muE;
		add("f(a, b, 1);", caE.toString());
		add("a.b.c.d", "");
		add("x[1][2]", "");
		add("x+=1", "");
		add("x/=1", "");

		
		//object
		add("x = { 1 : 2 };", "(x = {1 : 2})");
		add("[1,2,3,4];", "");
		add("[x+1 for x in l];", "");
		add("x = { key : val for v in dict };", "");
		add("\\x->1;", "");
		add("\\x->{ x = 1;y=x+1;return y; };", "");
		add("class { dec x:int; f = \\x->1;  };", "");
		add("class { x = 1; x:int; f = \\x->1;  };", "");
		add("class { x:int; f = \\x->1;  };", "");
		add("class { f:\\x:int:int;  };", "");
		add("interface { x:int; y:int; f:\\x:int:int;  };", "");
		
		// sel statements
		add("if x { x = 1; y = 2;}else{y = x = 0; }", "");
		add("match x{case 1:return 1;case 2:return 2;default:return 0;}", "");
		// iteration statements 
		add("for i in x{ x = do(x); x+=1;  }", "");
		add("while x{ x = do(x); x+=1;  }", "");
		// jump statements 
		add("while x{ break;  }", "");
		add("while x{ continue;  }", "");
		// exception statements 
		add("throw 1+1;", "");
		add("try { 1/0; }catch e:String|char { return 1; }", "");
		add("try { 1/0; }catch e:float { }", "");
		add("try { 1/0; }catch e:int { }", "");
		// import statement 
		add("import capra.c as c from \"./files/capre\";", "");
		add("import capra as c from \"./files/capre\";", "");
		add("import capra as c, muflone.lana as m from \"./files/capre\";", "");
		// types/declarations
		add("x:int", "");
		add("x:int|float|Object", "");
		add("x:{int:int}", "");
		add("x:int[]", "");
		add("f:\\x:int:int;", "");

		
		
		for(Entry<String, String> e : tests) {
			
			Preprocessor pP = new Preprocessor(e.getKey());
			CharStream cS = new CharStream(pP.process());
			TokenStream tS = new TokenStream(cS);		
			
			try {
				Parser p  = new Parser(tS);
				Statement s = p.parse().get(0).simplify();
				System.out.println(e.getKey()+" "+ ( e.getValue().equals(s.toString()) ? ok("OK") : fail("FAIL") ) +" "+s);
			}catch (Exception exception) {
				System.out.println(fail(e.getKey()+" "+exception.getMessage()+" FAIL"));;
				exception.printStackTrace();
			}
			
		}

		
//		f  = \\x:int -> 1
//		AssignmentExpression aE = new AssignmentExpression();
//		aE.left = new Identifier("f");
//		LambdaExpression lE = new LambdaExpression();
//		Signature s = new Signature();
//		VariableDeclaration vD = new VariableDeclaration();
//		vD.name = new Identifier("x");
//		vD.type = new PrimitiveType(Keywords.INT);
//		s.params = vD;
//		lE.signature  = s;
//		lE.expression  = new Int(1);
//		aE.right = lE;
//		ExpressionStatement e = new ExpressionStatement(aE);
//		System.out.println(e.simplify()+" capra");
		
		
	}
	
	public static String ok(String s) {
		return OKGREEN+s+ENDC;
	}
	
	public static String fail(String s) {
		return FAIL+s+ENDC;
	}
	
	public static void add(String s1, String s2) {
		tests.add(Map.entry(s1, s2));
	}
	
	
	

	
	
}
