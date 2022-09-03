package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class DestructuredList extends CinciaList {

	public DestructuredList(CinciaList list) {
		super(list.type);
		this.list = list.list;
	}

}
