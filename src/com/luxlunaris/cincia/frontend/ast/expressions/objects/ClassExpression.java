package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.expressions.binary.AssignmentExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PrimaryExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.IntToken;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

public class ClassExpression implements ObjectExpression{

	public List<Modifiers> modifiersList; // can be empty
	public Identifier superclass;
	public List<Identifier> interfaces;
	public List<Identifier> observables;
	public List<AssignmentExpression> assignments;
	public List<Declaration> declarations; 
	
	
	public ClassExpression() {
		
		modifiersList = new ArrayList<Modifiers>();
		interfaces = new ArrayList<Identifier>();
		observables = new ArrayList<Identifier>();
		assignments = new ArrayList<AssignmentExpression>();
		declarations = new ArrayList<Declaration>();
	}
	
	public void addDeclaration(Declaration declaration) {
		declarations.add(declaration);
	}
	
	
	public void addAssignment(AssignmentExpression assignment) {
		
		if(assignment.simplify() instanceof IntToken) { // remove nullop (see AddDecKeyword in preprocessor)
			return;
		}
		
		assignments.add(assignment);
	}
	
	
	
	@Override
	public Expression simplify() {
		this.declarations = declarations.stream().map(s->s.simplify()).collect(Collectors.toList());
		this.assignments = assignments.stream().map(s->(AssignmentExpression)s.simplify()).collect(Collectors.toList());		
		return this;
	}
	
	@Override
	public String toString() {
		return "class{ declarations: "+declarations.toString()+", assignments: "+assignments.toString()+" }";
	}
	
	
}
