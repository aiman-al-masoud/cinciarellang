package com.luxlunaris.cincia.frontend.tokens;

public class AbstractToken implements Token{
	
	@Override
	public String toString() {
				
		try {
			String value = this.getClass().getDeclaredField("value").get(this)+"";
			return "Token(type="+this.getClass().getSimpleName()+", "+"value="+value+")";
			
		} catch (NoSuchFieldException |SecurityException  |IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} 
		
		return "";
	}
	
}
