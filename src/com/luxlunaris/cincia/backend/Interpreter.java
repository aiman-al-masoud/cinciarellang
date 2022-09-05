package com.luxlunaris.cincia.backend;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.Compiler;
import com.luxlunaris.cincia.frontend.ast.declarations.FunctionDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
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
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.DestructuringExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.MinusExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.NegationExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;
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
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Bool;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Float;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Int;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Str;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;
import com.luxlunaris.cincia.frontend.charstream.CharStream;
import com.luxlunaris.cincia.frontend.parser.Parser;
import com.luxlunaris.cincia.frontend.preprocessor.Preprocessor;
import com.luxlunaris.cincia.frontend.tokenstream.TokenStream;



public class Interpreter extends AbstractTraversal<CinciaObject> {

	@FunctionalInterface
	interface Eval{
		CinciaObject eval(Ast ast, Enviro enviro);
	}

	@Override
	public CinciaObject evalInt(Int intex, Enviro enviro) {
		return CinciaObject.create(intex.getValue());
	}

	@Override
	public CinciaObject evalFloat(Float floatex, Enviro enviro) {
		return CinciaObject.create(floatex.getValue());
	}

	@Override
	public CinciaObject evalStr(Str strex, Enviro enviro) {
		return CinciaObject.create(strex.getValue());
	}

	@Override
	public CinciaObject evalBool(Bool boolex, Enviro enviro) {
		return CinciaObject.create(boolex.getValue());
	}

	@Override
	public CinciaObject evalIdentifier(Identifier identex, Enviro enviro) {
		return enviro.get(identex.value);
	}

	@Override
	public CinciaObject evalTernaryExpression(TernaryExpression terex, Enviro enviro) {

		if(eval(terex.cond, enviro).__bool__()) {
			return eval(terex.thenExpression, enviro);
		}else {
			return eval(terex.elseExpression, enviro);
		}

	}

	@Override
	public CinciaObject evalIfStatement(IfStatement ifStatement, Enviro enviro) {

		if(eval(ifStatement.cond, enviro).__bool__()) {
			return eval(ifStatement.thenBlock, enviro);
		}else {
			return eval(ifStatement.elseBlock, enviro);
		}

	}

	@Override
	public CinciaObject evalMatchStatement(MatchStatement ifStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalBreakStatement(BreakStatement breakStatement, Enviro enviro) {
		return null; //useless
	}

	@Override
	public CinciaObject evalContinueStatement(ContinueStatement continueStatement, Enviro enviro) {
		return null; //useless
	}



	// for x, y, i in [[1,2],[3,4],[5,6],[7,8]]{ print(x, y, i);  }
	// for x, y, i, a in [[1,2],[3,4],[5,6],[7,8]]{ print(x, y, i);  } SHOULD BE MARKED AS WRONG
	// for x,y in [[1,2,3],[3,4,5],[5,6,7],[7,8,9]]{ print(x);  } SHOULD BE MARKED AS WRONG
	@Override
	public CinciaObject evalForStatement(ForStatement forStatement, Enviro enviro) {

		CinciaIterable iterable = (CinciaIterable)eval(forStatement.iterable, enviro);		
		List<String> loopVars = forStatement.loopVars.stream().map(v-> ((Identifier)v).value ).collect(Collectors.toList());

		int index = 0;
		for(CinciaObject x : iterable) {

			// 1 set loop vars

			// 1.1 if there are >1 loop vars and x is an iterable, unpack it into the vars
			if(loopVars.size()>1 && x instanceof CinciaIterable) {

				CinciaIterable itx = (CinciaIterable)x;

				if(loopVars.size() < itx.size()) {
					throw new RuntimeException("Too few loop vars!");
				}// TODO: extra var (if present) for index

				if(loopVars.size() > itx.size() + 1) {
					throw new RuntimeException("Too many loop vars!");
				}// TODO: extra var (if present) for index

				for(int i=0; i<itx.size(); i++) {
					enviro.set(loopVars.get(i), itx.get(i));
				}


			}else {
				// 1.2 if x isn't an iterable, or there's just one loop var, don't unpack
				enviro.set(loopVars.get(0), x);
			}


			// 2 if there's an extra loop var, assign it to the index
			try {

				long unpackedElemSize = ((CinciaIterable)iterable.get(0)).size();
				if(loopVars.size() > unpackedElemSize) {
					enviro.set(loopVars.get(loopVars.size()-1), new CinciaInt(index));
				}

			}catch (ClassCastException e) {
				enviro.set(loopVars.get(loopVars.size()-1), new CinciaInt(index));
			}


			// 3 execute block
			eval(forStatement.block, enviro);
			index++; //4 increment iteration index

		}

		return null;
	}

	@Override
	public CinciaObject evalWhileStatement(WhileStatement whileStatement, Enviro enviro) {

		while(eval(whileStatement.cond, enviro).__bool__()) {

			// run one iteration
			CinciaObject o = eval(whileStatement.block, enviro); 

			//check iteration exit value to determine what to do next
			if(o == null) {
				continue;
			}

			if(o.getValue().equals(Keywords.CONTINUE)) {
				continue;
			}

			if(o.getValue().equals(Keywords.BREAK)) {
				break;
			}

			//return statement
			if(o != null) { 
				return o;
			}

		}

		return null;
	}

	// import fib as f from "./docs/examples/fib.ci"
	// import x from "./docs/examples/nested.ci"
	// import x.y from "./docs/examples/nested.ci"
	// import x.y as u from "./docs/examples/nested.ci"
	@Override
	public CinciaObject evalImportStatement(ImportStatement importStatement, Enviro enviro) {

		//1 if fromPath is path to text file, load code into string
		String source = "";		
		try {
			List<String> lines = Files.readAllLines(Paths.get(importStatement.fromPath.value), StandardCharsets.UTF_8);
			source = lines.stream().reduce((l1,l2)->l1+"\n"+l2).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//2 create a new isolated env
		Enviro envCopy = enviro.newChild();

		//3 evaluate the code in the string into the env //TODO: abstract this away in some other class
//		Preprocessor preprocessor = new Preprocessor(source);
//		CharStream charStream = new CharStream(preprocessor.process());
//		TokenStream tokenStream = new TokenStream(charStream);
//		Parser parser = new Parser(tokenStream);
//		List<Statement> statements = parser.parse();
//		statements = statements.stream().map(s->s.simplify()).collect(Collectors.toList());

		List<Ast> statements = new Compiler().compile(source);
		statements.forEach(s -> eval(s, envCopy) );

		//4 put the env in a "module" object
		AbstractCinciaObject module = new AbstractCinciaObject(Type.Module);
		module.enviro = envCopy;

		//5 import the desired pieces of the module into the current env	
		importStatement.imports.forEach(i->{

			CinciaObject desired = eval(i.getKey(), envCopy);
			String alias = i.getValue().value;

			if(alias != Identifier.NULL.value) {
				enviro.set(alias, desired);
			}else if(i.getKey() instanceof DotExpression){
				DotExpression dotExpression = (DotExpression)i.getKey();
				enviro.set(dotExpression.right.value, desired);
			}else if(i.getKey() instanceof Identifier){
				enviro.set(((Identifier)i.getKey()).value, desired);
			}

		});

		return null;

	}

	@Override
	public CinciaObject evalCompoundStatement(CompoundStatement cS, Enviro enviro) {

		for (Statement s : cS.statements) {

			if(s instanceof ReturnStatement) {
				return eval( (ReturnStatement)s , enviro);
			}else if(s instanceof BreakStatement) {
				return new CinciaKeyword(Keywords.BREAK);
			}else if(s instanceof ContinueStatement) {
				return new CinciaKeyword(Keywords.CONTINUE);
			}else {
				eval(s, enviro);
			}

		}

		return null;
	}


	@Override
	public CinciaObject evalTryStatement(TryStatement tryStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalThrowStatement(ThrowStatement throwStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalDefaultStatement(DefaultStatement defaultStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalReturnStatement(ReturnStatement returnStatement, Enviro enviro) {
		return eval(returnStatement.expression, enviro);
	}

	@Override
	public CinciaObject evalCaseStatement(CaseStatement caseStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalMultiExpression(MultiExpression multex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalRangeExpression(RangeExpression rangex, Enviro enviro) {

		CinciaObject from = eval(rangex.from, enviro);
		CinciaObject to = eval(rangex.to, enviro);

		CinciaList cinciaList = new CinciaList(new PrimitiveType(PrimitiveType.INT));

		if(from instanceof CinciaInt && to instanceof CinciaInt) {

			for(int i=(int)from.getValue(); i<=(int)to.getValue(); i++) {
				cinciaList.add(new CinciaInt(i));
			}

		}

		return cinciaList;
	}

	@Override
	public CinciaObject evalFunctionDeclaration(FunctionDeclaration fD, Enviro enviro) {
		enviro.set(fD.name.value, null, fD.signature); //TODO: check if already exists!!
		return null;
	}

	@Override
	public CinciaObject evalVariableDeclaration(VariableDeclaration vD, Enviro enviro) {
		enviro.set(vD.name.value, null, vD.type); //TODO: check if already exists!!
		return null;
	}

	@Override
	public CinciaObject evalMultiDeclaration(MultiDeclaration mD, Enviro enviro) {

		for(Declaration d : mD.declarations) {
			evalDeclaration(d, enviro);
		}

		return null;
	}


	@Override
	public CinciaObject evalMulExpression(MulExpression mulex, Enviro enviro) {

		CinciaObject left = eval(mulex.left, enviro);
		CinciaObject right = eval(mulex.right, enviro);

		if(mulex.op == Operators.ASTERISK) {
			return left.__mul__(right);
		}else if(mulex.op == Operators.DIV) {
			return left.__div__(right);
		}else if(mulex.op == Operators.MOD) {
			return left.__mod__(right);
		}

		throw new RuntimeException("Unknown multiplication operator!");
	}

	@Override
	public CinciaObject evalAddExpression(AddExpression addex, Enviro enviro) {

		CinciaObject left = eval(addex.left, enviro);
		CinciaObject right = eval(addex.right, enviro);

		if(addex.op == Operators.PLUS) {
			return left.__add__(right);
		}else if(addex.op == Operators.MINUS) {
			return left.__sub__(right);
		}

		throw new RuntimeException("Unknown addition operator!");

	}

	@Override
	public CinciaObject evalComparisonExpression(ComparisonExpression compex, Enviro enviro) {

		CinciaObject left = eval(compex.left, enviro);
		CinciaObject right = eval(compex.right, enviro);

		switch (compex.op) {

		case COMPARE:
			return left.__eq__(right);
		case NE:
			return left.__ne__(right);
		case LT:
			return left.__lt__(right);
		case GT:
			return left.__gt__(right);
		case LTE:
			return left.__lte__(right);
		case GTE:
			return left.__gte__(right);
		default:
			throw new RuntimeException("Unknown comparison operator!");
		}

	}

	@Override
	public CinciaObject evalOrExpression(OrExpression orex, Enviro enviro) {
		CinciaObject left = eval(orex.left, enviro);
		CinciaObject right = eval(orex.right, enviro);
		return left.__or__(right);
	}

	@Override
	public CinciaObject evalAndExpression(AndExpression andex, Enviro enviro) {
		CinciaObject left = eval(andex.left, enviro);
		CinciaObject right = eval(andex.right, enviro);
		return left.__and__(right);
	}

	@Override
	public CinciaObject evalAssignmentExpression(AssignmentExpression assex, Enviro enviro) {

		CinciaObject rval =  eval(assex.right, enviro);

		// if l-value is an identifier
		if(assex.left instanceof Identifier) {
			enviro.set(((Identifier)assex.left).value, rval, rval.getType());
		}

		// if l-value is a dot expression
		try {
			DotExpression dotex = (DotExpression)assex.left;
			CinciaObject dottable = eval(dotex.left, enviro);
			dottable.set(dotex.right.value, rval);
		}catch (ClassCastException e) {

		}

		// if l-value is an indexed expresson 
		try {
			IndexedExpression indexex = (IndexedExpression)assex.left;
			CinciaObject indexable = eval(indexex.indexable, enviro);
			CinciaObject index = eval(indexex.index, enviro);

			// if index is an int
			if(index instanceof CinciaInt) {
				indexable.set(((CinciaInt)index).getValue(), rval);
			}

			// if index is a string
			if(index instanceof CinciaString) {
				indexable.set(((CinciaString)index).getValue(), rval);
			}

			// TODO: fancy index


		}catch (ClassCastException e) {

		}



		return rval;
	}

	@Override
	public CinciaObject evalClassExpression(ClassExpression classex, Enviro enviro) {

		CinciaClass c = new CinciaClass();

		for(Declaration dec : classex.declarations) {	
			eval(dec, c.getEnviro());
		}

		for(AssignmentExpression assign : classex.assignments) {
			eval(assign, c.getEnviro());
		}

		return c;
	}

	@Override
	public CinciaObject evalDictExpression(DictExpression dictex, Enviro enviro) {

		CinciaDict d = new CinciaDict(Type.Any, Type.Any);

		dictex.entries.forEach(e->{

			CinciaObject key = eval(e.getKey(), enviro);
			CinciaObject val = eval(e.getValue(), enviro);

			if(key instanceof CinciaString) {
				d.set(((CinciaString)key).getValue(), val);
			}

			if(key instanceof CinciaInt) {
				d.set(((CinciaInt)key).getValue(), val);
			}

		});

		return d;
	}

	@Override
	public CinciaObject evalDictComprehension(DictComprehension dictcompex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalInterfaceExpression(InterfaceExpression interex, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalLambdaExpression(LambdaExpression lambdex, Enviro enviro) {

		// Check if env belongs to class, in that case return a method.
		CinciaClass b = (CinciaClass)enviro.get(CinciaClass.CLASS);
		if(b != null) {
			return new CinciaMethod(lambdex, this::eval);
		}

		// Check if function is pure, in that case return a pure function.
		if(lambdex.modifiers.contains(Modifiers.PURE)) {
			return new PureCinciaFunction(lambdex, this::eval);
		}

		// ... else return a standard top level function
		return new CinciaFunction(lambdex, this::eval);
	}

	@Override
	public CinciaObject evalListComprehension(ListComprehension listcompex, Enviro enviro) {

		PureCinciaFunction map = (PureCinciaFunction) eval(LambdaExpression.fromExpression((Identifier)listcompex.source, listcompex.element, Type.Any), enviro);
		PureCinciaFunction filter = (PureCinciaFunction) eval(LambdaExpression.fromExpression((Identifier)listcompex.source, listcompex.where, new PrimitiveType(PrimitiveType.BOOL)), enviro);
		CinciaIterable iterable = (CinciaIterable)eval(listcompex.iterable, enviro);
		return iterable.filter(filter).map(map);

	}

	@Override
	public CinciaObject evalListExpression(ListExpression listex, Enviro enviro) {

		// TODO: move this c... into evalMultiExpression
		List<Expression> elements = new ArrayList<Expression>();
		if(listex.elements instanceof MultiExpression) {
			elements.addAll(((MultiExpression)listex.elements).expressions);
		}else {
			elements.add(listex.elements);
		}


		List<CinciaObject> objects = elements.stream().map(e->eval(e, enviro)).collect(Collectors.toList());
		CinciaList cL = new CinciaList(Type.Any);

		objects.forEach(o->{

			if(o instanceof DestructuredList) {
				((DestructuredList) o).forEach(e-> cL.add(e));
			}else {
				cL.add(o);
			}

		});

		return cL;
	}

	@Override
	public CinciaObject evalCalledExpression(CalledExpression callex, Enviro enviro) {


		// TODO: move this c... into evalMultiExpression 
		// get arguments 
		List<CinciaObject> args = new ArrayList<CinciaObject>();

		if(callex.args!=null) {
			try {
				MultiExpression mE = (MultiExpression)callex.args;
				args.addAll(mE.expressions.stream().map(e-> eval(e, enviro)).collect(Collectors.toList()) );
			}catch (ClassCastException e) {
				args.add(eval(callex.args, enviro));
			}
		}

		// get called expression
		CinciaObject f = eval(callex.callable, enviro);


		//TODO: problem: recursive functions in nested imported modules can't resolve their own name!!!!
		// when a function refers to itself, the code block where it does so
		// is evaluated in the current environment, and if the name is not 
		// on the top level, the function name isn't resolved!
		// System.out.println("callable: "+f);


		// if class, call constructor and return reference to new object
		try {
			CinciaClass c = (CinciaClass)f;
			return c.constructor(args);
		}catch (ClassCastException e) {

		}

		// if method, call on parent object's ORIGINAL env
		try {
			CinciaMethod cm = (CinciaMethod)f;
			return cm.run(args);
		}catch (ClassCastException e) {

		}

		// if pure function, run with args as ONLY input, don't let it even read the global env.
		try {
			PureCinciaFunction cm = (PureCinciaFunction)f;
			return cm.run(args);
		}catch (ClassCastException e) {

		}

		// else it's a regular top level function, call on COPY of whatever environment was passed in		
		try {
			CinciaFunction l = (CinciaFunction)f;
			return l.run(args, enviro.newChild());
		}catch (ClassCastException e) {

		}


		throw new RuntimeException("Unsupported callable type!");
	}

	@Override
	public CinciaObject evalDotExpression(DotExpression dotex, Enviro enviro) {
		CinciaObject o = eval(dotex.left, enviro);
		return o.get(dotex.right.value);		
	}

	@Override
	public CinciaObject evalIndexedExpression(IndexedExpression indexex, Enviro enviro) {

		CinciaObject o = eval(indexex.indexable, enviro);
		CinciaObject index = eval(indexex.index , enviro);


		if( index instanceof CinciaString ) {
			return o.get((String)index.getValue());
		}


		if(index instanceof CinciaInt) {
			return o.get(((CinciaInt)index).getValue());
		}

		// If index is an iterable treat as fancy index
		// TEST
		//[1,2,3,4][0 to 2] // [1, 2, 3]
		//[1,2,3][[0,1]] // [1, 2]
		if(index instanceof CinciaIterable) {

			CinciaList l = new CinciaList(Type.Any);

			for(CinciaObject i : ((CinciaIterable)index)) {

				try {
					l.add(o.get((int)i.getValue()));
				}catch (ClassCastException e) {

				}

				try {
					l.add(o.get((String)i.getValue()));
				}catch (ClassCastException e) {

				}

			}

			return l;
		}


		throw new RuntimeException("Unsupported index type!");
	}

	@Override
	public CinciaObject evalReassignmentExpression(ReassignmentExpression reassex, Enviro enviro) {
		// TODO Auto-generated method stub
		//		CinciaObject o = eval(reassex.left, enviro);

		return null;
	}

	@Override
	public CinciaObject evalBracketedExpression(BracketedExpression brackex, Enviro enviro) {
		return eval(brackex.expression, enviro);
	}

	@Override
	public CinciaObject evalDestructuringExpression(DestructuringExpression destex, Enviro enviro) {

		// TODO: if dict evaluate to list of lists 
		return new DestructuredList((CinciaList)eval(destex.arg, enviro));
	}

	@Override
	public CinciaObject evalMinusExpression(MinusExpression minex, Enviro enviro) {
		return eval(minex.arg, enviro).__neg__();
	}

	@Override
	public CinciaObject evalNegationExpression(NegationExpression negex, Enviro enviro) {
		return eval(negex.arg, enviro).__not__();
	}


	// TEST
	// double = \x -> 2*x
	// 1 | double | double | double 
	// f = \x-> ( x | double | double | double )
	// 3 | f
	// x | \x->4*5 | \x->x<1
	@Override
	public CinciaObject evalPipeExpression(PipeExpression pipex, Enviro enviro) {

		Enviro envCopy =  enviro.newChild();
		CinciaObject arg = eval(pipex.expressions.get(0), envCopy);

		//TODO: can this be parallelized like in bash?
		for(int i=1; i<pipex.expressions.size(); i++) {
			CinciaFunction f = (CinciaFunction)eval(pipex.expressions.get(i), envCopy); 
			arg = f.run(Arrays.asList(arg), envCopy);
		}

		return arg;
	}


}
