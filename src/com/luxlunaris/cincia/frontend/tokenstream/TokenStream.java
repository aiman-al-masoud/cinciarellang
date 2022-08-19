package com.luxlunaris.cincia.frontend.tokenstream;

import java.util.function.Predicate;

import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.token.Float;
import com.luxlunaris.cincia.frontend.token.Identifier;
import com.luxlunaris.cincia.frontend.token.Int;
import com.luxlunaris.cincia.frontend.token.Keyword;
import com.luxlunaris.cincia.frontend.token.Keywords;
import com.luxlunaris.cincia.frontend.token.Operator;
import com.luxlunaris.cincia.frontend.token.Operators;
import com.luxlunaris.cincia.frontend.token.Punctuation;
import com.luxlunaris.cincia.frontend.token.Punctuations;
import com.luxlunaris.cincia.frontend.token.Str;
import com.luxlunaris.cincia.frontend.token.Token;
import com.luxlunaris.cincia.frontend.token.Bool;

public class TokenStream {

	CharStream cStream;
	Token currTok;

	public TokenStream(CharStream cStream) {
		this.cStream = cStream;
	}

	public void next() {

		// skip whitespace
		readWhile( c -> { return c == ' ' || c == '\t'; });


		char curr  = cStream.peek();
//		System.out.println("next() "+curr);

		if(curr == '/') {
			skipComment();
			next();
			return;
		}


		if(isIdStart(curr)) {

			String val = readWhile(this::isId);
			Keywords kw = Keywords.fromString(val);
			Boolean b = Bool.stringToBool(val);

			if(kw == null) {
				currTok = new Identifier(val);
				return;
			}

			if(b == null) {
				currTok = new Keyword(kw);
				return;
			}

			currTok = new Bool(b);
			return;
		}
		
		if(curr=='"' || curr == '\'' || curr == 'f') {
			currTok = readString();
			return;
		}
		
		if(Character.isDigit(curr)) {
			currTok = readNumeric();
//			System.out.println(curr);
//			cStream.next();
			return;
		}
		
		if(Operators.isOperator(curr+"")) {
			String val = readWhile(c->Operators.isOperator(c+""));
			currTok = new Operator(Operators.fromString(val));
			return;
		}
		
		
		if(Punctuations.isPunctuation(curr)) {
			currTok = new Punctuation(Punctuations.fromChar(curr));
			cStream.next();
			return;
		}
		
		
		//if everything fails, normally at the end of the char stream:
		currTok = null;
	}

	public Token peek() {
		return currTok;
	}


	public void croak(String message) {
		cStream.croak(message);
	}


	public boolean isEnd() {
		return currTok == null;
	}


	private String skipComment(){

		eat('/');

		switch (cStream.peek()) {
		case '/':
			return skipSingleLineComment();
		case '*':
			eat('*');
			return skipMultilineComment();
		default:
			croak("Expected '/' or '*'");
			return null;
		}

	}


	private String skipSingleLineComment() {
		String com = readWhile(c->c!='\n');
		eat('\n');
		return com;
	}


	private String skipMultilineComment() {
		return skipMultilineComment("");
	}

	private String skipMultilineComment(String com) {

		com = com+readWhile(c->c!='*');
		eat('*');

		if(cStream.peek()=='/') {
			eat('/');
			return com;
		}

		return '*'+skipMultilineComment(com);

	}


	private String readWhile(Predicate<Character> pred) {

		String res = "";
		char curr;

		while(! cStream.isEnd()  && pred.test(curr = cStream.peek())) {
			res+=curr;
			cStream.next();
		}

		return res;
	}


	private void eat(char c) {
		if(cStream.peek() == c) {
			cStream.next();
		}else {
			croak("Expected '"+c+"'");
		}
	}


	private Token readNumeric() { //TODO: .111 and 1e4

		String num = readWhile(c->Character.isDigit(c));

		if(cStream.peek()=='.') {
			eat('.');
			num+="."+readWhile(c->Character.isDigit(c));
			return new Float(Double.parseDouble(num));
		}

		return new Int(Integer.parseInt(num));
	}


	private Token readString() {

		String str = "";
		int i = 0;
		boolean escape = false;
		boolean doubleQuote = true;
		char curr;

		switch (cStream.peek()){
		case 'f':
			eat('f');
			eat('"'); //TODO: also '\''
			break;
		case '"':
			eat('"');
			break;
		case '\'':
			eat('\'');// eat '
			doubleQuote = false;
			break;
		default:
			break;
		}


		while (!cStream.isEnd()) {
			curr = cStream.peek();

			if(curr == '\\') {
				escape = true;
				eat('\\');
				continue;
			}

			if(escape){
				str+='\\';
				str+=curr;
				escape = false;
				cStream.next();
				continue;
			}

			if( (curr == '"' && doubleQuote)   ||   (curr == '\'' && !doubleQuote) ){
				cStream.next(); //eat delim
				break;
			}

			// passed all guards, now normal char
			str+=curr;
			cStream.next();

		}

		return new Str(str);
	}



	boolean isIdStart(char c){
		return  Character.isLetter(c) || c == '_';
	}

	boolean isId(char c){
		return isIdStart(c) || Character.isDigit(c);
	}
	




}
