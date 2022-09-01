package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.declarations.FunctionDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;

public class CinciaClass extends CinciaObject{

	public CinciaClass(Type type) {
		super(type);
	}

	public void addMethod(String name, CinciaMethod method) {
		set(name, method, method.type);
	}	
	
	public void addAttribute(String name, CinciaObject attrib) {
		set(name, attrib, attrib.type);
	}	

	public CinciaObject constructor() {
		Enviro newEnv = this.getEnviro().newChild(); //TODO: remove method duplication
		CinciaObject obj = new CinciaObject(this.type);
		obj.enviro = newEnv;
		obj.__init__();
		return obj;
	}




}
