package com.luxlunaris.cincia.frontend.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.luxlunaris.cincia.frontend.ast.declarations.FunctionDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.Signature;
import com.luxlunaris.cincia.frontend.ast.declarations.SingleDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
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
import com.luxlunaris.cincia.frontend.ast.interfaces.Constant;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.LeftValue;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PostfixExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PrimaryExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.interfaces.Token;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
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
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keyword;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;
import com.luxlunaris.cincia.frontend.ast.tokens.punctuation.Punctuations;
import com.luxlunaris.cincia.frontend.ast.tokens.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.tokens.type.SingleType;
import com.luxlunaris.cincia.frontend.ast.tokens.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.tokens.type.DictType;
import com.luxlunaris.cincia.frontend.ast.tokens.type.ListType;
import com.luxlunaris.cincia.frontend.ast.tokens.type.OneNameType;
import com.luxlunaris.cincia.frontend.ast.tokens.type.UnionType;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;



public class Parser {

	TokenStream tStream;

	public Parser(TokenStream tStream) {
		this.tStream = tStream;
		this.tStream.next(); //get first token
	}

	public List<Statement> parse(){

		ArrayList<Statement> res = new ArrayList<Statement>();

		while(!tStream.isEnd()) {
			res.add(parseStatement());
		}

		return res;
	}



	public Statement parseStatement() {

		Statement res = null;

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
		}else if(tStream.peek().getValue().equals( Keywords.DEC )) {
			// TODO: (elsewhere!) text preprocessing to auto-add dec keyword before actually feeding into parser
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
	
	public DeclarationStatement parseDeclStatement() {

		DeclarationStatement dS = new DeclarationStatement(parseDeclaration());
		eat(Punctuations.STM_SEP);
		return dS;
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


	public ForStatement parseForStatement() {
		
		eat(Keywords.FOR);
		ForStatement fS = new ForStatement();

		while(!tStream.isEnd()) {

			if (tStream.peek() instanceof Identifier ) {
				fS.loopVars.add( parseIdentifier() );
				continue;
			}

			if(tStream.peek().getValue().equals(Punctuations.COMMA)) { //TODO comma is really useless here 
				eat(Punctuations.COMMA);
				continue;
			}

			break;
		}
		
		eat(Keywords.IN); // TODO: or 'of' ?
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
			eat(Keywords.FINALLY);
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
		
		if(tStream.peek().getValue().equals(Punctuations.STM_SEP)) {
			eat(Punctuations.STM_SEP);
			return rS;
		}
		
		rS.expression = parseExpression();
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
	

	public MatchStatement parseMatchStatement() {

		eat(Keywords.MATCH);
		MatchStatement mS = new MatchStatement();
		mS.cond = parseSingleExpression();
		eat(Punctuations.CURLY_OPN);

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
		
		eat(Punctuations.CURLY_CLS);
		return mS;
	}

	public CaseStatement parseCaseStatement() {
		
		eat(Keywords.CASE);
		CaseStatement cS  = new CaseStatement();
		CompoundStatement block = new CompoundStatement();
		cS.cond = parseSingleExpression();
		eat(Punctuations.COL);
		
		while(!tStream.isEnd()) {
			
			//DOESN'T stop at break.
			//stops at next case statement, or default statement, or closing curly brace
			if(tStream.peek().getValue().equals(Keywords.CASE) || tStream.peek().getValue().equals(Keywords.DEFAULT) || tStream.peek().getValue().equals(Punctuations.CURLY_CLS)) {
				break;
			}
			
			block.add(parseStatement());
			
		}
		
		cS.block = block;
		return cS;
	}
	

	public DefaultStatement parseDefaultStatement() {
		
		eat(Keywords.DEFAULT);
		eat(Punctuations.COL);
		DefaultStatement dS  = new DefaultStatement();
		CompoundStatement block = new CompoundStatement();
		
		while(!tStream.isEnd()) {
			
			//DOESN'T stop at break.
			//stops at closing curly brace
			if(tStream.peek().getValue().equals(Punctuations.CURLY_CLS)) {
				break;
			}
			
			block.add(parseStatement());
			
		}
		
		dS.block = block;
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
		
		List<Modifier> modifiers = parseModifiers();
		Identifier id = parseIdentifier();
		eat(Punctuations.COL);

		if(tStream.peek().getValue().equals(Punctuations.SLASH_BCK)) {
			FunctionDeclaration fD = new FunctionDeclaration();
			fD.modifiers = modifiers;
			fD.name = id;
			fD.signature = parseSignature();
			return fD;
		}
		
		VariableDeclaration sD = new VariableDeclaration();
		sD.modifiers = modifiers;
		sD.name = id;
		sD.type = parseType();
		return sD;
	}

	public Signature parseSignature() {

		Signature sg = new Signature();
		eat(Punctuations.SLASH_BCK);
		sg.params = parseDeclaration();

		if(tStream.peek().getValue().equals(Punctuations.COL)) {
			eat(Punctuations.COL);
			sg.returnType = parseType();
		}

		return sg;
	}


	public Expression parseExpression() {
		
		MultiExpression mE = parseMultiExpression();
		return mE.expressions.size()==1? mE.expressions.get(0) : mE;
	}

	public MultiExpression parseMultiExpression() {

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
		
		return parseAsgnExpression();		
	}

	
	public Expression parseAsgnExpression() { //right assoc
		
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
		
		Collections.reverse(chain); // to traverse chain from right to left, eg: z = y = x = 1  ---->  1, x, y, z
		AssignmentExpression asgn1 = new AssignmentExpression(); //  preamble
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



	public Expression parseCondExpression() { //OrExpression or TernaryExpression

		Expression oE = parseOrExpression();

		if(!tStream.peek().getValue().equals(Punctuations.QUESTION_MARK)) {
			return oE;
		}

		TernaryExpression tE = new TernaryExpression();
		tE.cond = oE;
		eat(Punctuations.QUESTION_MARK);
//		tE.thenExpression = parseOrExpression();
		tE.thenExpression = parseSingleExpression();
		eat(Punctuations.COL);
//		tE.elseExpression = parseOrExpression();
		tE.elseExpression = parseSingleExpression();
		return tE;
		
	}

	
	public OrExpression parseOrExpression() { //left assoc, as most of the others

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
				eat(one.op);
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
				eat(one.op);
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
				eat(one.op);
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

		PostfixExpression exp = parsePrimaryExpression();

		while (!tStream.isEnd()) {
			
			if(tStream.peek().getValue().equals(Punctuations.PAREN_OPN)) {
				exp = parseCalledExpression(exp);
			}else if(tStream.peek().getValue().equals(Punctuations.SQBR_OPN)) {
				exp = parseIndexedExpression(exp);
			}else if(tStream.peek().getValue().equals(Punctuations.DOT)) {
				exp  = parseDotExpression(exp);
			}else if(Operators.isReassignmentOperator(tStream.peek().getValue())) {
				exp = parseReasgnExpression(exp);
			}else {
				break;
			}
			
		}
		
		return exp;
	}


	public CalledExpression parseCalledExpression(PostfixExpression left) {
		
		eat(Punctuations.PAREN_OPN);
		CalledExpression cE = new CalledExpression();
		cE.callable = left;
		
		if(!tStream.peek().getValue().equals(Punctuations.PAREN_CLS)) {
			cE.args = parseMultiExpression();
		}
		
		eat(Punctuations.PAREN_CLS);
		return cE;
	}

	public IndexedExpression parseIndexedExpression(PostfixExpression left) {
		
		eat(Punctuations.SQBR_OPN);
		
		if(tStream.peek().getValue().equals(Punctuations.SQBR_CLS)) {
			tStream.croak("Expected index");
		}
		
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
		dE.right = parseIdentifier();
		return dE;
	}

	public ReassignmentExpression parseReasgnExpression(PostfixExpression left) {
		
		ReassignmentExpression rE = new ReassignmentExpression();
		rE.left = left;
		
		try {
			rE.operator = (Operators)tStream.peek().getValue();
		}catch (ClassCastException e) {
			tStream.croak("Expected postfix operator");
		}
		
		eat(rE.operator);
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
		return parseConstant();

	}


	public BracketedExpression parseBracketedExpression() {
		
		eat(Punctuations.PAREN_OPN);
		Expression expression = parseExpression();
		eat(Punctuations.PAREN_CLS);
		return new BracketedExpression(expression);
	}


	public ObjectExpression parseObjectExpression() {

		if(tStream.peek().getValue().equals(Punctuations.SQBR_OPN)) {
			return parseList();
		}		

		if(tStream.peek().getValue().equals(Punctuations.CURLY_OPN)) {
			return parseDict();
		}

		// get through the list of modifiers then check if class or interface or \ (lambda)
		List<Modifier> modifiers = parseModifiers();

		if(tStream.peek().getValue().equals(Punctuations.SLASH_BCK)) {
			return parseLambdaExpression(modifiers);
		}

		if(tStream.peek().getValue().equals(Keywords.CLASS)) {
			return parseClassExpression(modifiers);
		}

		if(tStream.peek().getValue().equals(Keywords.INTERFACE)) {
			return parseInterfaceExpression(modifiers);
		}

		tStream.croak("Expected object-expression");
		return null;
	}

	public LambdaExpression parseLambdaExpression(List<Modifier> modifiers) {

		LambdaExpression lE = new LambdaExpression();
		lE.modifiers = modifiers;
		lE.signature = parseSignature();
		eat(Operators.ARROW);

		if(tStream.peek().getValue().equals(Punctuations.CURLY_OPN)) {
			lE.block = parseCompStatement();
		}else {
			lE.expression = parseExpression();
		}

		return lE;
	}

	public ClassExpression parseClassExpression(List<Modifier> modifiers) {

		ClassExpression cE = new ClassExpression();
		cE.modifiersList = modifiers;
		eat(Keywords.CLASS);

		//listensto, implements, extends could come in any order
		while( !tStream.isEnd() && !tStream.peek().getValue().equals(Punctuations.CURLY_OPN)) {  

			if(tStream.peek().getValue().equals(Keywords.LISTENSTO)) {
				cE.observables = parseIdList();
			}

			if(tStream.peek().getValue().equals(Keywords.IMPLEMENTS)) {
				cE.interfaces = parseIdList();
			}

			if(tStream.peek().getValue().equals(Keywords.EXTENDS)) {
				cE.superclass = parseIdentifier();
			}
		}

		eat(Punctuations.CURLY_OPN);

		while(!tStream.isEnd()) {

			if(tStream.peek().getValue().equals(Punctuations.CURLY_CLS)) {
				break;
			}

			//TODO cast type and check validity
			cE.addStatement(parseStatement());
		}

		eat(Punctuations.CURLY_CLS);
		return cE;
	}



	public InterfaceExpression parseInterfaceExpression(List<Modifier> modifiers) {
		InterfaceExpression iE = new InterfaceExpression();
		iE.modifiers = modifiers;
		eat(Keywords.INTERFACE);

		while( !tStream.isEnd() && !tStream.peek().getValue().equals(Punctuations.CURLY_OPN)) {  

			if(tStream.peek().getValue().equals(Keywords.EXTENDS)) {				
				iE.superInterfaces = parseIdList();
			}

		}

		eat(Punctuations.CURLY_OPN);

		while (!tStream.isEnd()) {

			if(tStream.peek().getValue().equals(Punctuations.CURLY_CLS)) {
				break;
			}

			iE.addDeclaration(parseDeclStatement().declaration);

		}

		eat(Punctuations.CURLY_CLS);

		return iE;

	}


	public List<Identifier> parseIdList(){ //comma separated

		ArrayList<Identifier> ids = new ArrayList<Identifier>();
		ids.add(parseIdentifier());

		while (!tStream.isEnd()) {

			if(tStream.peek().getValue().equals(Punctuations.COMMA)) {
				eat(Punctuations.COMMA);
				ids.add(parseIdentifier());
			}else {
				break;
			}

		}

		return ids;
	}

	public ObjectExpression parseList() {

		eat(Punctuations.SQBR_OPN);
		Expression exp = parseExpression();

		if(tStream.peek().getValue().equals(Keywords.FOR)) {
			return parseListComprehension(exp);
		}

		eat(Punctuations.COMMA);
		MultiExpression mE = new MultiExpression();
		mE.expressions.add(0, exp);
		ListExpression lE = new ListExpression();
		lE.elements = mE;
		eat(Punctuations.SQBR_CLS);
		return lE;
	}

	public ListComprehension parseListComprehension(Expression exp) {

		ListComprehension lC = new ListComprehension();
		lC.element = exp;
		eat(Keywords.FOR);
		lC.iterable = parseExpression();

		if(tStream.peek().getValue().equals(Keywords.WHERE)) {
			eat(Keywords.WHERE);
			lC.where = parseExpression();
		}

		eat(Punctuations.SQBR_CLS);
		return lC;
	}

	public ObjectExpression parseDict() {

		eat(Punctuations.CURLY_OPN);
		DictExpression dE = new DictExpression();
		Expression exp;
		boolean mayBeComprehension = true; 

		while (!tStream.isEnd()) {

			exp = parseExpression();

			if(tStream.peek().getValue().equals(Punctuations.COMMA)) {
				eat(Punctuations.COMMA);

				try {
					dE.addDestruct((DestructuringExpression)exp);
				}catch (ClassCastException e) {
					tStream.croak("Expected variable unpacking");
				}

			}else if (tStream.peek().getValue().equals(Punctuations.COL)) {
				eat(Punctuations.COL);
				Expression val = parseExpression();

				if(tStream.peek().getValue().equals(Keywords.FOR)) {

					if(mayBeComprehension) {
						return parseDictComprehension(Map.entry(exp, val));
					}

					tStream.croak("Misplaced 'for', not a comprehension");

				}else {
					dE.addEntry(exp, val);
				}

			}else if(tStream.peek().getValue().equals(Punctuations.CURLY_CLS)) {
				eat(Punctuations.CURLY_CLS);
				break;
			}

			mayBeComprehension = false;
		}

		return dE;
	}




	public DictComprehension parseDictComprehension(Entry<Expression, Expression> entry) {

		DictComprehension dC = new DictComprehension();
		dC.key = entry.getKey();
		dC.val = entry.getValue();
		eat(Keywords.FOR);
		dC.iterable = parseExpression();

		if(tStream.peek().getValue().equals(Keywords.WHERE)) {
			eat(Keywords.WHERE);
			dC.where = parseExpression();
		}

		eat(Punctuations.CURLY_CLS);
		return dC;

	}



	public Token parseConstant() { 

		Constant constant;
		try {
			constant = (Constant)tStream.peek();
			tStream.next();
			return constant;
		}catch (ClassCastException e) {
			tStream.croak("Expected constant literal");
			return null;
		}
	}


	public Type parseType() {
		UnionType type = parseUnionType();
		return type;
	}

	public UnionType parseUnionType() {

		UnionType uT = new UnionType();
		uT.addType(parseSingleType());

		while(!tStream.isEnd()) {

			if(tStream.peek().getValue().equals(Operators.SINGLE_OR)) {
				eat(Operators.SINGLE_OR);
				uT.addType(parseSingleType());
			}else {
				break;
			}
		}

		return uT;
	}

	public SingleType parseSingleType(){

		if(tStream.peek().getValue().equals(Punctuations.CURLY_OPN)) {
			return parseDictType();
		}

		OneNameType oT = parseOneNameType();

		if(tStream.peek().getValue().equals(Punctuations.SQBR_OPN)) {
			return parseListType(oT);
		}

		return oT;

	}

	public ListType parseListType(OneNameType oT) {
		ListType lT = new ListType();
		lT.value = oT;
		eat(Punctuations.SQBR_OPN);
		eat(Punctuations.SQBR_CLS);
		return lT;
	}

	public DictType parseDictType() {

		DictType dT = new DictType();
		eat(Punctuations.CURLY_OPN);
		dT.keyType = parseOneNameType();
		eat(Punctuations.COL);
		dT.valType = parseOneNameType();
		eat(Punctuations.CURLY_CLS);
		return dT;
	}

	public OneNameType parseOneNameType() {

		Token token = tStream.peek();

		if(token instanceof Keyword) {
			return parsePrimitiveType();
		}

		if(token instanceof Identifier) {
			return parseIdentifierType();
		}

		tStream.croak("Expected primitive or identifier");
		return null;
	}

	public IdentifierType parseIdentifierType() {
		IdentifierType iD = new IdentifierType((Identifier)tStream.peek());
		tStream.next();
		return iD;
	}

	public PrimitiveType parsePrimitiveType() {
		PrimitiveType pT = new PrimitiveType((Keywords)tStream.peek().getValue());
		tStream.next();
		return pT;
	}



	public Identifier parseIdentifier() {

		Identifier id = null;

		try {
			id = (Identifier)tStream.peek();
			tStream.next();
		}catch (ClassCastException e) {
			tStream.croak("Expected identifier");
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
