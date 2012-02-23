package xmlParse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Process.Event;

/**
 * @author Glenn Rivkees
 */

public class GoogleCalFileFactory extends FileParseFactory {
	
	public final String namespace = "http://schemas.google.com/gCal/2005";
	
	// Mappings for xpath expressions
    private static final Map<String, String> myXpathExprStrings = new HashMap<String, String>();
    static {
    	myXpathExprStrings.put("events", "//entry");
    	myXpathExprStrings.put("title", "./title");
    	myXpathExprStrings.put("description", "./summary");
    }

	public boolean isThisCal(Document doc) {
		return doc.getDocumentElement().getAttribute("xmlns:gCal").equals(namespace);
	}

	public List<Event> parseEvents(Document doc) {
		// Compile Xpath expressions and store in map
		Map<String, XPathExpression> pathXpr = compileXpath(myXpathExprStrings);
		// get list of event nodes
		NodeList myEvents;
		try {
			myEvents = (NodeList) pathXpr.get("events").evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			throw new RuntimeException("XPath expression failed to evaluate");
		}
		
		// List of Events
		List<Event> toReturnEvents = new ArrayList<Event>();
		// Run through nodes labeled event, and add to arraylist
		for (int i = 0; i < myEvents.getLength(); i++){
			Node nEvent = myEvents.item(i);
			try {
                // modified next two lines to parse time
				DateTime start=new TimeParser().getGoogleCalTime(pathXpr.get("description").evaluate(nEvent), "start");
				DateTime end=new TimeParser().getGoogleCalTime(pathXpr.get("description").evaluate(nEvent), "end");
				toReturnEvents.add(new Event(pathXpr.get("title").evaluate(nEvent), null, pathXpr.get("description").evaluate(nEvent), start, end, "")) ;
			} catch (XPathExpressionException e) {
				throw new RuntimeException("Event Xpath Parsing did not evaluate correctly");
			}	
		}
		return toReturnEvents;
	}

}
