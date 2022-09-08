package com.luxlunaris.cincia.backend;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * 
 * Carica file di testo come stringa unica.
 *
 */
public class ReadFile {

	public static String readTextFile(String pathname) throws IOException {

		List<String> lines = Files.readAllLines(Paths.get(pathname), StandardCharsets.UTF_8);
		Optional<String> o = lines.stream().reduce((l1,l2)->l1+"\n"+l2);
		return o.isPresent()?o.get():"";
		
	}

}
