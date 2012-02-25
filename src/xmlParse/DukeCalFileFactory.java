package xmlParse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.*;

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
			throw new ParsingException("XPath expression failed to evaluate", e);
		}
		
		// List of Events
		ArrayList<Event> toReturnEvents = new ArrayList<Event>();
		// Run through nodes labeled event, and add to arraylist
		for (int i = 0; i < myEvents.getLength(); i++){
			Node nEvent = myEvents.item(i);
			// run xpaths and make event
			try {
                // modified next two lines to parse time
				DateTime start=getDukeCalTime(pathXpr.get("startTime").evaluate(nEvent));
				DateTime end=getDukeCalTime(pathXpr.get("endTime").evaluate(nEvent));				
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
	private DateTime getDukeCalTime(String content) {
		
		DateTimeFormatterBuilder myBuilder=new DateTimeFormatterBuilder().appendYear(4, 4).appendMonthOfYear(2).appendDayOfMonth(2);
		if(content.length()==15){
			myBuilder.appendLiteral('T').appendHourOfDay(2).appendMinuteOfHour(2).appendSecondOfMinute(2);
		}
		DateTimeFormatter myFormat=myBuilder.toFormatter();
		DateTime myTime=myFormat.parseDateTime(content);
		return myTime;
	}
	
}
