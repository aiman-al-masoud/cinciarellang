package com.luxlunaris.cincia.frontend.ast.declarations;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;

/**
 * Sequence of comma-separated simple expressions
 *
 */
public class MultiDeclaration implements Declaration{
	
	public List<SingleDeclaration> declarations;
	
	public MultiDeclaration() {
		declarations = new ArrayList<SingleDeclaration>();
	}
	
	public void addDeclaration(SingleDeclaration sd) {
		declarations.add(sd);
	}
	
}
