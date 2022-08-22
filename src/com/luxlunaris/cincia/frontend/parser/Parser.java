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
import com.luxlunaris.cincia.frontend.ast.tokens.Bool;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.Int;
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

		ExpressionStatement eS = new ExpressionStatement(parseSingleExpression());
		eat(Punctuations.STM_SEP);
		return eS;
	}

	public IfStatement parseIfStatement() {

		eat(Keywords.IF);
		IfStatement ifS = new IfStatement();

		ifS.cond =  parseSingleExpression();
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
		mS.cond = parseSingleExpression();

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
		
		
		fS.iterable = parseSingleExpression();
		fS.block = parseCompStatement();
		
		return fS;
	}

	public WhileStatement parseWhileStatement() {
		eat(Keywords.WHILE);
		
		WhileStatement wS = new WhileStatement();
		wS.cond = parseSingleExpression();
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
		cc.throwable = parseSingleExpression();
		cc.block = parseCompStatement();
		
		return cc;
	}

	public ThrowStatement parseThrowStatement() {
		eat(Keywords.THROW);
		
		ThrowStatement tS = new ThrowStatement(parseSingleExpression());		
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
			
			rS.addValue(parseSingleExpression());
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
		cS.cond = parseSingleExpression();
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
		
		DotExpression dEx = parseDotExpression(null);//TODO:: buruf????
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
		mD.addDeclaration(parseSingleDeclaration(parseModifiers()));

		while(!tStream.isEnd()) {	
			
			if(tStream.peek().getValue().equals(Punctuations.COMMA)) {
				eat(Punctuations.COMMA);
				mD.addDeclaration(parseSingleDeclaration(parseModifiers()));
				continue;
			}
			
			break;
		}
		
		return mD;
	}
	
	public List<Modifier> parseModifiers(){
		
		ArrayList<Modifier> res = new ArrayList<Modifier>();
		
		while (!tStream.isEnd()){
				
			try {
				res.add((Modifier)tStream.peek());
				tStream.next();
			}catch (ClassCastException e) {
				break;
			}
		}
		
		return res;
	}


	public SingleDeclaration parseSingleDeclaration(List<Modifier> modifiers) {
		
		SingleDeclaration sD = new SingleDeclaration();
		
//		while (!tStream.isEnd()){
//			
//			try {
//				sD.addModifier((Modifier)tStream.peek());
//				tStream.next();
//			}catch (ClassCastException e) {
//				break;
//			}
//		}
//		
		
//		sD.modifiers = parseModifiers();
		
		sD.modifiers = modifiers;
		
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
	
	

	public Signature parseSignature(List<Modifier> modifiers) {
		
		Signature sg = new Signature();
		
//		while (!tStream.isEnd()){
//			
//			try {
//				sg.addModifier((Modifier)tStream.peek());
//				tStream.next();
//			}catch (ClassCastException e) {
//				break;
//			}
//		}
		
//		sg.modifiers = parseModifiers();
		sg.modifiers = modifiers;
		
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

	
	public Expression parseExpression() {
		MultiExpression mE = parseMultiExpression();
		return mE.expressions.size()==1? mE.expressions.get(0) : mE;
	}
	
	public MultiExpression parseMultiExpression() {
		//comma separated single expressions		
		
		MultiExpression mE = new MultiExpression();
		mE.addExpression(parseSingleExpression());
		
		while(!tStream.isEnd()) {	
			
			if(tStream.peek().getValue().equals(Punctuations.COMMA)) {
				eat(Punctuations.COMMA);
				mE.addExpression(parseSingleExpression());

				continue;
			}
			
			break;
		}
		
		return mE;
		
	}
	
	public Expression parseSingleExpression() {
		
		// assignment (assignment or conditional)
		return parseAsgnExpression();
		
	}
	
	
	//right assoc
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
			
			try {
				asgn1.left = (LeftValue) chain.get(i);
				AssignmentExpression asgn2 = new AssignmentExpression();
				asgn2.right = asgn1;
				asgn1 = asgn2;
			}catch (ClassCastException e) {
				tStream.croak("Expected left-value, got: '"+chain.get(i)+"'");
			}
			
		}
		
		return asgn1;
		
	}
	
	
	
	public Expression parseCondExpression() {
		
		//OrExpression or TernaryExpression
		
		Expression oE = parseOrExpression();
		
		if(!tStream.peek().getValue().equals(Punctuations.QUESTION_MARK)) {
			return oE;
		}
		
		TernaryExpression tE = new TernaryExpression();
		tE.cond = oE;
		eat(Punctuations.QUESTION_MARK);
		tE.thenExpression = parseOrExpression();
		eat(Punctuations.COL);
		tE.elseExpression = parseOrExpression();
		
		return tE;
	}
	
	//left assoc
	public OrExpression parseOrExpression() {
		
		OrExpression oE = new OrExpression(); 
		oE.left = parseAndExpression();
		
		
		while(!tStream.isEnd()) {
			if(tStream.peek().getValue().equals(Operators.OR)) {
				eat(Operators.OR);
				oE.right = parseAndExpression();
				OrExpression oE2 = new OrExpression();
				oE2.left = oE;
				oE = oE2;
			}else {
				break;
			}
		}
		
		return oE;
	}

	public AndExpression parseAndExpression() {
		
		AndExpression one = new AndExpression();
		one.left = parseComparisonExpression();
		
		while(!tStream.isEnd()) {
			if(tStream.peek().getValue().equals(Operators.AND)) {
				eat(Operators.AND);
				one.right = parseComparisonExpression();
				AndExpression two = new AndExpression();
				two.left = one;
				one = two;
			}else {
				break;
			}
		}
		
		return one;
	}

	public ComparisonExpression parseComparisonExpression() {

		ComparisonExpression one = new ComparisonExpression();
		one.left = parseAddExpression();
		
		while(!tStream.isEnd()) {
			if(Operators.isComparisonOperator(tStream.peek().getValue())) {
				one.op = (Operators)tStream.peek().getValue();
				tStream.next();
				one.right = parseAddExpression();
				ComparisonExpression two = new ComparisonExpression();
				two.left = one;
				one = two;
			}else {
				break;
			}
		}
		
		return one;
		
	}

	public AddExpression parseAddExpression() {
		AddExpression one = new AddExpression();
		one.left = parseMulExpression();
		
		while(!tStream.isEnd()) {
			if(Operators.isAddOperator(tStream.peek().getValue())) {
				one.op = (Operators)tStream.peek().getValue();
				tStream.next();
				one.right = parseMulExpression();
				AddExpression two = new AddExpression();
				two.left = one;
				one = two;
			}else {
				break;
			}
		}
		
		return one;
	}

	public MulExpression parseMulExpression() {
		
		MulExpression one = new MulExpression();
		one.left = parseUnaryExpression();
		
		while(!tStream.isEnd()) {
			if(Operators.isMulOperator(tStream.peek().getValue())) {
				one.op = (Operators)tStream.peek().getValue();
				tStream.next();
				one.right = parseUnaryExpression();
				MulExpression two = new MulExpression();
				two.left = one;
				one = two;
			}else {
				break;
			}
		}
		
		return one;
	}

	public UnaryExpression parseUnaryExpression() {
		
		if(tStream.peek().getValue().equals(Operators.MINUS)) {
			return parseMinusExpression();
		}else if(tStream.peek().getValue().equals(Operators.NOT)  ) {
			return parseNegationExpression();
		}else if(tStream.peek().getValue().equals(Operators.ASTERISK)  ) {
			return parseDestrExpression();
		}else {
			return parsePostfixExpression();
		}
	}

	public MinusExpression parseMinusExpression() {
		eat(Operators.MINUS);
		return new MinusExpression(parseUnaryExpression());
	}

	public NegationExpression parseNegationExpression() {
		eat(Operators.NOT);
		return new NegationExpression(parseUnaryExpression());

	}
	public DestructuringExpression parseDestrExpression() {
		eat(Operators.ASTERISK);
		return new DestructuringExpression(parseUnaryExpression());
	}

	
	public PostfixExpression parsePostfixExpression() {
		
		PrimaryExpression exp = parsePrimaryExpression();
		
		if(tStream.peek().getValue().equals(Punctuations.PAREN_OPN)) {
			return parseCalledExpression(exp);
		}else if(tStream.peek().getValue().equals(Punctuations.SQBR_OPN)) {
			return parseIndexedExpression(exp);
		}else if(tStream.peek().getValue().equals(Punctuations.DOT)) {
			return parseDotExpression(exp);
		}else if(Operators.isReassignmentOperator(tStream.peek().getValue())) {
			return parseReasgnExpression(exp);
		}else {
			return exp;
		}
		
	}


	public CalledExpression parseCalledExpression(PostfixExpression left) {
		eat(Punctuations.PAREN_OPN);
		CalledExpression cE = new CalledExpression();
		cE.callable = left;
		cE.args = parseMultiExpression();
		eat(Punctuations.PAREN_CLS);
		return cE;
	}
	
	public IndexedExpression parseIndexedExpression(PostfixExpression left) {
		eat(Punctuations.SQBR_OPN);
		IndexedExpression iE = new IndexedExpression();
		iE.indexable = left;  
		iE.index = parseExpression(); //TODO: add slicing with ':'
		eat(Punctuations.SQBR_CLS);
		return iE;
	}
	
	public DotExpression parseDotExpression(PostfixExpression left) {
		eat(Punctuations.DOT);
		DotExpression dE = new DotExpression();
		dE.left = left;  
		dE.right = (DotExpression)parsePostfixExpression(); //TODO: mhhhhhhhhhhh
		eat(Punctuations.DOT);
		return dE;
	}

	public ReassignmentExpression parseReasgnExpression(PostfixExpression left) {
		ReassignmentExpression rE = new ReassignmentExpression();
		rE.left = left;
		rE.operator = (Operators)tStream.peek().getValue();
		tStream.next(); // eat operator
		rE.right =  parseExpression();
		return rE;
	}

	public PrimaryExpression parsePrimaryExpression() {
	
		
		// TODO: change EBNF, objects expressions are now primary expressions (does that make sense?)
		// if it starts with modifer, or 'class' or 'interface' or '{' or '[' it's an object
		if(tStream.peek().getValue().equals(Punctuations.CURLY_OPN) || tStream.peek().getValue().equals(Punctuations.SQBR_OPN) || tStream.peek() instanceof Modifier || tStream.peek().getValue().equals(Keywords.CLASS)|| tStream.peek().getValue().equals(Keywords.INTERFACE)) {
			return parseObjectExpression();
		}
		
		// bracketed expression
		if(tStream.peek().getValue().equals(Punctuations.PAREN_OPN)) {
			return parseBracketedExpression();
		}
		
		// identifiers
		if(tStream.peek().getValue() instanceof Identifier) {
			return parseIdentifier();
		}
		
		// constant values
//		if(tStream.peek().getValue() instanceof Int  || tStream.peek().getValue() instanceof Bool || tStream.peek().getValue() instanceof com.luxlunaris.cincia.frontend.ast.tokens.Float || tStream.peek().getValue() instanceof Str) {
			return parseConstant();
//		}
		
		
	}
	
	
	public BracketedExpression parseBracketedExpression() {
		eat(Punctuations.PAREN_OPN);
		Expression expression = parseExpression();
		eat(Punctuations.PAREN_CLS);
		return new BracketedExpression(expression);
	}
	
	
	public ObjectExpression parseObjectExpression() {
		
		// check if { or [   => dict or list
		if(tStream.peek().getValue().equals(Punctuations.SQBR_OPN)) {
			return parseList();
		}
		
		
		if(tStream.peek().getValue().equals(Punctuations.CURLY_OPN)) {
			return parseDict();
		}
		
		// get through the list of modifiers then check if class or interface or \ (lambda)
		List<Modifier> modifiers = parseModifiers();
		
		
		
		
	}
	
	public LambdaExpression parseLambdaExpression() {

	}

	public ClassExpression parseClassExpression() {

	}

	public InterfaceExpression parseInterfaceExpression() {

	}
	
	public ObjectExpression parseList() {
		
	}
	
	public ListExpression parseListExpression() {

	}
	
	public ListComprehension parseListComprehension() {

	}
	
	public ObjectExpression parseDict() {
		
	}
	
	public DictExpression parseDictExpression() {

	}
	
	public DictComprehension parseDictComprehension() {

	}

	

	public Token parseConstant() { //TODO: add constant interface and classcast check
		Token token = tStream.peek();
		tStream.next();
		return token;
	}


	public Identifier parseIdentifier() {
		
		Identifier id = null;
		
		try {
			id = (Identifier)tStream.peek();
			tStream.next();
		}catch (ClassCastException e) {
			tStream.croak("Expected id");
		}
		
		return id;
	}


	public void eat(Object value) {

		if (!tStream.peek().getValue().equals(value)) {
			tStream.croak("Expected "+value);
		}

		tStream.next();
	}







}
