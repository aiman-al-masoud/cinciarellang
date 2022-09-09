package com.luxlunaris.cincia.tests.parser.classes;

import com.luxlunaris.cincia.frontend.ast.expressions.binary.AssignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.DictComprehension;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.DictExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.IndexedExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Int;

public class DictTester extends AbstractTester {

	public DictTester() {

		// dictionary literal expression
		DictExpression diE = new DictExpression();
		diE.addEntry(new Int(1), new Int(2));
		AssignmentExpression asE = new AssignmentExpression();
		asE.left = new Identifier("x");
		asE.right = diE;
		add("x = { 1 : 2 };", asE.toString());

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

	}

}
