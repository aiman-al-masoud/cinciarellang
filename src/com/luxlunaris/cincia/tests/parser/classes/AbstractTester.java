package com.luxlunaris.cincia.tests.parser.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AbstractTester {

	public final List<Entry<String, String>> tests;
	
	public AbstractTester() {
		tests = new ArrayList<Map.Entry<String,String>>();
	}
	
	public void add(String s1, String s2) {
		tests.add(Map.entry(s1, s2));
	}	

}
