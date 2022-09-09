package com.luxlunaris.cincia.tests.parser.classes;

public class ImportTester extends AbstractTester{

	public ImportTester() {
		
		add("import capra.c as c from \"./files/capre\";", "");
		add("import capra as c from \"./files/capre\";", "");
		add("import capra as c, muflone.lana as m from \"./files/capre\";", "");
	}

}
