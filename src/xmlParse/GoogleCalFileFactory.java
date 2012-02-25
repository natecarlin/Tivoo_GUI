package xmlParse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathConstants;
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
			throw new ParsingException("XPath expression failed to evaluate", e);
		}
		
		// List of Events
		List<Event> toReturnEvents = new ArrayList<Event>();
		// Run through nodes labeled event, and add to arraylist
		for (int i = 0; i < myEvents.getLength(); i++){
			Node nEvent = myEvents.item(i);
			try {
                // modified next two lines to parse time
				DateTime start=getGoogleCalTime(pathXpr.get("description").evaluate(nEvent), "start");
				DateTime end=getGoogleCalTime(pathXpr.get("description").evaluate(nEvent), "end");
				toReturnEvents.add(new Event(pathXpr.get("title").evaluate(nEvent), null, pathXpr.get("description").evaluate(nEvent), start, end, "")) ;
			} catch (XPathExpressionException e) {
				throw new ParsingException("Event Xpath Parsing did not evaluate correctly", e);
			}	
		}
		return toReturnEvents;
	}
	
	
	/**
	 * create a DateTime Object from a string in Google Calendar
	 * @author Gang Song
	 */

	private DateTime getGoogleCalTime(String content, String period) {

		String[] sections = content.split(" ");

		if (sections[0].equals("Recurring")) {
			String date = sections[3];
			String time = sections[4];
			DateTime dt = new DateTime(date + "T" + time);
			if (period.equals("start"))
				return dt;
			else
				return dt.plusSeconds(Integer.parseInt(sections[6].substring(0,
						4)));
		} else {
			DateTimeFormatterBuilder myFormatBuilder = new DateTimeFormatterBuilder();
			myFormatBuilder.appendMonthOfYearShortText().appendDayOfMonth(1)
					.appendLiteral(',').appendYear(4, 4);
			DateTimeFormatter myFormat;

			String myYear;
			String myTime;
			String myMonth = sections[2];
			String myDay = sections[3];
			if (sections[4].contains("<")) {
				myYear = sections[4].substring(0, sections[4].indexOf("<"));
				myFormat = myFormatBuilder.toFormatter();
				return myFormat.parseDateTime(myMonth + myDay + myYear);
			}
			myYear = sections[4];

			if (period.equals("start")) {

				myTime = sections[5];
			} else {
				myTime = sections[7].substring(0, sections[7].indexOf("&"));
			}

			myFormatBuilder = checkAndBuildHourFormat(myFormatBuilder, myTime);
			myFormat = myFormatBuilder.toFormatter();
			return myFormat.parseDateTime(myMonth + myDay + myYear + myTime);
		}

	}

	/**
	 * construct a DateTimeFormatterBuilder according to different hour and
	 * minute format
	 * @author Gang Song
	 */
	private DateTimeFormatterBuilder checkAndBuildHourFormat(
			DateTimeFormatterBuilder myBuilder, String myHourAndMinute) {
		if (myHourAndMinute.contains(":"))
			return myBuilder.appendClockhourOfHalfday(1).appendLiteral(':')
					.appendMinuteOfHour(2).appendHalfdayOfDayText();
		else
			return myBuilder.appendClockhourOfHalfday(1)
					.appendHalfdayOfDayText();

	}

}
