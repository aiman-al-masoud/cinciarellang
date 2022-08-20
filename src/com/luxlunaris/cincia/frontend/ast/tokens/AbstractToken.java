package com.luxlunaris.cincia.frontend.ast.tokens;

import com.luxlunaris.cincia.frontend.ast.interfaces.Token;

public class AbstractToken implements Token{
	
	@Override
	public String toString() {
				
		try {
			String value = this.getClass().getDeclaredField("value").get(this)+"";
//			this.getClass().getSimpleName()
			return "<"+value+">";
			
		} catch (NoSuchFieldException |SecurityException  |IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} 
		
		return "";
	}
	
}
