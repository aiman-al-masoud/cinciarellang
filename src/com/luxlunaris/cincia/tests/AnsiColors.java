package com.luxlunaris.cincia.tests;

public class AnsiColors {

	public static final String OKGREEN = "\033[92m";
	public static final String WARNING = "\033[93m";
	public static final String FAIL = "\033[91m";
	public static final String ENDC = "\033[0m";

	public static final String wrap(String text, String color) {
		return color+text+ENDC;
	}

	public static final String ok(String text) {
		return wrap(text, OKGREEN);
	}

	public static final String fail(String text) {
		return wrap(text, FAIL);
	}

	public static final String warn(String text) {
		return wrap(text, WARNING);
	}


}
