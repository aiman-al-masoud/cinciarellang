package com.luxlunaris.cincia.frontend.ast.statements.selection;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class IfStatement implements Statement{
	
	public Expression cond;
	public CompoundStatement thenBlock;
	public CompoundStatement elseBlock;
	
	
	
}
