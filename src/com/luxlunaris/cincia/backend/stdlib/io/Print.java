package com.luxlunaris.cincia.backend.stdlib.io;

import java.util.List;

import com.luxlunaris.cincia.backend.callables.CinciaFunction;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.primitives.CinciaString;
import com.luxlunaris.cincia.backend.shell.Repl;
import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;

public class Print extends CinciaFunction {


	public Print() {

		super(Print::printWrapper, getSignature());
		

	}

	protected static CinciaObject printWrapper(List<CinciaObject> args) {
		// TODO: more elegant solution to removing "s when printing strings!
		args.forEach(e->{System.out.print(   e instanceof CinciaString ? ((CinciaString)e).toJava()   :e ); System.out.print(" ");  });
		System.out.println();
		return null;
	}
	
	protected static Signature getSignature() {
		Signature signature = new Signature();		
		VariableDeclaration vD = new VariableDeclaration();
		vD.name = new Identifier("x");
		vD.type = new PrimitiveType(PrimitiveType.STRING); 
		signature.params =vD; //TODO: actually it's an unlimited number of args
		signature.returnType = Type.Any; //TODO: actually it's void, or IO
		return signature;
	}

}
