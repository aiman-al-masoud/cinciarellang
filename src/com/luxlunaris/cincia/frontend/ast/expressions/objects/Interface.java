package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;

public class Interface implements Expression{
	
	public List<MultiDeclaration> attributeDeclarations;
	public List<Signature> methodDeclaration;
	
}
