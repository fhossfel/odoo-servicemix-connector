package de.thoughtgang.camel.odoo.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.opentest4j.AssertionFailedError;
import org.w3c.dom.Node;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;
import org.xmlunit.util.Predicate;

public class Util {

	public static String readFile(Path path) throws IOException {
		
		return readFile(path, StandardCharsets.UTF_8);
	
	}

	public static String readFile(String path) throws IOException {
		
		return readFile(path, StandardCharsets.UTF_8);
	
	}

	public static String readFile(String path, Charset encoding) throws IOException {
		
		return readFile(Paths.get(path), encoding);
	
	}

	public static String readFile(Path path, Charset encoding) throws IOException {
		
		byte[] encoded = Files.readAllBytes(path);
		return new String(encoded, encoding);
	
	}

	public static String readRessource(String path) throws IOException, URISyntaxException {
		
		return readRessource(path, StandardCharsets.UTF_8);
	
	}
	
	public static String readRessource(String path, Charset encoding) throws IOException, URISyntaxException {

		byte[] encoded = Files.readAllBytes(Paths.get(Util.class.getClassLoader().getResource(path).toURI()));
		return new String(encoded, encoding);

	}
	
	public static void assertXmlEqual(String expected, String actual) {
		
		assertXmlEqual(expected, actual, n -> true);
				
	}

	
	public static void assertXmlEqual(String expected, String actual, Predicate<Node> filter) {
	
		assertXmlEqual(expected, actual, filter, 50);
				
	}
	
	public static void assertXmlEqual(String expected, String actual, Predicate<Node> filter, int maxDiffs) {
		
		
		Diff diff = DiffBuilder.compare(expected).withTest(actual).withNodeFilter(node -> filter.test(node)).build();			
		
		int i = 0;
        for (Difference difference :  diff.getDifferences()) {

            System.out.println("***********************");
            System.out.println(difference);
            System.out.println("***********************");
            
            i++;
            
            if (i >= maxDiffs && maxDiffs != 0) break;
        }

		if (diff.hasDifferences()) throw new AssertionFailedError("-- Differences found in XML! --");

        
			
	}
	
	
	private Util() {}

}
