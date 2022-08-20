package com.luxlunaris.cincia.frontend.ast.statements.selection;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.labelled.CaseStatement;
import com.luxlunaris.cincia.frontend.ast.statements.labelled.DefaultStatement;

public class MatchStatement implements Statement{
	
	public Expression cond;
	public List<CaseStatement> casesList;
	public DefaultStatement defaultStatement;
	
	
	
	
}
