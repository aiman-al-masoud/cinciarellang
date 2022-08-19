package com.luxlunaris.cincia.frontend.charstream;

public class CharStream {
	
	public static final char EOF = (char)-1;
	
	String source;
	int pos, row, col;
	
	public CharStream(String source) {
		this.source = source;
		pos = row = col = 0;
	}
	
	public void next() {
		pos++;
		
		if(peek() == '\n') {
			row++;
			col = 0;
		}else {
			col++;
		}
		
	}
	
	public char peek() {
		return isEnd() ? EOF : source.charAt(pos);
	}
	
	public void croak(String message){
		throw new RuntimeException(message);
	}
	
	public boolean isEnd() {
		return pos >= source.length();
	}
	
	
	
	
	
}
