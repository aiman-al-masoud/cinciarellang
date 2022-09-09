package com.luxlunaris.cincia.frontend.charstream;

import com.luxlunaris.cincia.frontend.charstream.CinciaSyntaxException;

public class CharStream {
	
	public static final char EOF = (char)-1;
	
	String source;
	int pos, row, col;
	int rowStartPos; // starting position of current row
	
	public CharStream(String source) {
		this.source = source;
//		pos = row = col = 0;
		pos = 0;
		row  = col = 1;
	}
	
	public void next() {
		pos++;
		
		if(peek() == '\n') {
			row++;
			col = 0;
			rowStartPos = pos;
		}else {
			col++;
		}
		
	}
	
	public void prev() {
		
		//TODO
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
	
	public void croak(String message) throws CinciaSyntaxException{
//		throw new RuntimeException(message+" at "+row+":"+col);
		throw new CinciaSyntaxException(message, pos, row, col, rowStartPos);
	}
	
	public boolean isEnd() {
		return pos >= source.length();
	}
	
	
	public int getPos() {
		return pos;
	}
	
	
	
}
