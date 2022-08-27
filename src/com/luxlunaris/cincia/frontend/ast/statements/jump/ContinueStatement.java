package com.luxlunaris.cincia.frontend.ast.statements.jump;

import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;

public class ContinueStatement implements Statement{

	@Override
	public Statement simplify() {
		return this;
	}
	
	@Override
	public String toString() {
		return Keywords.CONTINUE.toString();
	}
}
