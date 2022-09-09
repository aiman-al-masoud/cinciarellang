package com.luxlunaris.cincia.backend.iterables;


public class DestructuredList extends CinciaList {

	public DestructuredList(CinciaList list) {
//		super(list.type);
		super(list.getType()); //TODO: check
		this.list = list.list;
	}

}
