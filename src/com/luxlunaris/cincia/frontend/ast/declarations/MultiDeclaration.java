package com.luxlunaris.cincia.frontend.ast.declarations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

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
	
	@Override
	public String toString() {
		return "["+declarations.stream().map(x->x+"").reduce((x1, x2)->x1+", "+x2).get()+"]";
	}

	@Override
	public Declaration simplify() {
		this.declarations  = declarations.stream().map(d-> (SingleDeclaration) d.simplify()).collect(Collectors.toList());
		
		if(this.declarations.size()==1) {
			return this.declarations.get(0);
		}
		
		return this;
	}

	@Override
	public List<SingleDeclaration> toList() {
		return declarations;
	}

	@Override
	public Declaration changeType(Type newType) {
//		declarations.stream().map(d->d.changeType(newType))'
		throw new RuntimeException("Not implemented");
 	}

}
