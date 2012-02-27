package xmlParse;

import java.util.HashMap;

import javax.xml.xpath.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import Process.Event;

/**
 * @author Glenn Rivkees
 */

public class DukeCalFileParser extends AbstractFileParser {
	
	@SuppressWarnings("serial")
	public DukeCalFileParser(){
		// Mappings for xpath expressions
		super(
				new HashMap<String, String>(){{
					put("events", "//event");
					put("startTime", "./start/unformatted");
					put("endTime", "./end/unformatted");
					put("title", "./summary");
					put("location", "./location/address");
					put("description", "./description");}}
		);
	}
	
	public boolean isThisCal(Document doc) {
		// TODO: find way to distinguish a duke cal, right now this works because it is the last one.
		return true;
	}

	
	public Event evaluateXpath(Node nEvent) throws XPathExpressionException {
		DateTime start=getTime(myXPathXpr.get("startTime").evaluate(nEvent));
		DateTime end=getTime(myXPathXpr.get("endTime").evaluate(nEvent));				
		return new Event(myXPathXpr.get("title").evaluate(nEvent), myXPathXpr.get("location").evaluate(nEvent), myXPathXpr.get("description").evaluate(nEvent), start, end, "") ;
	}
	
	/**
	 * create Joda Time from a specific period (a subnode of an event like
	 * 'start' or 'end') in an event (this method is used for parsing
	 * DukeCalendar)
	 * @author Gang Song
	 */
	private DateTime getTime(String content) {
		
		DateTimeFormatterBuilder myBuilder=new DateTimeFormatterBuilder().appendYear(4, 4).appendMonthOfYear(2).appendDayOfMonth(2);
		if(content.length()==15){
			myBuilder.appendLiteral('T').appendHourOfDay(2).appendMinuteOfHour(2).appendSecondOfMinute(2);
		}
		DateTimeFormatter myFormat=myBuilder.toFormatter();
		DateTime myTime=myFormat.parseDateTime(content);
		return myTime;
	}
	
}
