package xmlParse;

import org.w3c.dom.*;

public abstract class XmlParseUtils {


	/*
	 * Extract Text From Node
	 */
	static String extractNodeText(Node node, String tag) {
		Element n = (Element) node;
		try {
			return n.getElementsByTagName(tag).item(0).getTextContent();
		} catch (NullPointerException e) {
			return "";
		}
	}

	static String getLocation(Node node) {
		Element n = (Element) node;
		return extractNodeText(n.getElementsByTagName("location").item(0),
				"address");
	}
}