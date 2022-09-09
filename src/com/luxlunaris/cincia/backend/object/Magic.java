package com.luxlunaris.cincia.backend.object;


/**
 * A set of standard CinciaObject methods, most of which overridable.
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
	into,
	copy, 
	freeze;
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
	
}
