package xmlParse;

import java.util.ArrayList;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import Process.Event;

public abstract class xmlParse {
	
	Document myDocument;
	
	/*
	 * Load Doc from URL, parse, and save to instance var
	 */
	public xmlParse(String link) throws Exception{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document myDoc = dBuilder.parse(link);
		myDoc.getDocumentElement().normalize();
		myDocument = myDoc;
	}

	
	public abstract ArrayList<Event> parse();
	
	/* 
	 * Extract Text From Node
	 */
	static String extractNodeText(Node node, String tag){
		Element n = (Element) node;
		try {
			return n.getElementsByTagName(tag).item(0).getTextContent();
		} catch (NullPointerException e) {
			return "";
		}
	}
	static String getLocation(Node node){
		Element n = (Element) node;
		return extractNodeText(n.getElementsByTagName("location").item(0), "address");
	}
}