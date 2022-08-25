package com.luxlunaris.cincia.frontend.ast.statements.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.labelled.CaseStatement;
import com.luxlunaris.cincia.frontend.ast.statements.labelled.DefaultStatement;

public class MatchStatement implements Statement{
	
	public Expression cond;
	public List<CaseStatement> casesList;
	public DefaultStatement defaultStatement;
	
	
	public MatchStatement() {
		casesList = new ArrayList<CaseStatement>();
	}
	
	public void add(CaseStatement cS) {
		casesList.add(cS);
	}

	@Override
	public Statement simplify() {
		this.cond = cond.simplify();
		this.casesList = casesList.stream().map(c->(CaseStatement)c.simplify()).collect(Collectors.toList());
		return this;
	}
	
	@Override
	public String toString() {
		return "match "+casesList+" "+defaultStatement;
	}
	
}
