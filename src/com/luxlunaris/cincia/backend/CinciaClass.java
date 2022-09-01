package com.luxlunaris.cincia.backend;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.declarations.FunctionDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;

public class CinciaClass extends CinciaObject{

	
	public CinciaClass(Type type) {
		super(type);
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

	public void addAttribute(String name, CinciaObject value) {
		set(name, value, value.type);
	}	


	//TODO: remove method duplication (all instances refer to same func objects)
	//TODO: deal with modifiers such as static
	public CinciaObject constructor(List<CinciaObject> args) {
		Enviro newEnv = this.getEnviro().newChild(); 
		CinciaObject obj = new CinciaObject(this.type);  
		obj.enviro = newEnv;
		obj.__init__(args);
		return obj;
		
	}


}
