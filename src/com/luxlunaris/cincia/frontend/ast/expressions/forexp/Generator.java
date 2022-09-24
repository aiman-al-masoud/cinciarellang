package com.luxlunaris.cincia.frontend.ast.expressions.forexp;



import java.util.ArrayList;
import java.util.List;



import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;

public class Generator{
	public List<Identifier> loopVars;
	public Expression iterable;

	public Generator() {
		loopVars  = new ArrayList<Identifier>();
	}

	public Generator simplify() {
		this.iterable = iterable.simplify();
		return this;
	}

	@Override
	public String toString() {
		return "loopvars="+loopVars+" iterable="+iterable;
	}

}