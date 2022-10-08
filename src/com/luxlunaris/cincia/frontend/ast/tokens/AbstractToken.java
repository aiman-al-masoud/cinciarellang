package com.luxlunaris.cincia.frontend.ast.tokens;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Token;

public abstract class AbstractToken implements Token{
	
	@Override
	public String toString() {
		return getValue() +"";
	}
	
	@Override
	public Object getValue() {
		
		try {
			 return this.getClass().getDeclaredField("value").get(this);
			
		} catch (NoSuchFieldException |SecurityException  |IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	@Override
	public Expression simplify() {
		return this;
	}
	
	/**
	 * Tokens should NOT override equals(), as they should 
	 * always be compared by identity for TokenStream's
	 * backtracing functionality to work!
	 */
	@Override
	public final boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	
}
