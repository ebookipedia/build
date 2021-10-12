package mirror;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class Head {
	
	Map<String, Element> translations = new HashMap<>();
	
	String title;
	
	Head(Element html) {
		NodeList nodes = html.getElementsByTagName("head");
		if (nodes.getLength() == 0) {
			return;
		}
		nodes = nodes.item(0).getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (!(node instanceof Element)) {
				continue;
			}
			Element element = (Element) node;
			String name  = element.getTagName(); 
			if (name.equals("link")) {
				Element link = element;
				String rel = link.getAttribute("rel");
				String hreflang = link.getAttribute("hreflang");
				if ("alternate".equals(rel) && !"".equals(hreflang)) {
					translations.put(hreflang, link);
				} else {
					throw new IllegalArgumentException("One of the <head> child <link> elements is not supported: child #" + (i + 1));
				}
			} else if (name.equals("title")) {
				title = element.getTextContent();
			} else {
				throw new IllegalArgumentException("One of the <head> child elements is not supported: child #" + (i + 1));
			}
		}		
	}
	
}