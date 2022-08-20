package com.luxlunaris.cincia.frontend.nodes.statements.labelled;

import com.luxlunaris.cincia.frontend.nodes.interfaces.Expression;
import com.luxlunaris.cincia.frontend.nodes.interfaces.Statement;
import com.luxlunaris.cincia.frontend.nodes.statements.CompoundStatement;

public class CaseStatement implements Statement{
	
	
	public Expression cond;
	public CompoundStatement block;
	
	
}
