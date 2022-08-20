package com.luxlunaris.cincia.frontend.nodes.statements.selection;

import com.luxlunaris.cincia.frontend.nodes.expressions.Expression;
import com.luxlunaris.cincia.frontend.nodes.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.nodes.statements.Statement;

public class IfStatement implements Statement{
	
	public Expression cond;
	public CompoundStatement thenBlock;
	public CompoundStatement elseBlock;
	
	
	
}
