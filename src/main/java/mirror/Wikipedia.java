package mirror;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ibm.icu.util.ULocale;

public class Wikipedia implements Runnable {

	Languages languages = new Languages();
	Element body;
	Repository repository;
	String language;
	String article;
	
	private Wikipedia(String name) {
		Element root = parse().getDocumentElement();
		Head head = null;
		if (root.getTagName().equals("html")) {
			head = new Head(root);
			NodeList nodes = root.getElementsByTagName("body");
			if (nodes.getLength() != 1) {
				throw new IllegalStateException("The HTML document does not contain a <body>");
			}
			body = (Element) nodes.item(0);
		} else if (root.getTagName().equals("body")) {
			body = root;
		} else {
			throw new IllegalStateException("The root element is not supported: <" + root.getTagName() + '>');
		}
		boolean omittedTitle = head == null || head.title == null;
		language = root.getAttribute("lang");
		boolean omittedLang = "".equals(language);
		if (omittedLang && omittedTitle) {
			repository = new Repository(name, languages);
			language = this.repository.language;
			article = this.repository.article;
		} else if (omittedTitle) {
			throw new IllegalStateException("If 'lang' is specified <title> must be specified too");
		} else if (omittedLang) {
			language = "en";
			article = head.title;
		} else if (languages.contains(language)) {
			article = head.title;
			repository = new Repository(name, null);
		} else {
			throw new IllegalStateException("The value specificed as attribute 'lang' at the root element is not valid: " + language);
		}
		ULocale.setDefault(new ULocale(language));
	}
	
	public static void main(String[] arguments) {
		arguments = new String[]{"Abiogenesis-en/mirror"};
		String name = arguments[0];
		new Wikipedia(name).run();
	}
	
	Document parse() {
		File file = new File("src/excerpt.html");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder b = dbf.newDocumentBuilder();
			return b.parse(file);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void run() {
		
	}

}