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
		
		//TODO: vars (other than methods) aren't copied and point to "static" attributes in class
//		c = class{ __init__ = \x -> 1; }
//		b = class{ __init__ = \x -> 1; }
//		b.a.r = 1
//		y = b()
//		y.a.r = 2
//		b.a.r // 2 WROOOONG unless attrib is static

		
		
		Enviro newEnv = this.getEnviro().newChild(); 
		AbstractCinciaObject obj = new AbstractCinciaObject(new IdentifierType("Object"));  
		obj.myClass = this;
		obj.enviro = newEnv;
		
		
		
		this.getEnviro().items().forEach(e->{
			if(e.getValue() instanceof CinciaMethod) {
				CinciaMethod cm = ((CinciaMethod)e.getValue()).copy(obj);
				obj.set(e.getKey(), cm);
			}
		});		
		
		obj.set("this", obj, type); 
		
		CinciaMethod m1 = new CinciaMethod(obj::copy);
		m1.parent = obj;
		obj.set(Magic.copy, m1);
		obj.set(Magic.freeze, new CinciaMethod(obj::freeze));
		obj.set(Magic.as, new CinciaMethod(obj::as));
		
		obj.__init__(args);
		return obj;
	}
	
	@Override
	public String toString() {
		return "class{}";
	}


}
