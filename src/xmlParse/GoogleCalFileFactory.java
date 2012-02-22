package xmlParse;

import java.util.ArrayList;
import java.util.List;

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

	public boolean isThisKindOfThing(Document doc) {
		if (doc.getDocumentElement().getAttribute("xmlns:gCal").equals(namespace))
			return true;
		return false;
	}

	public List<Event> parseEvents(Document doc) {
		// List of Events
		List<Event> toReturnEvents = new ArrayList<Event>();
		NodeList myEvents = doc.getElementsByTagName("entry");
		// Run through nodes labeled event, and add to arraylist
		for (int i = 0; i < myEvents.getLength(); i++){
			Node nEvent = myEvents.item(i);
			if (nEvent.getNodeType() == Node.ELEMENT_NODE){//null;//null;
				DateTime start=new TimeParser().getGoogleCalTime(XmlParseUtils.extractNodeText(nEvent, "summary"), "start");
				DateTime end=new TimeParser().getGoogleCalTime(XmlParseUtils.extractNodeText(nEvent, "summary"), "end");
				toReturnEvents.add(new Event(XmlParseUtils.extractNodeText(nEvent, "title"), XmlParseUtils.extractNodeText(nEvent, "Location"), XmlParseUtils.extractNodeText(nEvent, "summary"), start, end, "")) ;
			}
			
		}
		return toReturnEvents;
	}

}
