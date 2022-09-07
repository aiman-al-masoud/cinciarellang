package com.luxlunaris.cincia.ide;

public class Indenter {

	protected String source;

	public Indenter(String source) {
		this.source = source;
	}

	public String getIndented() {

		String result = "";
		String indentation = "";

		for(String line : source.split("\n")) {
			result+=indentation+line.trim()+"\n";

			if(line.contains("{")) {
				indentation+="\t";
			}else {
				indentation = indentation.length()>0? indentation.charAt(0)+"" : indentation;
			}

		}

		return result;
	}

}
