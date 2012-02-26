package xmlParse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Process.Event;

/**
 * @author Glenn Rivkees
 */

public class CsvCalFileFactory extends FileParseFactory {
	
	// Mappings for xpath expressions
    private static final Map<String, String> myXpathExprStrings = new HashMap<String, String>();
    static {
    	myXpathExprStrings.put("events", "//row");
    	myXpathExprStrings.put("title", "./Col1");
    	myXpathExprStrings.put("description", "./Col2");
    	myXpathExprStrings.put("startTime", "./Col6");
    	myXpathExprStrings.put("endTime", "./Col16");
    	myXpathExprStrings.put("location", "./Col15");
    }

	public boolean isThisCal(Document doc) {
		return doc.getDocumentElement().getChildNodes().item(1).getNodeName().equals("row");
	}

	public List<Event> parseEvents(Document doc) {
		// Compile Xpath expressions and store in map
		Map<String, XPathExpression> pathXpr = compileXpath(myXpathExprStrings);
		// get list of event nodes
		NodeList myEvents = getEventNodeList("event", doc, pathXpr);
		
		// List of Events
		List<Event> toReturnEvents = new ArrayList<Event>();
		// Run through nodes labeled event, and add to arraylist
		for (int i = 0; i < myEvents.getLength(); i++){
			Node nEvent = myEvents.item(i);
			try {
                // modified next two lines to parse time
				DateTime start=getTime(pathXpr.get("startTime").evaluate(nEvent));
				DateTime end=getTime(pathXpr.get("endTime").evaluate(nEvent));
				toReturnEvents.add(new Event(pathXpr.get("title").evaluate(nEvent), pathXpr.get("location").evaluate(nEvent), pathXpr.get("description").evaluate(nEvent), start, end, "")) ;
			} catch (XPathExpressionException e) {
				throw new ParsingException("Event Xpath Parsing did not evaluate correctly", e);
			}	
		}
		return toReturnEvents;
	}
	
	
	/**
	 * create Joda Time from a specific period (a subnode of an event like
	 * 'start' or 'end') in an event (this method is used for parsing
	 * DukeCalendar)
	 * @author Gang Song
	 */
	private DateTime getTime(String content) {
		//TODO: Implement time parse
		return null;
	}

}
