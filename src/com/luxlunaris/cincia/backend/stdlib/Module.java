package com.luxlunaris.cincia.backend.stdlib;

import java.util.Arrays;
import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;

public class Module extends AbstractCinciaObject {

	public Module() {
		super(new IdentifierType("Module"));
	}

	@Override
	public CinciaObject get(String path) {

		List<String> hierarchy = Arrays.asList(path.split("\\."));

		if(hierarchy.size()==1) {
			return super.get(path);
		}

		CinciaObject obj = get(hierarchy.get(1));

		if (hierarchy.size() == 2) {
			return obj;
		}

		if(!(obj instanceof Module)) {
			throw new RuntimeException("You're treating a regular object like a module!");
		}

		String subpath = hierarchy.subList(2, hierarchy.size()-1).stream().reduce((p1,p2) -> p1+"."+p2).get();
		return obj.get(subpath);

	}

}
