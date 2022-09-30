package com.luxlunaris.cincia.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListDir {
	
	/**
	 * Recursively list (absolute paths of) files in dir.
	 * @param dirpath
	 * @return
	 */
	public static List<String> listDir(String dirpath){

		var  f=  new File(dirpath);

		if(!f.isDirectory()) {
			return Arrays.asList(f.getAbsolutePath());
		}

		var files = new ArrayList<String>();
		var newFiles = Arrays.asList(f.listFiles()).stream().map(fi->fi.getAbsolutePath()).collect(Collectors.toList());		

		for (String nf : newFiles) {	
			files.addAll(listDir(nf));
		}

		return files;

	}

}
