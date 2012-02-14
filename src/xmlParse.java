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
				DateTime start=createTime(nEvent, "start");
				DateTime end=createTime(nEvent, "end");
				toReturnEvents.add(new Event(extractNodeText(nEvent, "summary"), getLocation(nEvent), extractNodeText(nEvent, "description"), start, end)) ;
			}
			
		}
		return toReturnEvents;
	}

	/*
	 * create Joda Time from a specific period (a subnode of an event like 'start' or 'end')
	 * in an event
	 */
	private static DateTime createTime(Node nEvent, String period) throws IOException {
		NodeList children = nEvent.getChildNodes();
		Node myTime = null;
		for(int j = 0; j < children.getLength(); j++){
			Node child = children.item(j);
			if(child.getNodeName().equals(period) ){
				myTime=child;
				break;
			}					
		}
		if(myTime==null)
			throw new IOException("No time on this event");
		DateTime dt =new DateTime(getIntOfTime(myTime, "hour"), getIntOfTime(myTime, "month"),getIntOfTime(myTime, "day"),getIntOfTime(myTime, "hour24"),getIntOfTime(myTime, "minute"),0,0);
	   return dt;
	}
	
	/*
	 * return an integer of specific time scale
	 */
	private static int getIntOfTime(Node myTime, String scale) {
		return Integer.parseInt(extractNodeText(myTime, scale));
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