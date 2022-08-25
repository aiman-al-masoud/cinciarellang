package com.luxlunaris.cincia.frontend.ast.statements.jump;

import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;

public class ContinueStatement implements Statement{

	@Override
	public Statement simplify() {
		return this;
	}
}
