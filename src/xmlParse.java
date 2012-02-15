import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.*;

import org.joda.time.DateTime;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class xmlParse {

	
	static ArrayList<Event> parse (String urlString) throws IOException {
		// Load and parse XML
		Document myDoc;
		try {
			myDoc = parseXMLtreeFromURL(urlString);
		} catch (ParserConfigurationException e) {
			myDoc = null;
		} catch (SAXException e) {
			myDoc = null;
		} catch (IOException e) {
			myDoc = null;
		}
		// List of Events
		ArrayList<Event> toReturnEvents = new ArrayList<Event>();
		NodeList myEvents = myDoc.getElementsByTagName("event");
		// Run through nodes labeled event, and add to arraylist
		for (int i = 0; i < myEvents.getLength(); i++){
			Node nEvent = myEvents.item(i);
			if (nEvent.getNodeType() == Node.ELEMENT_NODE){
				DateTime start=TimeParser.createTime(nEvent, "start");
				DateTime end=TimeParser.createTime(nEvent, "end");
				toReturnEvents.add(new Event(extractNodeText(nEvent, "summary"), getLocation(nEvent), extractNodeText(nEvent, "description"), start, end)) ;
			}
			
		}
		return toReturnEvents;
	}


	
	/*
	 * Load file from URI, and parse XML Tree
	 */
	static Document parseXMLtreeFromURL(String myUrl) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document myDoc = dBuilder.parse(myUrl);
		myDoc.getDocumentElement().normalize();
		return myDoc;
	}
	
	/* 
	 * Extract Text From Node
	 */
	static String extractNodeText(Node node, String tag){
		Element n = (Element) node;
		return n.getElementsByTagName(tag).item(0).getTextContent();
	}
	static String getLocation(Node node){
		Element n = (Element) node;
		return extractNodeText(n.getElementsByTagName("location").item(0), "address");
	}
}