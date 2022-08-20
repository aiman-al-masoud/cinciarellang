package com.luxlunaris.cincia.frontend.nodes.statements.jump;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.nodes.expressions.Expression;
import com.luxlunaris.cincia.frontend.nodes.statements.Statement;

public class ReturnStatement implements Statement{
	
	public List<Expression> values;
	
	public ReturnStatement() {
		values = new ArrayList<Expression>();
	}
}
