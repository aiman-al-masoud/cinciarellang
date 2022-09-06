package com.luxlunaris.cincia.frontend.charstream;

public class CinciaSytnaxException extends RuntimeException {
	
	public int pos, row, col;
	public String msg;
	
	public CinciaSytnaxException(String msg, int pos, int row, int col) {
		super(msg+" at "+row+":"+col);
		this.pos = pos;
		this.row = row;
		this.col = col;
		this.msg = msg;
	}
	
	
	
	
	
}
