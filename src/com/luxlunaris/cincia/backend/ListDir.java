package com.luxlunaris.cincia.backend;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListDir {
	
	public static List<String> listDir(String dirpath){
		
		return Arrays.asList(new File(dirpath).listFiles())
												.stream()
												.map(f->f.getName())
												.collect(Collectors.toList());
	}

}
