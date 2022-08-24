package com.luxlunaris.cincia.frontend.ast.tokens;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Token;

public abstract class AbstractToken implements Token{
	
	@Override
	public String toString() {
				
//		try {
//			String value = this.getClass().getDeclaredField("value").get(this)+"";
////			this.getClass().getSimpleName()
//			return "<"+value+">";
//			
//		} catch (NoSuchFieldException |SecurityException  |IllegalArgumentException | IllegalAccessException e) {
//			e.printStackTrace();
//		} 
//		
//		return "";
		
		return getValue() +"";
	}
	
	@Override
	public Object getValue() {
		
		try {
			 return this.getClass().getDeclaredField("value").get(this);
//			this.getClass().getSimpleName()
//			return value;
			
		} catch (NoSuchFieldException |SecurityException  |IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	@Override
	public Expression simplify() {
		return this;
	}
	
}
