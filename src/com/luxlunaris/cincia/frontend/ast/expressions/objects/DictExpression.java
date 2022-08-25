package com.luxlunaris.cincia.frontend.ast.expressions.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.luxlunaris.cincia.frontend.ast.expressions.unary.DestructuringExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.ObjectExpression;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

public class DictExpression implements ObjectExpression{
	
	//order doesn't matter
	public List<Entry<Expression, Expression>> entries;
	public List<DestructuringExpression> destructs;
	
	
	public DictExpression() {
		entries = new ArrayList<Map.Entry<Expression, Expression>>();
		destructs = new ArrayList<DestructuringExpression>();
	}
	
	public void addEntry(Expression key, Expression val) {
		entries.add(Map.entry(key, val));
	}
	
	public void addDestruct(DestructuringExpression exp) {
		destructs.add(exp);
	}

	@Override
	public Expression simplify() {
		
		this.entries = entries.stream().map(e->Map.entry(e.getKey().simplify(), e.getValue().simplify())).collect(Collectors.toList());
		return this;
	}
	
	@Override
	public String toString() {

		Optional<String> des = destructs.stream().map(e->"*("+e.arg+")").reduce( (e1,e2)-> {return e1+", "+e2;});
//		Optional<String> pairs=  entries.stream().map(e->e.getKey().simplify()+" : "+e.getValue().simplify()).reduce( (e1,e2)-> {return e1+", "+e2;});
		Optional<String> pairs=  entries.stream().map(e->e.getKey()+" : "+e.getValue()).reduce( (e1,e2)-> {return e1+", "+e2;});

		return "{" + ( pairs.isPresent()? pairs.get() : "") + ( des.isPresent()? ", "+des.get() : "") +"}";
	}
	
	
}
