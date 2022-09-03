package com.luxlunaris.cincia.backend;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.declarations.FunctionDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ClassExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;

public class CinciaClass extends AbstractCinciaObject{
	
	public static String CLASS = "class";

	public CinciaClass() {
		super(new IdentifierType("Class"));
		enviro.set(CLASS, this);
	}

	public void declareAttribute(String name, Type type) {
		set(name, null, type);
	}	

	public void declareMethod(String name, Signature type) {
		set(name, null, type);
	}	

	public void addMethod(String name, CinciaMethod method) {
		set(name, method, method.type);
	}	

	public void addAttribute(String name, AbstractCinciaObject value) {
		set(name, value, value.type);
	}	

	//TODO: deal with modifiers such as static
	public CinciaObject constructor(List<CinciaObject> args) {
		
		// basically prototypal inheritance (like Javascript)
		CinciaObject obj = this.copy(args);
		
		// java-code wrappers need to point to the original instance of the new 
		// java object
		
		obj.set("this", obj, type); 
		CinciaMethod m1 = new CinciaMethod(obj::copy);
		m1.parent = obj;
		obj.set(Magic.copy, m1);
		CinciaMethod m2 = new CinciaMethod(obj::freeze);
		m2.parent = obj;
		obj.set(Magic.freeze, m2);
		CinciaMethod m3 = new CinciaMethod(obj::as);
		m3.parent = obj;
		obj.set(Magic.as, m3);
		
		obj.__init__(args);
		
		return obj;
	}
	
	@Override
	public String toString() {
		return "class{}";
	}


}
