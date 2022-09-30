package com.luxlunaris.cincia.tests;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListDir {

	public static List<String> listDir(String dirpath){

		//		return Arrays.asList(new File(dirpath).listFiles())
		//												.stream()
		//												.map(f->f.getName())
		//												.collect(Collectors.toList());
		
//		System.out.println(dirpath);
//		System.out.println(Arrays.asList(new File(dirpath).listFiles()));
		
		var  f=  new File(dirpath);
		
		if(!f.isDirectory()) {
			return Arrays.asList(f.getAbsolutePath());
		}
		
		var files = new ArrayList<String>();
		var newFiles = Arrays.asList(f.listFiles()).stream().map(fi->fi.getAbsolutePath()).collect(Collectors.toList());
//		files.addAll(newFiles);
		
		
		for (String nf : newFiles) {	
			files.addAll(listDir(nf));
		}
		
		return files;
		
		
//
//		return Arrays
//				.asList(new File(dirpath).listFiles())
//				.stream().flatMap(f-> {
//
//					if(f.isDirectory()) {
//						return listDir(f.getAbsolutePath()).stream();
//					}else {
//						return Stream.empty();
//					}
//
//				})
//				.collect(Collectors.toList());
//
	}

}
