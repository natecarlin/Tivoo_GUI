package xmlParse;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Process.Event;

public class googleCalParse extends xmlParse {

	public googleCalParse(String link) throws Exception {
		super(link);
	}

	public List<Event> parse ()  {
		// List of Events
		List<Event> toReturnEvents = new ArrayList<Event>();
		NodeList myEvents = myDocument.getElementsByTagName("entry");
		// Run through nodes labeled event, and add to arraylist
		for (int i = 0; i < myEvents.getLength(); i++){
			Node nEvent = myEvents.item(i);
			if (nEvent.getNodeType() == Node.ELEMENT_NODE){//null;//null;
				DateTime start=new TimeParser().getGoogleCalTime(extractNodeText(nEvent, "summary"), "start");
				DateTime end=new TimeParser().getGoogleCalTime(extractNodeText(nEvent, "summary"), "end");
				toReturnEvents.add(new Event(extractNodeText(nEvent, "title"), extractNodeText(nEvent, "Location"), extractNodeText(nEvent, "summary"), start, end, "")) ;
			}
			
		}
		return toReturnEvents;
	}
}
