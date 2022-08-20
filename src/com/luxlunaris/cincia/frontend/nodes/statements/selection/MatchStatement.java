package com.luxlunaris.cincia.frontend.nodes.statements.selection;

import java.util.List;

import com.luxlunaris.cincia.frontend.nodes.interfaces.Expression;
import com.luxlunaris.cincia.frontend.nodes.interfaces.Statement;
import com.luxlunaris.cincia.frontend.nodes.statements.labelled.CaseStatement;
import com.luxlunaris.cincia.frontend.nodes.statements.labelled.DefaultStatement;

public class MatchStatement implements Statement{
	
	public Expression cond;
	public List<CaseStatement> casesList;
	public DefaultStatement defaultStatement;
	
	
	
	
}
