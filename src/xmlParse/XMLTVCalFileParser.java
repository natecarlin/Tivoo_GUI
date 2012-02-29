package xmlParse;

import java.util.HashMap;

import javax.xml.xpath.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import Process.Event;

/**
 * @author Glenn Rivkees
 */

public class XMLTVCalFileParser extends AbstractFileParser {
	
	@SuppressWarnings("serial")
	public XMLTVCalFileParser(){
		// Mappings for xpath expressions
		super(
				new HashMap<String, String>(){{
					put("events", "//programme");
					put("startTime", "./@start");
					put("endTime", "./@stop");
					put("title", "./title");
					put("channel", "./channel");
					put("description", "./desc");}}
		);
	}
	
	public boolean isThisCal(Document doc) {
		return (doc.getDoctype() != null) && doc.getDoctype().getName().equals("tv");
	}

	
	public Event evaluateXpath(Node nEvent) throws XPathExpressionException {
		DateTime start=getTime(myXPathXpr.get("startTime").evaluate(nEvent));
		DateTime end=getTime(myXPathXpr.get("endTime").evaluate(nEvent));	
		Event toReturnEvent = new Event(start, end);
		toReturnEvent.addFeature("title", myXPathXpr.get("title").evaluate(nEvent));
		toReturnEvent.addFeature("description", myXPathXpr.get("description").evaluate(nEvent));
		toReturnEvent.addFeature("channel", myXPathXpr.get("channel").evaluate(nEvent));
		toReturnEvent.addFeature("link", myXPathXpr.get("link").evaluate(nEvent));
		return toReturnEvent;
	}
	
	/**
	 * create Joda Time from a specific period in an event
	 * @author Gang Song
	 */
	private DateTime getTime(String content) {
		DateTimeFormatter format=DateTimeFormat.forPattern("yyyyMMddHHmmss Z");
		DateTime time=format.parseDateTime(content);
		//TODO: Implement time parse
		return time;
	}
	
}
