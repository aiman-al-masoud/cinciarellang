package com.luxlunaris.cincia.frontend.ast.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.luxlunaris.cincia.frontend.ast.expressions.postfix.DotExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PostfixExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.StrToken;

/**
 * 
 * import foo.bar as bar, blurf.foo as f from "path/to/file"
 *
 */
public class ImportStatement implements Statement{
	
	public List<Entry<PostfixExpression, Identifier>> imports;
	public StrToken fromPath;
	
	public ImportStatement() {
		imports = new ArrayList<Map.Entry<PostfixExpression,Identifier>>();
	}
	
	public void addImport(PostfixExpression imported, Identifier alias) {
		imports.add(Map.entry(imported, alias)); //alias could be null
	}
	
	@Override
	public Statement simplify() {
		return this;
	}
	
	@Override
	public String toString() {
		return "import "+imports+" from "+fromPath;
	}
	
}
