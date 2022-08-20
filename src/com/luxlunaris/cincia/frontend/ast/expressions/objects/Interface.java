package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;

public class Interface implements ObjectExpression{
	
	public List<MultiDeclaration> attributeDeclarations;
	public List<Signature> methodDeclaration;
	
}
