package com.luxlunaris.cincia.frontend.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.Signature;
import com.luxlunaris.cincia.frontend.ast.declarations.SingleDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.TernaryExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AddExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AndExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AssignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.ComparisonExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.MulExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.OrExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ClassExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.DictComprehension;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.DictExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.InterfaceExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ListComprehension;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ListExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.CalledExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.DotExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.IndexedExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.ReassignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.primary.BracketedExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.DestructuringExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.MinusExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.NegationExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.LeftValue;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PostfixExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PrimaryExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.interfaces.Token;
import com.luxlunaris.cincia.frontend.ast.interfaces.UnaryExpression;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.ast.statements.DeclarationStatement;
import com.luxlunaris.cincia.frontend.ast.statements.ExpressionStatement;
import com.luxlunaris.cincia.frontend.ast.statements.ImportStatement;
import com.luxlunaris.cincia.frontend.ast.statements.exception.CatchClause;
import com.luxlunaris.cincia.frontend.ast.statements.exception.ThrowStatement;
import com.luxlunaris.cincia.frontend.ast.statements.exception.TryStatement;
import com.luxlunaris.cincia.frontend.ast.statements.iteration.ForStatement;
import com.luxlunaris.cincia.frontend.ast.statements.iteration.WhileStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.BreakStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.ContinueStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.ReturnStatement;
import com.luxlunaris.cincia.frontend.ast.statements.labelled.CaseStatement;
import com.luxlunaris.cincia.frontend.ast.statements.labelled.DefaultStatement;
import com.luxlunaris.cincia.frontend.ast.statements.selection.IfStatement;
import com.luxlunaris.cincia.frontend.ast.statements.selection.MatchStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.Str;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;
import com.luxlunaris.cincia.frontend.ast.tokens.punctuation.Punctuations;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;


public class Parser {

	TokenStream tStream;

	public Parser(TokenStream tStream) {
		this.tStream = tStream;
		this.tStream.next(); //initialize
	}

	public List<Statement> parse(){

		ArrayList<Statement> res = new ArrayList<Statement>();

		while(!tStream.isEnd()) {
			res.add(parseStatement());
		}

		return res;
	}



	public Statement parseStatement() {

		Statement res;

		if(tStream.peek().getValue().equals(Keywords.IF)) {
			res = parseIfStatement();
		}else if(tStream.peek().getValue().equals(Keywords.MATCH)) {
			res = parseMatchStatement();
		}else if(tStream.peek().getValue().equals(Punctuations.CURLY_OPN)) {
			res = parseCompStatement();
		}else if(tStream.peek().getValue().equals( Keywords.FOR )) {
			res = parseForStatement();
		}else if(tStream.peek().getValue().equals( Keywords.WHILE )) {
			res = parseWhileStatement();
		}else if(tStream.peek().getValue().equals( Keywords.TRY )) {
			res = parseTryStatement();
		}else if(tStream.peek().getValue().equals( Keywords.THROW )) {
			res = parseThrowStatement();
		}else if(tStream.peek().getValue().equals( Keywords.RETURN )) {
			res = parseReturnStatement();
		}else if(tStream.peek().getValue().equals( Keywords.CONTINUE )) {
			res = parseContinueStatement();
		}else if(tStream.peek().getValue().equals( Keywords.BREAK )) {
			res = parseBreakStatement();
		}else if(tStream.peek().getValue().equals( Keywords.CASE )) {
			res = parseCaseStatement();
		}else if(tStream.peek().getValue().equals( Keywords.DEFAULT )) {
			res = parseDefaultStatement();
		}else if(tStream.peek().getValue().equals( Keywords.IMPORT )) {
			res = parseImportStatement();
		}else if(Modifiers.isModifier(tStream.peek().getValue().toString())) { //TODO: fix tmp solution requiring at least one modifier before any declaration to identify it as a declaration
			res = parseDeclStatement();
		}else {
			res = parseExpressionStatement();
		}

		return res;		
	}

	public ExpressionStatement parseExpressionStatement() {

		ExpressionStatement eS = new ExpressionStatement(parseExpression());
		eat(Punctuations.STM_SEP);
		return eS;
	}

	public IfStatement parseIfStatement() {

		eat(Keywords.IF);
		IfStatement ifS = new IfStatement();

		ifS.cond =  parseExpression();
		ifS.thenBlock =  parseCompStatement();

		if(tStream.peek().getValue().equals(Keywords.ELSE)) {
			eat(Keywords.ELSE);
			ifS.elseBlock = parseCompStatement();
		}

		return ifS;
	}

	public MatchStatement parseMatchStatement() {

		eat(Keywords.MATCH);
		MatchStatement mS = new MatchStatement();
		mS.cond = parseExpression();

		while(!tStream.isEnd()) {
			if(tStream.peek().getValue().equals(Keywords.CASE)) {
				mS.add(parseCaseStatement());
			}else {
				break;
			}
		}

		if(tStream.peek().getValue().equals(Keywords.DEFAULT)) {
			mS.defaultStatement = parseDefaultStatement();
		}

		return mS;
	}


	public CompoundStatement parseCompStatement() {

		eat(Punctuations.CURLY_OPN);
		CompoundStatement cS = new CompoundStatement();

		while(!tStream.isEnd()) {

			if(tStream.peek().getValue().equals(Punctuations.CURLY_CLS)) {
				break;
			}

			cS.add(parseStatement());
		}

		eat(Punctuations.CURLY_CLS);

		return cS;
	}

	public DeclarationStatement parseDeclStatement() {
		
		DeclarationStatement dS = new DeclarationStatement(parseDeclaration());
		eat(Punctuations.STM_SEP);
		return dS;
	}
	

	public ForStatement parseForStatement() {
		
		eat(Keywords.FOR);
		ForStatement fS = new ForStatement();
		
		while(!tStream.isEnd()) {
			
			if (tStream.peek() instanceof Identifier ) {
				fS.loopVars.add( (Identifier)tStream.peek() );
				tStream.next(); //eat identifier
				continue;
			}
			
			if(tStream.peek().getValue().equals(Punctuations.COMMA)) { //TODO comma is really useless here 
				eat(Punctuations.COMMA);
				continue;
			}
			
			break;
		}
		
		
		fS.iterable = parseExpression();
		fS.block = parseCompStatement();
		
		return fS;
	}

	public WhileStatement parseWhileStatement() {
		eat(Keywords.WHILE);
		
		WhileStatement wS = new WhileStatement();
		wS.cond = parseExpression();
		wS.block = parseCompStatement();
		
		return wS;
	}

	public TryStatement parseTryStatement() {
		
		eat(Keywords.TRY);
		
		TryStatement tS = new TryStatement();
		tS.tryBlock = parseCompStatement();
		
		while (!tStream.isEnd()) {
			
			if(tStream.peek().getValue().equals(Keywords.CATCH)) {
				tS.add(parseCatchClause());
			}else {
				break;
			}
			
		}
		
		if(tStream.peek().getValue().equals(Keywords.FINALLY)) {
			tS.finallyBlock = parseCompStatement();
		}
		
		return tS;
	}
	
	public CatchClause parseCatchClause() {
		eat(Keywords.CATCH);
		
		CatchClause cc = new CatchClause();
		cc.throwable = parseExpression();
		cc.block = parseCompStatement();
		
		return cc;
	}

	public ThrowStatement parseThrowStatement() {
		eat(Keywords.THROW);
		
		ThrowStatement tS = new ThrowStatement(parseExpression());		
		eat(Punctuations.STM_SEP);
		return tS;
	}
	
	

	public ReturnStatement parseReturnStatement() {
		eat(Keywords.RETURN);
		ReturnStatement rS  = new ReturnStatement();
		
		while(!tStream.isEnd()) {
			
			if(tStream.peek().getValue().equals(Punctuations.STM_SEP)) {
				break;
			}
			
			if(tStream.peek().getValue().equals(Punctuations.COMMA)) { //TODO: also useless, could do with whitespace alone, but maybe this is useful in some sense...
				eat(Punctuations.COMMA);
				continue;
			}
			
			rS.addValue(parseExpression());
		}
		
		eat(Punctuations.STM_SEP);
		return rS;
	}

	public ContinueStatement parseContinueStatement() {
		eat(Keywords.CONTINUE);
		return new ContinueStatement();
	}

	public BreakStatement parseBreakStatement() {
		eat(Keywords.BREAK);
		return new BreakStatement();
	}


	public CaseStatement parseCaseStatement() {
		eat(Keywords.CASE);
		
		CaseStatement cS  = new CaseStatement();
		cS.cond = parseExpression();
		cS.block = parseCompStatement();
		return cS;
	}

	public DefaultStatement parseDefaultStatement() {
		eat(Keywords.DEFAULT);
		DefaultStatement dS  = new DefaultStatement();
		dS.block = parseCompStatement();
		return dS;
	}


	public ImportStatement parseImportStatement() {
		
		eat(Keywords.IMPORT);
		ImportStatement iS = new ImportStatement();
		
		while(!tStream.isEnd()) {
			
			if(tStream.peek().getValue().equals(Keywords.FROM)) {
				break;
			}
			
			if(tStream.peek().getValue().equals(Punctuations.COMMA)) { //TODO: saaaaaaaaaaammmmmmmmmeeee 
				eat(Punctuations.COMMA);
				continue;
			}
			
			Entry<DotExpression, Identifier> imported = parseImported();
			iS.addImport(imported.getKey(), imported.getValue());
		}
		
		eat(Keywords.FROM);
		try {
			iS.fromPath =  (Str)tStream.peek();
		}catch (ClassCastException e) {
			tStream.croak("Expected import path (string constant)");
		} 
		
		eat(Punctuations.STM_SEP);
		return iS;
	}
	
	
	public Entry<DotExpression, Identifier> parseImported(){
		
		DotExpression dEx = parseDotExpression();
		Identifier alias = null; // can be null
		
		if(tStream.peek().getValue().equals(Keywords.AS)) {
			 eat(Keywords.AS);
			 alias = parseIdentifier();
		}
		
		return Map.entry(dEx, alias);
	}
	

	
	//TODO: problem, Signature is also a Declaration
	public Declaration parseDeclaration() {
		
		MultiDeclaration mD = parseMultiDeclaration();
		return mD.declarations.size()==1? mD.declarations.get(0) : mD;
	}


	public MultiDeclaration parseMultiDeclaration() {
		
		MultiDeclaration mD = new MultiDeclaration();
		mD.addDeclaration(parseSingleDeclaration());

		while(!tStream.isEnd()) {	
			
			if(tStream.peek().getValue().equals(Punctuations.COMMA)) {
				eat(Punctuations.COMMA);
				mD.addDeclaration(parseSingleDeclaration());
				continue;
			}
			
			break;
		}
		
		return mD;
	}


	public SingleDeclaration parseSingleDeclaration() {
		
		SingleDeclaration sD = new SingleDeclaration();
		
		while (!tStream.isEnd()){
			
			try {
				sD.addModifier((Modifier)tStream.peek());
				tStream.next();
			}catch (ClassCastException e) {
				break;
			}
		}
		
		
		try {
			sD.name = (Identifier)tStream.peek();
			tStream.next();
		}catch (ClassCastException e) {
			tStream.croak("Expected identifier (variable name)");
		}
		
		
		if(tStream.peek().getValue().equals(Punctuations.COL)) { //TODO: turn int, float, bool into identifiers
			eat(Punctuations.COL);
			sD.type = parseIdentifier();
			tStream.next();
		}
		
		return sD;
	}
	
	

	public Signature parseSignature() {
		
		Signature sg = new Signature();
		
		while (!tStream.isEnd()){
			
			try {
				sg.addModifier((Modifier)tStream.peek());
				tStream.next();
			}catch (ClassCastException e) {
				break;
			}
		}
		
		sg.params = parseDeclaration();
		
		
		if(tStream.peek().getValue().equals(Punctuations.COL)) {
			eat(Punctuations.COL);
			
			try {
				sg.returnType = (Identifier) tStream.peek();
				tStream.next();
			}catch (ClassCastException e) {
				tStream.croak("Expected identifier (class/type name)");
			}
			
		}
		
		
		return sg;
	}



	
	public MultiExpression parseMultiExpression() {

	}
	
	public Expression parseExpression() {
		
		// assignment (assignment or conditional)
		return parseAsgnExpression();
		
	}
	

	public Expression parseAsgnExpression() {
		
		ArrayList<Expression> chain = new ArrayList<Expression>(); 
		chain.add(parseCondExpression()); 
		
		if(!tStream.peek().getValue().equals(Operators.ASSIGN)) {
			return chain.get(0);
		}
	
		while(!tStream.isEnd()) {
			
			if(!tStream.peek().getValue().equals(Operators.ASSIGN)) {
				break;
			}
			
			eat(Operators.ASSIGN);
			chain.add(parseCondExpression());  			
		}
		
		// z = y = x = 1  ---->  1, x, y, z
		// to traverse chain from right to left
		Collections.reverse(chain);
		
		
		AssignmentExpression asgn1 = new AssignmentExpression();
		asgn1.right = chain.get(0); //  preamble
		
		for(int i = 1; i < chain.size(); i++) {
			asgn1.left = (LeftValue) chain.get(i);
			AssignmentExpression asgn2 = new AssignmentExpression();
			asgn2.right = asgn1;
			asgn1 = asgn2;
		}
		
//		asgn1.left = (LeftValue) chain.get(1);
//		
//		AssignmentExpression asgn2 = new AssignmentExpression();
//
//		asgn2.left = (LeftValue) chain.get(2);
//		asgn2.right = asgn1;
//		
//		AssignmentExpression asgn3 = new AssignmentExpression();
//
//		asgn3.left = (LeftValue) chain.get(3);
//		asgn3.right = asgn2;
		
		
		
	}
	
	
	
	public Expression parseCondExpression() {

	}
	
	public OrExpression parseOrExpression() {

	}

	public TernaryExpression parseTernary() {

	}


	public AndExpression parseAndExpression() {

	}

	public ComparisonExpression parseComparisonExpression() {

	}

	public AddExpression parseAddExpression() {

	}

	public MulExpression parseMulExpression() {

	}

	public UnaryExpression parseUnaryExpression() {

	}

	public DestructuringExpression parseDestrExpression() {

	}

	public MinusExpression parseMinusExpression() {

	}

	public NegationExpression parsenNegationExpression() {

	}

	public PostfixExpression parsePostfixExpression() {

	}


	public CalledExpression parseCalledExpression() {

	}


	public DotExpression parseDotExpression() {

	}

	public IndexedExpression parseIndexedExpression() {

	}

	public ReassignmentExpression parseReasgnExpression() {

	}

	public PrimaryExpression parsePrimaryExpression() {
	
		
		// TODO: change EBNF, objects expressions are now primary expressions (does that make sense?)
		// if it starts with modifer, or 'class' or 'interface' or '{' or '[' it's an object
		if(tStream.peek().getValue().equals(Punctuations.CURLY_OPN) || tStream.peek().getValue().equals(Punctuations.SQBR_OPN) || tStream.peek() instanceof Modifier || tStream.peek().getValue().equals(Keywords.CLASS)|| tStream.peek().getValue().equals(Keywords.INTERFACE)) {
			return parseObjectExpression();
		}
		
		// bracketed expression
		
		
		// constant values
		
	}
	
	
	public ObjectExpression parseObjectExpression() {

	}
	
	public LambdaExpression parseLambdaExpression() {

	}

	public ClassExpression parseClassExpression() {

	}

	public InterfaceExpression parseInterfaceExpression() {

	}

	public ListExpression parseListExpression() {

	}

	public DictExpression parseDictExpression() {

	}



	public ListComprehension parseListComprehension() {

	}

	public DictComprehension parseDictComprehension() {

	}


	public BracketedExpression parseBracketedExpression() {

	}

	public Token parseConstant() {

	}


	public Identifier parseIdentifier() {

	}


	public void eat(Object value) {

		if (!tStream.peek().getValue().equals(value)) {
			tStream.croak("Expected "+value);
		}

		tStream.next();
	}







}
