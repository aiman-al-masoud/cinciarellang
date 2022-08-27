package com.luxlunaris.cincia.frontend.ast.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.luxlunaris.cincia.frontend.ast.expressions.postfix.DotExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Str;

/**
 * 
 * import foo.bar as bar, blurf.foo as f from "path/to/file"
 *
 */
public class ImportStatement implements Statement{
	
	public List<Entry<DotExpression, Identifier>> imports;
	public Str fromPath;
	
	public ImportStatement() {
		imports = new ArrayList<Map.Entry<DotExpression,Identifier>>();
	}
	
	public void addImport(DotExpression imported, Identifier alias) {
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
