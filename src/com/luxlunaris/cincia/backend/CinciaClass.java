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

	//TODO: remove method duplication (all instances should refer to same func objects)
	//TODO: deal with modifiers such as static
	public AbstractCinciaObject constructor(List<CinciaObject> args) {
		
		// (NOPE, thats not a problem, for now...): TODO: vars (other than methods) aren't copied and point to "static" attributes in class?
		
		Enviro newEnv = this.getEnviro().newChild(); 
		AbstractCinciaObject obj = new AbstractCinciaObject(new IdentifierType("Object"));  
		obj.myClass = this;
		obj.enviro = newEnv;
		set("this", obj, type); 
		set(Magic.copy, new CinciaMethod(obj::copy));
		set(Magic.freeze, new CinciaMethod(obj::freeze));
		set(Magic.as, new CinciaMethod(obj::as));
		
		
		this.getEnviro().items().forEach(e->{
			if(e.getValue() instanceof CinciaMethod) {
				CinciaMethod cm = ((CinciaMethod)e.getValue()).copy(obj);
				obj.set(e.getKey(), cm);
			}
		});		
		
		obj.__init__(args);
		return obj;
	}
	
	@Override
	public String toString() {
		return "class{}";
	}


}
