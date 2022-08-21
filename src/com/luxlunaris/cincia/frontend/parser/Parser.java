package com.luxlunaris.cincia.frontend.parser;

import java.util.ArrayList;
import java.util.List;

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
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;
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
		
	}

	public TryStatement parseTryStatement() {

	}

	public ThrowStatement parseThrowStatement() {

	}

	public ReturnStatement parseReturnStatement() {

	}

	public ContinueStatement parseContinueStatement() {

	}

	public BreakStatement parseBreakStatement() {

	}


	public CaseStatement parseCaseStatement() {
		eat(Keywords.CASE);


	}

	public DefaultStatement parseDefaultStatement() {
		eat(Keywords.DEFAULT);
	}


	public ImportStatement parseImportStatement() {

	}


	public Declaration parseDeclaration() {

	}

	public Signature parseSignature() {

	}

	public MultiDeclaration parseMultiDeclaration() {

	}


	public SingleDeclaration parseSingleDeclaration() {

	}



	public Expression parseExpression() {

	}

	public MultiExpression parseMultiExpression() {

	}

	public AssignmentExpression parseAsgnExpression() {

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
