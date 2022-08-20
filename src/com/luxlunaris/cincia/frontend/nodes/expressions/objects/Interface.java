package com.luxlunaris.cincia.frontend.nodes.expressions.objects;

import java.util.List;

import com.luxlunaris.cincia.frontend.nodes.declarations.Declaration;
import com.luxlunaris.cincia.frontend.nodes.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.nodes.declarations.Signature;
import com.luxlunaris.cincia.frontend.nodes.expressions.Expression;

public class Interface implements Expression{
	
	public List<MultiDeclaration> attributeDeclarations;
	public List<Signature> methodDeclaration;
	
}
