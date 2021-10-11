package mirror;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ibm.icu.util.ULocale;

public class Wikipedia implements Runnable {

	private Element excerpt;
	private ULocale locale;
	private List<Element> alternates = new ArrayList<>();
	private String title;
	
	private Wikipedia(String repository) {
		excerpt = parse().getDocumentElement();
		if (excerpt.getTagName().equals("html")) {
			NodeList list = excerpt.getElementsByTagName("head");
			if (list.getLength() > 0) {
				list = list.item(0).getChildNodes();
				for (int i = 0; i < list.getLength(); i++) {
					Node node = list.item(i);
					if (!(node instanceof Element)) {
						continue;
					}
					Element item = (Element) node;
					if (item.getTagName().equals("link")) {
						String rel = item.getAttribute("rel");
						if ("".equals(rel)) {
							throw new IllegalArgumentException("A link tag was found on head without a rel attribute");
						} else if ("alternate".equals(rel)) {
							alternates.add(item);
						}
					} else if (item.getTagName().equals("title")) {
						title = item.getTextContent();
					}
				}
			}
		}
		String code = excerpt.getAttribute("lang");
		if (title == null && "".equals(code)) {
			int index = repository.indexOf('/');
			if (index == -1) {
				throw new IllegalArgumentException("Invalid repository name");
			}
			String owner = repository.substring(0, index);
			repository = repository.substring(index + 1);
			index = owner.indexOf('-');
			if (index == -1) {
				throw new IllegalStateException("The repository owner was expected to carry title and lang information not provided on excerpt.html");
			}
			String language = owner.substring(index + 1);
			ULocale loc = new ULocale(language);
			System.out.println(loc);
			System.out.println(loc.getDisplayLanguage());
			System.out.println(loc.getLanguage());
			System.out.println(loc.getDisplayName());
		}
		
		
		
		language = new ULocale("".equals(attribute)? "en" : attribute);
		ULocale.setDefault(language);
	}
	
	public static void main(String[] arguments) {
		System.out.println(System.getProperty("user.dir"));
		arguments = new String[]{"Abiogenesis-en/mirror"};
		String repository = arguments[0];
		new Wikipedia(repository).run();
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