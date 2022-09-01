package com.luxlunaris.cincia.backend;


/**
 * A set of overridable method names.
 * 
 */
public enum Magic {

	__bool__,
	__add__,
	__sub__,
	__mul__,
	__mod__,
	__div__,
	__neg__,
	__or__,
	__and__,
	__lt__,
	__gt__,
	__lte__,
	__gte__,
	__eq__,
	__ne__,
	__not__,
	__str__,
	__init__,
	as,
	copy, 
	freeze;
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
	
}
