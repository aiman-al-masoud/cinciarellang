package com.luxlunaris.cincia.frontend.token;

public class Bool extends AbstractToken{

	public final boolean value;

	public Bool(boolean value) {
		this.value  =value;
	}

	public static Boolean stringToBool(String string) {

		boolean isTrue = string.equals("true")? true : false;
		boolean isFalse = string.equals("false")? true : false;
//		Boolean b = isTrue? true : (isFalse? false : null); 
		Boolean b = !(isTrue || isFalse) ? null : isTrue? true : false;
		return b;
	}

}
