package xmlParse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Process.Event;

/**
 * @author Glenn Rivkees
 */

public class GoogleCalFileParser extends AbstractFileParser {
	
	public final String namespace = "http://schemas.google.com/gCal/2005";
	
	private static final Pattern FORMAT1_REGEX = Pattern.compile("[A-Z][a-z]+ [A-Z][a-z]+ [0-9]+, [0-9]+");
	private static final Pattern FORMAT2_REGEX = Pattern.compile("[A-Z][a-z]+ [A-Z][a-z]+ [0-9]+, [0-9]+ [0-9]+[ap]m");
	private static final Pattern FORMAT3_REGEX = Pattern.compile("[A-Z][a-z]+ [A-Z][a-z]+ [0-9]+, [0-9]+ [0-9]+[:][0-9]+[ap]m");
	// Mappings for xpath expressions
    private static final Map<String, String> myXpathExprStrings = new HashMap<String, String>();
    //Map patterns to DateTimeFormatters 
    private static final Map<Pattern, DateTimeFormatter> formatterMap=new HashMap<Pattern, DateTimeFormatter>();
    static {
    	myXpathExprStrings.put("events", "//entry");
    	myXpathExprStrings.put("title", "./title");
    	myXpathExprStrings.put("description", "./content");// changed it from ./summary to ./content according to Jim's requirement
    	myXpathExprStrings.put("link", "./link[@rel='alternate']");
    	formatterMap.put(FORMAT1_REGEX, DateTimeFormat.forPattern("EEE MMM d, yyyy"));
		formatterMap.put(FORMAT2_REGEX, DateTimeFormat.forPattern("EEE MMM d, yyyy haa"));
		formatterMap.put(FORMAT3_REGEX, DateTimeFormat.forPattern("EEE MMM d, yyyy h:mmaa"));
    }

	public boolean isThisCal(Document doc) {
		return doc.getDocumentElement().getAttribute("xmlns:gCal").equals(namespace);
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
                // modified next few lines to create a map which contains every information inside the 'content'
				
				Map<String, String> contentMap=getContentMap(pathXpr.get("description").evaluate(nEvent));
				DateTime[] times;
				if(contentMap.containsKey("Recurring Event")){
				    times=getRecurringTime(contentMap.get("\nFirst start"), contentMap.get("\nDuration"));
				}
				else{
					times=getRegularTime(contentMap.get("When"), formatterMap);
				}
				DateTime start=times[0];
				DateTime end=times[1];
				toReturnEvents.add(new Event(pathXpr.get("title").evaluate(nEvent), null, pathXpr.get("description").evaluate(nEvent), start, end, pathXpr.get("link").evaluate(nEvent))) ;
			} catch (XPathExpressionException e) {
				throw new ParsingException("Event Xpath Parsing did not evaluate correctly", e);
			}	
		}
		return toReturnEvents;
	}
	
	
	/*
	 * create a map to store diverse information from a 'content' tag;
	 * @author Gang Song
	 */
	
	private Map<String, String> getContentMap(String content){
		
		Map<String , String> map=new HashMap<String, String>();
		String[] elements =content.split("<br />");
		
		for(String s: elements){
			if(s.contains(": ")){
			String[] pair=s.split(": ", 2);
			map.put(pair[0], pair[1]);
			}
			else map.put(s, "");
		}
		return map;
		
	}
	
	/*
	 * get recurring time from 'content'
	 * @author Gang Song
	 */
	
	private DateTime[] getRecurringTime(String timeString, String duration){
		
		DateTimeFormatter fmt=DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss ");		
		DateTime[] times=new DateTime[2];		
		times[0]= fmt.parseDateTime(timeString.substring(0, timeString.indexOf("E")));
		times[1]=times[0].plusSeconds(Integer.parseInt(duration.substring(0, 4)));		
		return times;
	}
	
	/*
	 *get regular time from 'content' 
	 *@author Gang Song
	 */
	
	private DateTime[] getRegularTime(String timeString, Map<Pattern, DateTimeFormatter> map){
		
	    DateTime[] times=new DateTime[2];
	    String start;
	    String end;
	    if(timeString.length()>15){
	    String time=timeString.substring(0, timeString.indexOf("\n")-1);
	    start=time.substring(0, time.indexOf("to")-1);
	    StringBuilder endbuilder=new StringBuilder(time);
	    end=endbuilder.delete(time.indexOf(",")+6, time.indexOf("to")+2).toString();
	    }
	    else{
	    	start=timeString;
	    	end=timeString;
	    }
		for(Pattern pat: map.keySet()){
			Matcher startMatcher=pat.matcher(start);
			Matcher endMatcher=pat.matcher(end);
			if(startMatcher.matches()){			
				times[0]=map.get(pat).parseDateTime(start);
			}
			if(endMatcher.matches()){
				times[1]=map.get(pat).parseDateTime(end);
			}
		}
		return times;
	}
	
}