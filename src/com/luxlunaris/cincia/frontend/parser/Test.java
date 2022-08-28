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
		
		// call expression
		CalledExpression caE = new CalledExpression();
		caE.callable = new Identifier("f");
		MultiExpression muE = new MultiExpression();
		muE.expressions = Arrays.asList(new Identifier("a"), new Identifier("b"), new Int(1));
		caE.args = muE;
		add("f(a, b, 1);", caE.toString());
		
		// dot expression
		DotExpression dE = new DotExpression();
		dE.left = new Identifier("a");
		dE.right = new Identifier("b");
		DotExpression dE2 = new DotExpression();
		dE2.left = dE;
		dE2.right = new Identifier("c");
		DotExpression dE3 = new DotExpression();
		dE3.left = dE2;
		dE3.right = new Identifier("d");
		add("a.b.c.d", dE3.toString());
		
		// indexed expression
		IndexedExpression iE = new IndexedExpression();
		iE.indexable = new Identifier("x");
		iE.index = new Int(1);
		IndexedExpression iE2 = new IndexedExpression();
		iE2.indexable = iE;
		iE2.index = new Int(2);
		add("x[1][2]", iE2.toString());
		
		// reassignment expression
		ReassignmentExpression rE = new ReassignmentExpression();
		rE.left = new Identifier("x");
		rE.right = new Int(1);
		rE.op = Operators.PLUS_ASSIGN;
		add("x+=1", rE.toString());
		rE.op = Operators.DIV_ASSIGN;
		add("x/=1", rE.toString());

		
		// dictionary literal expression
		DictExpression diE = new DictExpression();
		diE.addEntry(new Int(1), new Int(2));
		AssignmentExpression asE = new AssignmentExpression();
		asE.left = new Identifier("x");
		asE.right = diE;
		add("x = { 1 : 2 };", asE.toString());
		
		// list literal expression
		ListExpression lE = new ListExpression();
		MultiExpression muEx = new MultiExpression();
		muEx.expressions = Arrays.asList(new Int(1), new Int(2), new Int(3), new Int(4));
		lE.elements = muEx;
		add("[1,2,3,4];", lE.toString());
		
		
		// list comprehension
		ListComprehension lC = new ListComprehension();
		AddExpression adE = new AddExpression();
		adE.left = new Identifier("x");
		adE.right = new Int(1);
		adE.op = Operators.PLUS;
		lC.element = adE;
		lC.source =  new Identifier("x");
		lC.iterable =  new Identifier("l");
		add("[x+1 for x in l];", lC.toString());
		
		
		// dict comprehension
		DictComprehension dC = new DictComprehension();
		Identifier en = new Identifier("e");
		IndexedExpression ine = new IndexedExpression();
		ine.indexable = en;
		ine.index = new Int(0);
		IndexedExpression ine2 = new IndexedExpression();
		ine2.indexable = en;
		ine2.index = new Int(1);
		dC.key = ine;
		dC.val = ine2;
		dC.source = en;
		dC.iterable = new Identifier("entries");
		AssignmentExpression ase = new AssignmentExpression();
		ase.left = new Identifier("x");
		ase.right = dC;
		add("x = { e[0] : e[1] for e in entries };", ase.toString());
		
		
		// lambda expression
		LambdaExpression lex = new LambdaExpression();
		lex.expression = new Int(1);
		Signature sg = new Signature();
		VariableDeclaration vD = new VariableDeclaration();
		vD.name = new Identifier("x");
		sg.params = vD;
		lex.signature = sg;
		add("\\x->1;", lex.toString());
		
		
		// lambda expression with code block
		lex.expression = null;
		CompoundStatement co = new CompoundStatement();
		AssignmentExpression one = new AssignmentExpression();
		AssignmentExpression two = new AssignmentExpression();
		ReturnStatement three = new ReturnStatement();
		one.left = new Identifier("x");
		one.right = new Int(1);
		AddExpression aex = new AddExpression();
		aex.left = new Identifier("x");
		aex.right  = new Int(1);
		aex.op = Operators.PLUS;
		two.left = new Identifier("y");
		two.right = aex;
		three.expression = new Identifier("y");
		co.add(new ExpressionStatement(one));
		co.add(new ExpressionStatement(two));
		co.add(three);
		lex.block =co;
		add("\\x->{ x = 1;y=x+1;return y; };", lex.toString());
		
		// class with declarations, methods and assignments
		add("class { x:int; f:\\\\x:int:int; f = \\x->1; x = 1;  };", "");

		
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
