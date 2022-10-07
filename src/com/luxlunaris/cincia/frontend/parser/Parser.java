package com.luxlunaris.cincia.frontend.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.luxlunaris.cincia.frontend.ast.declarations.FunctionDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.SingleDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.IfExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.MatchExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.PipeExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.RangeExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.TernaryExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AddExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AndExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AssignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.ComparisonExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.MulExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.OrExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.forexp.ForExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.forexp.Generator;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ClassExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.DictExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ListExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.CalledExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.DotExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.IndexedExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.ReassignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.primary.BracketedExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.DictType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.ListType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.OneNameType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.expressions.type.SingleType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.UnionType;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.DestructuringExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.MinusExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.NegationExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Constant;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
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
import com.luxlunaris.cincia.frontend.ast.statements.iteration.WhileStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.BreakStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.ContinueStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.ReturnStatement;
import com.luxlunaris.cincia.frontend.ast.statements.labelled.CaseStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Str;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keyword;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;
import com.luxlunaris.cincia.frontend.ast.tokens.punctuation.Punctuations;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;



public class Parser {

	protected TokenStream tStream;

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
		
		if(tStream.peek().getValue().equals(Punctuations.CURLY_OPN)) {
			res = parseCompStatement();
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
		}else if(tStream.peek().getValue().equals( Keywords.IMPORT )) {
			res = parseImportStatement();
		}else if(tStream.peek().getValue().equals( Keywords.DEC )) {
			res = parseDeclStatement();
		}else {
			res = parseExpressionStatement();
		}


		eat(Punctuations.STM_SEP);
		return res;
	}


	private ExpressionStatement parseExpressionStatement() {

		ExpressionStatement eS = new ExpressionStatement(parseExpression());
		return eS;
	}

	private DeclarationStatement parseDeclStatement() {

		eat(Keywords.DEC);
		DeclarationStatement dS = new DeclarationStatement(parseDeclaration(true));
		return dS;
	}


	private IfExpression parseIfStatement() {

		eat(Keywords.IF);
		IfExpression ifS = new IfExpression();
		ifS.cond =  parseSingleExpression();
		ifS.thenBlock =  parseCompStatement();

		if(tStream.peek().getValue().equals(Keywords.ELSE)) {
			eat(Keywords.ELSE);
			ifS.elseBlock = parseCompStatement();
		}

		return ifS;
	}


	private CompoundStatement parseCompStatement() {

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


	private ForExpression parseForExpression() {

		eat(Keywords.FOR);
		ForExpression fS = new ForExpression();

		while(!tStream.isEnd()) {

			if (tStream.peek() instanceof Identifier ) {
				fS.generators.add(  parseGenerator()  );
				continue;
			}

			if(tStream.peek().getValue().equals(Punctuations.COMMA)) { //TODO comma is really useless here 
				eat(Punctuations.COMMA);
				continue;
			}

			// not identifier nor comma
			break;
		}


		// if curly open eat block 
		if(tStream.peek().getValue().equals(Punctuations.CURLY_OPN)) {			
			fS.block = parseCompStatement();
		}

		// if arrow parse "yield" part for for expression
		if(tStream.peek().getValue().equals(Operators.ARROW)) {
			eat(Operators.ARROW);
			fS.yield = parseSingleExpression();
		}


		return fS;
	}

	private Generator parseGenerator(){

		Generator g = new Generator();

		while(!tStream.isEnd()) {

			if (tStream.peek() instanceof Identifier ) {
				g.loopVars.add( parseIdentifier() );
				continue;
			}

			if(tStream.peek().getValue().equals(Punctuations.COMMA)) {  
				eat(Punctuations.COMMA);
				continue;
			}

			// not identifier nor comma
			break;
		}

		eat(Keywords.IN); 	
		g.iterable = parseSingleExpression();


		// parse filter
		if (tStream.peek().getValue().equals(Keywords.IF)) {
			eat(Keywords.IF);
			g.filter = parseSingleExpression();

		}


		return g;
	}


	private WhileStatement parseWhileStatement() {

		eat(Keywords.WHILE);
		WhileStatement wS = new WhileStatement();
		wS.cond = parseSingleExpression();
		wS.block = parseCompStatement();
		return wS;
	}

	private TryStatement parseTryStatement() {

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

	private CatchClause parseCatchClause() {

		eat(Keywords.CATCH);
		CatchClause cc = new CatchClause();
		cc.throwable = parseSingleDeclaration(true);
		cc.block = parseCompStatement();
		return cc;
	}

	private ThrowStatement parseThrowStatement() {

		eat(Keywords.THROW);
		ThrowStatement tS = new ThrowStatement(parseSingleExpression());		
		return tS;
	}

	private ReturnStatement parseReturnStatement() {

		eat(Keywords.RETURN);
		ReturnStatement rS  = new ReturnStatement();

		if(tStream.peek().getValue().equals(Punctuations.STM_SEP)) {
			eat(Punctuations.STM_SEP);
			return rS;
		}

		rS.expression = parseExpression();
		return rS;

	}

	private ContinueStatement parseContinueStatement() {

		eat(Keywords.CONTINUE);
		return new ContinueStatement();
	}

	private BreakStatement parseBreakStatement() {

		eat(Keywords.BREAK);
		return new BreakStatement();
	}


	private MatchExpression parseMatchExpression() {

		eat(Keywords.MATCH);
		MatchExpression mS = new MatchExpression();
		mS.cond = parseSingleExpression();
		eat(Punctuations.CURLY_OPN);

		while(!tStream.isEnd()) {

			if(tStream.peek() instanceof Identifier ||  tStream.peek() instanceof Constant) {
				mS.add(parseCaseStatement());
			}else {
				break;
			}

		}

		eat(Punctuations.CURLY_CLS);
		return mS;
	}

	private CaseStatement parseCaseStatement() {

		CaseStatement cS  = new CaseStatement();
		cS.cond = parseSingleExpression();
		eat(Operators.ARROW);

		if(tStream.peek().getValue().equals(Punctuations.CURLY_OPN)) {
			cS.block = parseCompStatement();
			eat(Punctuations.STM_SEP);
		}else {
			cS.expression = parseSingleExpression();
			eat(Punctuations.STM_SEP);
		}

		return cS;
	}

	private ImportStatement parseImportStatement() {

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

			Entry<PostfixExpression, Identifier> imported = parseImported();
			iS.addImport(imported.getKey(), imported.getValue());

		}

		eat(Keywords.FROM);

		try {
			iS.fromPath = (Str)tStream.peek();
			tStream.next();

		}catch (ClassCastException e) {
			tStream.croak("Expected import path (string constant)");
		} 

		return iS;
	}


	private Entry<PostfixExpression, Identifier> parseImported(){

		PostfixExpression  dE  = parsePostfixExpression();

		Identifier alias = Identifier.NULL; // can be null

		if(tStream.peek().getValue().equals(Keywords.AS)) {
			eat(Keywords.AS);
			alias = parseIdentifier();
		}

		return Map.entry(dE, alias);
	}

	private List<Modifiers> parseModifiers(){

		ArrayList<Modifiers> res = new ArrayList<Modifiers>();

		while (!tStream.isEnd()){

			try {
				res.add(((Modifier)tStream.peek()).value);
				tStream.next();
			}catch (ClassCastException e) {
				break;
			}
		}

		return res;
	}

	private Declaration parseDeclaration(boolean requireType) {

		MultiDeclaration mD = parseMultiDeclaration(requireType);
		return mD.declarations.size()==1? mD.declarations.get(0) : mD;
	}


	private MultiDeclaration parseMultiDeclaration(boolean requireType) {

		MultiDeclaration mD = new MultiDeclaration();
		mD.addDeclaration(parseSingleDeclaration(requireType));

		while(!tStream.isEnd()) {	

			if(tStream.peek().getValue().equals(Punctuations.COMMA)) {
				eat(Punctuations.COMMA);
				mD.addDeclaration(parseSingleDeclaration(requireType));
				continue;
			}

			break;
		}

		return mD;
	}

	private SingleDeclaration parseSingleDeclaration(boolean requireType) {

		List<Modifiers> modifiers = new ArrayList<Modifiers>();

		if(tStream.peek() instanceof Modifier) {
			modifiers = parseModifiers();
		}

		Identifier id = parseIdentifier();

		// if 'type is not required'
		// unspecified type declarations may make sense in function definitions/declarations where you may have parameter type inference.
		if( !requireType && !tStream.peek().getValue().equals(Punctuations.COL)) {
			VariableDeclaration vD = new VariableDeclaration();
			vD.name = id;
			vD.modifiers = modifiers;
			return vD; //unspecified type
		}


		// with type
		eat(Punctuations.COL);

		// if func
		if(tStream.peek().getValue().equals(Punctuations.SLASH_BCK)) {
			FunctionDeclaration fD = new FunctionDeclaration();
			fD.modifiers = modifiers;
			fD.name = id;
			fD.signature = parseSignature();
			return fD;
		}

		// if variable
		VariableDeclaration sD = new VariableDeclaration();
		sD.modifiers = modifiers;
		sD.name = id;
		sD.type = parseType();
		return sD;
	}

	private Signature parseSignature() {

		Signature sg = new Signature();
		eat(Punctuations.SLASH_BCK);

		// function may not take any parameters
		if(tStream.peek() instanceof Identifier || tStream.peek() instanceof Modifier) {
			sg.params = parseDeclaration(false);
		}

		if(tStream.peek().getValue().equals(Punctuations.COL)) {
			eat(Punctuations.COL);
			sg.returnType = parseType();
		}

		return sg;
	}


	private Expression parseExpression() {

		MultiExpression mE = parseMultiExpression();
		return mE.expressions.size()==1? mE.expressions.get(0) : mE;
	}

	private MultiExpression parseMultiExpression() {

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


	private Expression parseSingleExpression() {



		return parseAsgnExpression();		
	}


	private List<Modifiers> parseAssignmentModifiers(){
		
		try {

			if(Modifiers.isAssignmentModifier(((Modifier)tStream.peek()).value)) {
				List<Modifiers> modifiers = parseModifiers();
				return modifiers;
			}

		} catch (ClassCastException e) {

		}

		return Arrays.asList();

	}


	private Expression parseAsgnExpression() { //right assoc
		
		
		List<Modifiers> modifiers = parseAssignmentModifiers();

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
				//TODO: fix this
				//				asgn1.left = (LeftValue) chain.get(i);
				asgn1.left = chain.get(i);
				AssignmentExpression asgn2 = new AssignmentExpression();
				asgn2.right = asgn1;
				asgn1 = asgn2;
			}catch (ClassCastException e) {
				tStream.croak("Expected left-value, got: '"+chain.get(i)+"'");
			}

		}


		asgn1.comment = tStream.getCurrentComment();
		asgn1.modifiers = modifiers;
		return asgn1;

	}



	private Expression parseCondExpression() { //OrExpression or TernaryExpression
		
		if(tStream.peek().getValue().equals(Keywords.IF)) {
			return parseIfStatement();
		}


		if( tStream.peek().getValue().equals(Keywords.MATCH)) {
			return parseMatchExpression();
		}

		if(tStream.peek().getValue().equals(Keywords.FOR)) {
			return parseForExpression();
		}


		Expression oE = parseOrExpression();


		if(tStream.peek().getValue().equals(Punctuations.QUESTION_MARK)) {
			TernaryExpression tE = new TernaryExpression();
			tE.cond = oE;
			eat(Punctuations.QUESTION_MARK);
			tE.thenExpression = parseSingleExpression();
			eat(Punctuations.COL);
			tE.elseExpression = parseSingleExpression();
			return tE;
		}


		if(tStream.peek().getValue().equals(Keywords.TO)) {
			RangeExpression rE = new RangeExpression();
			rE.from = oE;
			eat(Keywords.TO);
			rE.to = parseSingleExpression();
			return rE;
		}


		//pipe
		if(tStream.peek().getValue().equals(Operators.SINGLE_OR)) {
			return parsePipeExpression(oE);
		}


		return oE;
	}

	private PipeExpression parsePipeExpression(Expression first) {

		eat(Operators.SINGLE_OR);
		PipeExpression pipe = new PipeExpression();
		pipe.add(first);
		pipe.add(parseOrExpression());

		while(!tStream.isEnd()) {

			if(!tStream.peek().getValue().equals(Operators.SINGLE_OR)) {
				break;
			}

			eat(Operators.SINGLE_OR);
			pipe.add(parseOrExpression());
		}

		return pipe;
	}


	private OrExpression parseOrExpression() { //left assoc, as most of the others

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

	private AndExpression parseAndExpression() {

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

	private ComparisonExpression parseComparisonExpression() {

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

	private AddExpression parseAddExpression() {

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

	private MulExpression parseMulExpression() {

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

	private UnaryExpression parseUnaryExpression() {

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

	private MinusExpression parseMinusExpression() {

		eat(Operators.MINUS);
		return new MinusExpression(parseUnaryExpression());
	}

	private NegationExpression parseNegationExpression() {

		eat(Operators.NOT);
		return new NegationExpression(parseUnaryExpression());

	}
	private DestructuringExpression parseDestrExpression() {

		eat(Operators.ASTERISK);
		return new DestructuringExpression(parseUnaryExpression());
	}


	private PostfixExpression parsePostfixExpression() {

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


	private CalledExpression parseCalledExpression(PostfixExpression left) {

		eat(Punctuations.PAREN_OPN);
		CalledExpression cE = new CalledExpression();
		cE.callable = left;

		if(!tStream.peek().getValue().equals(Punctuations.PAREN_CLS)) {
			cE.args = parseMultiExpression();
		}

		eat(Punctuations.PAREN_CLS);
		return cE;
	}

	private IndexedExpression parseIndexedExpression(PostfixExpression left) {

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

	private DotExpression parseDotExpression(PostfixExpression left) {

		eat(Punctuations.DOT);
		DotExpression dE = new DotExpression();
		dE.left = left;
		dE.right = parseIdentifier();
		return dE;
	}

	private ReassignmentExpression parseReasgnExpression(PostfixExpression left) {

		ReassignmentExpression rE = new ReassignmentExpression();
		rE.left = left;

		try {
			rE.op = (Operators)tStream.peek().getValue();
		}catch (ClassCastException e) {
			tStream.croak("Expected postfix operator!");
		}

		eat(rE.op);

		if(Arrays.asList(Operators.PLUSPLUS, Operators.MINUSMINUS).contains(rE.op)) {
			return rE;
		}

		rE.right =  parseExpression();
		return rE;
	}

	private PrimaryExpression parsePrimaryExpression() {

		// TODO: change EBNF, objects expressions are now primary expressions (does that make sense?)
		// if it starts with modifer, or 'class' or 'interface' or '{' or '[' it's an object
		//		if(tStream.peek().getValue().equals(Punctuations.CURLY_OPN) || tStream.peek().getValue().equals(Punctuations.SQBR_OPN) || tStream.peek() instanceof Modifier || tStream.peek().getValue().equals(Keywords.CLASS)|| tStream.peek().getValue().equals(Keywords.INTERFACE) || tStream.peek().getValue().equals(Punctuations.SLASH_BCK)) {

		if(tStream.peek().getValue().equals(Punctuations.CURLY_OPN) || tStream.peek().getValue().equals(Punctuations.SQBR_OPN) || tStream.peek() instanceof Modifier || tStream.peek().getValue().equals(Keywords.CLASS) || tStream.peek().getValue().equals(Punctuations.SLASH_BCK)) {

			return parseObjectExpression();
		}

		// bracketed expression
		if(tStream.peek().getValue().equals(Punctuations.PAREN_OPN)) {
			return parseBracketedExpression();
		}

		// identifiers
		if(tStream.peek() instanceof Identifier) {
			return parseIdentifier();
		}

		// TODO: ????????????????????
		if(tStream.peek() instanceof Keyword) {
			Keyword k = (Keyword)tStream.peek();
			tStream.next();
			return k;
		}

		// constant values
		return parseConstant();

	}


	private BracketedExpression parseBracketedExpression() {

		eat(Punctuations.PAREN_OPN);
		Expression expression = parseExpression();
		eat(Punctuations.PAREN_CLS);
		return new BracketedExpression(expression);
	}


	private ObjectExpression parseObjectExpression() {

		if(tStream.peek().getValue().equals(Punctuations.SQBR_OPN)) {
			return parseList();
		}		

		if(tStream.peek().getValue().equals(Punctuations.CURLY_OPN)) {
			return parseDict();
		}

		// get through the list of modifiers then check if class or interface or \ (lambda)
		List<Modifiers> modifiers = parseModifiers();

		if(tStream.peek().getValue().equals(Punctuations.SLASH_BCK)) {
			return parseLambdaExpression(modifiers);
		}

		if(tStream.peek().getValue().equals(Keywords.CLASS)) {

			return parseClassExpression(modifiers);
		}

		tStream.croak("Expected object-expression");
		return null;
	}

	private LambdaExpression parseLambdaExpression(List<Modifiers> modifiers) {


		LambdaExpression lE = new LambdaExpression();
		lE.modifiers = modifiers;
		lE.signature = parseSignature();

		eat(Operators.ARROW);

		if(tStream.peek().getValue().equals(Punctuations.CURLY_OPN)) {

			lE.block = parseCompStatement();
		}else {
			lE.expression = parseSingleExpression(); // parse single only, or else multiple callbacks are read as one single argument
		}

		return lE;
	}

	private ClassExpression parseClassExpression(List<Modifiers> modifiers) {

		ClassExpression cE = new ClassExpression();
		cE.modifiersList = modifiers;
		eat(Keywords.CLASS);

		eat(Punctuations.CURLY_OPN);

		while(!tStream.isEnd()) {

			if(tStream.peek().getValue().equals(Punctuations.CURLY_CLS)) {
				break;
			}

			Statement s = parseStatement();

			try {
				ExpressionStatement ex = (ExpressionStatement)s;
				cE.addAssignment((AssignmentExpression)ex.expression);

			}catch (ClassCastException e) {
				DeclarationStatement dec = (DeclarationStatement)s;
				cE.addDeclaration(dec.declaration);				

			}

		}

		eat(Punctuations.CURLY_CLS);
		return cE;
	}

	private List<Identifier> parseIdList(){ //comma separated

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

	private ObjectExpression parseList() {

		eat(Punctuations.SQBR_OPN);
		ListExpression lE = new ListExpression();

		// empty list
		if(tStream.peek().getValue().equals(Punctuations.SQBR_CLS)) {
			eat(Punctuations.SQBR_CLS);
			lE.elements = new MultiExpression();
			return lE;
		}

		// first element
		Expression exp = parseSingleExpression();


		// one element list
		if(tStream.peek().getValue().equals(Punctuations.SQBR_CLS)) {
			eat(Punctuations.SQBR_CLS);
			lE.elements = exp;
			return lE;
		}

		eat(Punctuations.COMMA);
		MultiExpression mE = parseMultiExpression();
		mE.expressions.add(0, exp);
		lE.elements = mE;
		eat(Punctuations.SQBR_CLS);
		return lE;

	}



	private ObjectExpression parseDict() {

		eat(Punctuations.CURLY_OPN);
		DictExpression dE = new DictExpression();
		Expression key;
		Expression val;
		key = val = null;

		while(!tStream.isEnd()) {

			if(tStream.peek().getValue().equals(Punctuations.CURLY_CLS)) {
				eat(Punctuations.CURLY_CLS);

				if(key!=null  &&  val != null) {
					dE.addEntry(key, val);
					key = val = null;
				}

				break;
			}

			if(tStream.peek().getValue().equals(Punctuations.COMMA)) {
				eat(Punctuations.COMMA);

				if(key!=null  &&  val != null) {
					dE.addEntry(key, val);
					key = val = null;
				}

				continue;
			}

			if(tStream.peek().getValue().equals(Operators.ASTERISK)) {
				dE.addDestruct(parseDestrExpression());
				continue;
			}

			if(tStream.peek().getValue().equals(Punctuations.COL)) {
				eat(Punctuations.COL);
				val = parseSingleExpression();
				continue;
			}

			key = parseSingleExpression();			

		}

		return dE;
	}




	private Token parseConstant() { 

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


	private Type parseType() {
		UnionType type = parseUnionType();
		return type;
	}

	private UnionType parseUnionType() {

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

	private SingleType parseSingleType(){

		if(tStream.peek().getValue().equals(Punctuations.CURLY_OPN)) {
			return parseDictType();
		}

		OneNameType oT = parseOneNameType();

		if(tStream.peek().getValue().equals(Punctuations.SQBR_OPN)) {
			return parseListType(oT);
		}

		return oT;

	}

	private ListType parseListType(OneNameType oT) {

		ListType lT = new ListType();
		lT.value = oT;
		eat(Punctuations.SQBR_OPN);
		eat(Punctuations.SQBR_CLS); // TODO: keep on doing this in a loop for x[][][]...
		return lT;
	}

	private DictType parseDictType() {

		DictType dT = new DictType();
		eat(Punctuations.CURLY_OPN);
		dT.keyType = parseOneNameType();
		eat(Punctuations.COL);
		dT.valType = parseOneNameType();
		eat(Punctuations.CURLY_CLS);
		return dT;
	}

	private OneNameType parseOneNameType() {

		if(tStream.peek() instanceof Keyword) {
			return parsePrimitiveType();
		}

		if(tStream.peek() instanceof Identifier) {
			return parseIdentifierType();
		}

		tStream.croak("Expected primitive or identifier");
		return null;
	}

	private IdentifierType parseIdentifierType() {

		IdentifierType iD = new IdentifierType(((Identifier)tStream.peek()).value);
		tStream.next();
		return iD;
	}

	private PrimitiveType parsePrimitiveType() {

		PrimitiveType pT = new PrimitiveType((Keywords)tStream.peek().getValue());
		tStream.next();
		return pT;
	}


	private Identifier parseIdentifier() {

		Identifier id = null;

		try {
			id = (Identifier)tStream.peek();
			tStream.next();
		}catch (ClassCastException e) {
			tStream.croak("Expected identifier");
		}

		return id;
	}


	private void eat(Object value) {

		if (!tStream.peek().getValue().equals(value)) {
			tStream.croak("Expected "+value);
		}

		tStream.next();
	}



}
