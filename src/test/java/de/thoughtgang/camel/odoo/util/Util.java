package de.thoughtgang.camel.odoo.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.xmlrpc.XmlRpcException;

import org.apache.commons.httpclient.methods.RequestEntity;
import org.opentest4j.AssertionFailedError;
import org.slf4j.Logger;
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
        
    
        
    public static void logRequest(Logger logger, RequestEntity requestEntity) throws XmlRpcException {
        
        ByteArrayOutputStream bos = null;

        try {
            logger.debug("---- Request ----");
            bos = new ByteArrayOutputStream();
            requestEntity.writeRequest(bos);
            logger.debug(toPrettyXml(logger, bos.toString()));
            
        }   catch (IOException e) {

            throw new XmlRpcException(e.getMessage(), e);
            
        } finally {

            closeQuietly(bos);
            
        }
        
    }


    public static void logRequest(Logger logger, String content) {
        logger.debug("---- Request ----");
        logger.debug(toPrettyXml(logger, content));
    }

    public static String logResponse(Logger logger, InputStream istream)throws XmlRpcException {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(istream));
            String line = null;
            StringBuilder respBuf = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                respBuf.append(line);
            }
            String response = respBuf.toString();
            logger.debug("---- Response ----");
            logger.debug(toPrettyXml(logger, respBuf.toString()));
            return response;
        } catch (IOException e) {
            throw new XmlRpcException(e.getMessage(), e);
        } finally {
            closeQuietly(reader);
        }
    }

    public static void logResponse(Logger logger, String content) {
        logger.debug("---- Response ----");
        logger.debug(toPrettyXml(logger, content));
    }

    private static String toPrettyXml(Logger logger, String xml) {
        try {
            Transformer transformer
                    = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "2");
            StreamResult result = new StreamResult(new StringWriter());
            StreamSource source = new StreamSource(new StringReader(xml));
            transformer.transform(source, result);
            return result.getWriter().toString();
        } catch (Exception e) {

            logger.warn("Can't parse XML");
            return xml;

        }

    }
        
    private static void closeQuietly(OutputStream os) {
        
        try {            
            os.close();        
        } catch (Exception e) {            
            // do nothing            
        }        
    }
    
    private static void closeQuietly(Reader reader) {
        
       try {            
           reader.close();        
       } catch (Exception e) {            
            // do nothing            
       }        
    }

	
    private Util() {}

}
