package mirror;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Excerpt {
	
	Document document = parse();
	
	private Document parse() {
		File file = new File("src/excerpt.html");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder b = dbf.newDocumentBuilder();
			return b.parse(file);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
}
