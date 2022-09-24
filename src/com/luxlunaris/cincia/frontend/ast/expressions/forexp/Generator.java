package com.luxlunaris.cincia.frontend.ast.expressions.forexp;



import java.util.ArrayList;
import java.util.List;



import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Bool;

public class Generator{
	public List<Identifier> loopVars;
	public Expression iterable;
	public Expression filter;

	public Generator() {
		loopVars  = new ArrayList<Identifier>();
		filter = new Bool(true); // everything passes by default
	}

	public Generator simplify() {
		this.iterable = iterable.simplify();
		
//		if(filter!=null) {
			filter = filter.simplify();
//		}
		
		return this;
	}

	@Override
	public String toString() {
		return "loopvars="+loopVars+" iterable="+iterable;
	}

}