package com.luxlunaris.cincia.backend.throwables;

/**
 * 
 * @author aiman
 *
 */
public class UndefinedError extends CinciaException {
	
	public final String undefinedName;
	
	public UndefinedError(String undefinedName) {
		super(undefinedName+" is undefined!");
		this.undefinedName = undefinedName;
	}
	
	
}
