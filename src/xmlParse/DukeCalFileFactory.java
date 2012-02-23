package xmlParse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.*;

import org.joda.time.DateTime;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Process.Event;

/**
 * @author Glenn Rivkees
 */

public class DukeCalFileFactory extends FileParseFactory {
	
	// Mappings for xpath expressions
    private static final Map<String, String> myXpathExprStrings = new HashMap<String, String>();
    static {
    	myXpathExprStrings.put("events", "//event");
    	myXpathExprStrings.put("startTime", "./start/unformatted");
    	myXpathExprStrings.put("endTime", "./end/unformatted");
    	myXpathExprStrings.put("title", "./summary");
    	myXpathExprStrings.put("location", "./location/address");
    	myXpathExprStrings.put("description", "./description");
    }
	
	public boolean isThisCal(Document doc) {
		// TODO: find way to distinguish a duke cal, right now this works because it is the last one.
		return true;
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
		ArrayList<Event> toReturnEvents = new ArrayList<Event>();
		// Run through nodes labeled event, and add to arraylist
		for (int i = 0; i < myEvents.getLength(); i++){
			Node nEvent = myEvents.item(i);
			// run xpaths and make event
			try {
                // modified next two lines to parse time
				DateTime start=new TimeParser().getDukeCalTime(pathXpr.get("startTime").evaluate(nEvent));
				DateTime end=new TimeParser().getDukeCalTime(pathXpr.get("endTime").evaluate(nEvent));				
				toReturnEvents.add(new Event(pathXpr.get("title").evaluate(nEvent), pathXpr.get("location").evaluate(nEvent), pathXpr.get("description").evaluate(nEvent), start, end, "")) ;
			} catch (XPathExpressionException e) {
				throw new RuntimeException("Event Xpath Parsing did not evaluate correctly");
			}
			
		}
		return toReturnEvents;
	}

}
