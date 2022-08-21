package com.luxlunaris.cincia.frontend.ast.statements.jump;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;

public class ReturnStatement implements Statement{
	
	public List<Expression> values;
	
	public ReturnStatement() {
		values = new ArrayList<Expression>();
	}
	
	public void addValue(Expression value) {
		this.values.add(value);
	}
}
