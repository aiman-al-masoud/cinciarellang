package com.luxlunaris.cincia.frontend.nodes.declarations;

import java.util.ArrayList;
import java.util.List;

public class MultiDeclaration implements Declaration{
	
	public List<SingleDeclaration> declarations;
	
	public MultiDeclaration() {
		declarations = new ArrayList<SingleDeclaration>();
	}
	
}
