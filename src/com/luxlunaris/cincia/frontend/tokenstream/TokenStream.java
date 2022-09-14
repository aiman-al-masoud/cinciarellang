package com.luxlunaris.cincia.frontend.tokenstream;

import java.util.function.Predicate;

import com.luxlunaris.cincia.frontend.ast.interfaces.Token;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Bool;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Float;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Int;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Str;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keyword;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operator;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;
import com.luxlunaris.cincia.frontend.ast.tokens.punctuation.Punctuation;
import com.luxlunaris.cincia.frontend.ast.tokens.punctuation.Punctuations;
import com.luxlunaris.cincia.frontend.charstream.CharStream;

public class TokenStream {

	CharStream cStream;
	Token currTok;

	public TokenStream(CharStream cStream) {
		this.cStream = cStream;
	}

	public void next() {

		// skip whitespace
		readWhile( c -> { return c == ' ' || c == '\t' || c=='\n'; });

		char curr  = cStream.peek();

		if(curr == '/') {
			
			String com = skipComment();
			
			if(com != null) { 
				next();
				return;
			}

		}

		if(Identifier.isIdStart(curr)) {

			String val = readWhile(Identifier::isId);
			Keywords kw = Keywords.fromString(val);
			Boolean b = Bool.stringToBool(val);
			Modifiers mod = Modifiers.fromString(val);
			
			// second condition to allow for methods with same name as keywords.
			if(kw==null  || currTok!=null && currTok.getValue().equals(Punctuations.DOT)) {
				currTok = new Identifier(val);
				return;
			} 

			if(b!=null) {
				currTok = new Bool(b);
				return;
			}

			if(mod!=null) {
				currTok = new Modifier(mod);
				return;
			}

			currTok = new Keyword(kw);
			return;
		}

		//TODO: consider changing f-string syntax to something more like js
		if(curr=='"' || curr == '\'' || curr == 'f') {
			currTok = readString();
			return;
		}

		if(Character.isDigit(curr)) {
			currTok = readNumeric();
			return;
		}

		if(Operators.isOperator(curr)) {
			String val = readWhile(c->Operators.isOperator(c));
			Operators op = Operators.fromString(val);

			if(op==null) {
				croak("'"+val+"' is not a valid operator");
			}

			currTok = new Operator(op);
			return;
		}


		if(Punctuations.isPunctuation(curr)) {
			
			currTok = new Punctuation(Punctuations.fromChar(curr));
			cStream.next();
			
			
			// ignore multiple contiguous ;
			// while peek() is equal to ; AND current is ; keep on skipping to the next
			while( Punctuations.STM_SEP.toString().equals(cStream.peek()+"") && currTok.getValue().equals(Punctuations.STM_SEP)) {
				cStream.next();
			}

			return;
		}


		//if everything fails, normally at the end of the char stream:
		currTok = null;
	}

	public Token peek() {
		return currTok;
	}


	public void croak(String message) throws RuntimeException{
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
			String com = skipMultilineComment();
			return com;
		default: // not a comment, it may be a division sign 
			cStream.prev(); // restore division sign
			return null;

		}

	}


	private String skipSingleLineComment() {
		String com = readWhile(c->c!='\n');
		cStream.next();
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









}
