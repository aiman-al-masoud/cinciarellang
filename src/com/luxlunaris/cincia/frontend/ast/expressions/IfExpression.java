package com.luxlunaris.cincia.frontend.ast.expressions;

import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class IfExpression extends SingleExpression{

	public Expression cond;
	public Ast thenBranch;
	public Ast elseBranch;

	@Override
	public Expression simplify() {
		this.cond  = cond.simplify();

		if(thenBranch!=null) {
			this.thenBranch = thenBranch.simplify();
		}

		if(elseBranch!=null) {
			this.elseBranch = elseBranch.simplify();
		}

		return this;
	}

	@Override
	public String toString() {
		return "if "+cond+" then "+thenBranch+" else "+elseBranch;
	}

	public Ast getThen() { //TODO: throw if both null?
		return thenBranch;
	}

	public Ast getElse() { //TODO: throw if both null?
		return elseBranch;
	}


}
