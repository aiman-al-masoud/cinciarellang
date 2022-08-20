package com.luxlunaris.cincia.frontend.nodes.expressions.objects;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.nodes.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.nodes.interfaces.Expression;
import com.luxlunaris.cincia.frontend.nodes.tokens.Identifier;
import com.luxlunaris.cincia.frontend.nodes.tokens.modifier.Modifier;

public class Class implements Expression{

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
