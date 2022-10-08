package com.luxlunaris.cincia.backend.throwables;

public class ReassignmentException extends CinciaException {
	
	String name;
	
	public ReassignmentException(String name) {
		super(name+" is final, and cannot be reassigned!");
	}
	
}
