package com.luxlunaris.cincia.backend.stdlib;

import java.util.Arrays;
import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.backend.stdlib.concurrency.Concurrency;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;

public class Stdlib extends AbstractCinciaObject{

	public Stdlib() {
		super(new IdentifierType("Module")); //TODO: extract type
		set("concurrency", new Concurrency());
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

		String subpath = hierarchy.subList(2, hierarchy.size()-1).stream().reduce((p1,p2) -> p1+"."+p2).get();
		return obj.get(subpath);

	}

}
