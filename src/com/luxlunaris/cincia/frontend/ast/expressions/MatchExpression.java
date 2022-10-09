package com.luxlunaris.cincia.frontend.ast.expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.labelled.CaseStatement;
import com.luxlunaris.cincia.frontend.ast.statements.labelled.DefaultStatement;

public class MatchExpression extends SingleExpression{
	
	public Expression cond;
	public List<CaseStatement> casesList;
	public DefaultStatement defaultStatement;
	
	
	public MatchExpression() {
		casesList = new ArrayList<CaseStatement>();
	}
	
	public void add(CaseStatement cS) {
		casesList.add(cS);
	}

	@Override
	public Expression simplify() {
		this.cond = cond.simplify();
		this.casesList = casesList.stream().map(c->(CaseStatement)c.simplify()).collect(Collectors.toList());
		
		if(defaultStatement!=null) {
			this.defaultStatement = (DefaultStatement) defaultStatement.simplify();
		}
		
		return this;
	}
	
	@Override
	public String toString() {
		return "match "+cond+" "+casesList+" "+defaultStatement;
	}
	
}
