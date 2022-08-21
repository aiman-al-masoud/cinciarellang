package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;

public class ClassExpression implements ObjectExpression{

	public List<Modifier> modifiersList; // can be empty
	public Identifier superclass;
	public List<Identifier> interfaces;
	public List<Identifier> observables;
	public List<LambdaExpression> methods;
	public List<Declaration> attributes; 
	
	public ClassExpression() {
		modifiersList = new ArrayList<Modifier>();
		interfaces = new ArrayList<Identifier>();
		observables = new ArrayList<Identifier>();
		methods = new ArrayList<LambdaExpression>();
		attributes = new ArrayList<Declaration>();
	}
	
	
	
}
