package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;

public class InterfaceExpression implements ObjectExpression{
	
	public List<Modifier> modifiersList; // can be empty	
//	public List<MultiDeclaration> attributeDeclarations;
//	public List<Signature> methodDeclaration;
	public List<Declaration> declarations;
	
	public InterfaceExpression() {
		modifiersList = new ArrayList<Modifier>();
		declarations = new ArrayList<Declaration>();
	}
	
}
