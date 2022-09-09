package com.luxlunaris.cincia.frontend.charstream;

public class CinciaSyntaxException extends RuntimeException {
	
	public int pos, row, col, rowStartPos;
	public String msg;
	
	public CinciaSyntaxException(String msg, int pos, int row, int col, int rowStartPos) {
		super(msg+" at "+row+":"+col);
		this.pos = pos;
		this.row = row;
		this.col = col;
		this.msg = msg;
		this.rowStartPos = rowStartPos;
	}
	
	
	
	
	
}
