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
		
		
		
	}
	
	public ExpressionStatement parseExpressionStatement() {
		
	}
	
	public IfStatement parseIfStatement() {
		
	}
	
	public MatchStatement parseMatchStatement() {
		
	}
	
	
	public CompoundStatement parseCompStatement() {
		
	}
	
	public DeclarationStatement parseDeclStatement() {
		
	}
	
	public ForStatement parseForStatement() {
		
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
		
	}
	
	public DefaultStatement parseDefaultStatement() {
		
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
