package com.luxlunaris.cincia.frontend.ast.tokens;


public class Bool extends AbstractToken{

	public final boolean value;
	public static final String TRUE = "true";
	public static final String  FALSE = "false";


	public Bool(boolean value) {
		this.value  =value;
	}

	public static Boolean stringToBool(String string) {

		boolean isTrue = string.equals(TRUE)? true : false;
		boolean isFalse = string.equals(FALSE)? true : false;
		boolean isNull = !(isTrue || isFalse); //neither true nor false
		return isNull ? null : isTrue? true : false;
//		Boolean b = isTrue? true : (isFalse? false : null); 
	}

}
