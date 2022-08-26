package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;

public class InterfaceExpression implements ObjectExpression{
	
	public List<Modifier> modifiers; // can be empty	
//	public List<MultiDeclaration> attributeDeclarations;
//	public List<Signature> methodDeclaration;
	public List<Declaration> declarations;
	public List<Identifier> superInterfaces;
	
	public InterfaceExpression() {
		modifiers = new ArrayList<Modifier>();
		declarations = new ArrayList<Declaration>();
		superInterfaces = new ArrayList<Identifier>();
	}
	
	
	public void addDeclaration(Declaration declaration) {
		declarations.add(declaration);
	}


	@Override
	public Expression simplify() {
		this.declarations = declarations.stream().map(d->d.simplify()).collect(Collectors.toList());
		return this;
	}
	
	@Override
	public String toString() {
		return declarations.toString();
	}
	
	
}
