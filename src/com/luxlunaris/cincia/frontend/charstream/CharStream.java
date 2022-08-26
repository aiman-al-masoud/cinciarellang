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
	
	public void prev() {
		
//		if(peek() == '\n') {
//			row--;
//		}else {
//			col--;
//		}
//		
		pos--;
	}
	
	public char peek() {
		return isEnd() ? EOF : source.charAt(pos);
	}
	
	public void croak(String message) throws RuntimeException{
		throw new RuntimeException(message+" at "+row+":"+col);
	}
	
	public boolean isEnd() {
		return pos >= source.length();
	}
	
	
	
	
	
	
}
