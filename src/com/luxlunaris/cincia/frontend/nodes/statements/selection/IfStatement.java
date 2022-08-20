package com.luxlunaris.cincia.frontend.nodes.statements.selection;

import com.luxlunaris.cincia.frontend.nodes.interfaces.Expression;
import com.luxlunaris.cincia.frontend.nodes.interfaces.Statement;
import com.luxlunaris.cincia.frontend.nodes.statements.CompoundStatement;

public class IfStatement implements Statement{
	
	public Expression cond;
	public CompoundStatement thenBlock;
	public CompoundStatement elseBlock;
	
	
	
}
