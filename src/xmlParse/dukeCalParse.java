package xmlParse;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Process.Event;

public class dukeCalParse extends xmlParse {

	public dukeCalParse(String link) throws Exception {
		super(link);
	}

	public List<Event> parse ()  {
		// List of Events
		ArrayList<Event> toReturnEvents = new ArrayList<Event>();
		NodeList myEvents = myDocument.getElementsByTagName("event");
		// Run through nodes labeled event, and add to arraylist
		for (int i = 0; i < myEvents.getLength(); i++){
			Node nEvent = myEvents.item(i);
			if (nEvent.getNodeType() == Node.ELEMENT_NODE){
				DateTime start=new TimeParser().createTime(nEvent, "start");
				DateTime end=new TimeParser().createTime(nEvent, "end");
				toReturnEvents.add(new Event(extractNodeText(nEvent, "summary"), getLocation(nEvent), extractNodeText(nEvent, "description"), start, end, "")) ;
			}
			
		}
		return toReturnEvents;
	}
}
