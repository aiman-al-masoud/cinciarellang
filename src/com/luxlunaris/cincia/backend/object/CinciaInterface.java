package com.luxlunaris.cincia.backend.object;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.SingleDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.InterfaceExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaInterface extends CinciaCinciaClass implements Type{

	protected InterfaceExpression interex;
	protected List<SingleDeclaration> declarations;
	
	public CinciaInterface(InterfaceExpression interex) {
		
		super();
		this.type = this;
		this.interex = interex;
		
		declarations = new ArrayList<SingleDeclaration>();
		
		interex.declarations.forEach(d->{
			
			try {
				MultiDeclaration md = (MultiDeclaration)d;
				declarations.addAll(md.declarations);
			} catch (ClassCastException e) {
				declarations.add((SingleDeclaration)d);
			}
			
		});
		
		
		declarations.forEach(d->{
			set(d.getName(), null, d.getType());
		});
		
	}

	@Override
	public boolean matches(Type other) {
		return this ==other;//TODO: !!!!!!!!!!!!!!!!!!!!!!!
	}
	
	@Override
	public Expression simplify() {
		return this;
	}
	
	public List<SingleDeclaration> getDeclarations(){
		return declarations;
	}
	
	
	
	
	

}
