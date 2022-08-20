package com.luxlunaris.cincia.frontend.nodes.declarations;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.nodes.interfaces.Declaration;

public class MultiDeclaration implements Declaration{
	
	public List<SingleDeclaration> declarations;
	
	public MultiDeclaration() {
		declarations = new ArrayList<SingleDeclaration>();
	}
	
}
