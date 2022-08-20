package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;

public class Class implements ObjectExpression{

	public List<Modifier> modifiersList; // can be empty
	public Identifier superclass;
	public List<Identifier> interfaces;
	public List<Identifier> observables;
	public List<Lambda> methods;
	public List<Declaration> attributes; 
	
	public Class() {
		modifiersList = new ArrayList<Modifier>();
		interfaces = new ArrayList<Identifier>();
		observables = new ArrayList<Identifier>();
		methods = new ArrayList<Lambda>();
		attributes = new ArrayList<Declaration>();
	}
	
	
	
}
