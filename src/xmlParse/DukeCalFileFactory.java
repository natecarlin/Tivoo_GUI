package xmlParse;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Process.Event;

public class DukeCalFileFactory extends FileParseFactory {

	public boolean isThisKindOfThing(Document doc) {
		System.out.println(doc.toString());
		return true;
	}

	public List<Event> parseEvents(Document doc) {
		// List of Events
		ArrayList<Event> toReturnEvents = new ArrayList<Event>();
		NodeList myEvents = doc.getElementsByTagName("event");
		// Run through nodes labeled event, and add to arraylist
		for (int i = 0; i < myEvents.getLength(); i++){
			Node nEvent = myEvents.item(i);
			if (nEvent.getNodeType() == Node.ELEMENT_NODE){
				DateTime start=new TimeParser().createTime(nEvent, "start");
				DateTime end=new TimeParser().createTime(nEvent, "end");
				toReturnEvents.add(new Event(XmlParseUtils.extractNodeText(nEvent, "summary"), XmlParseUtils.getLocation(nEvent), XmlParseUtils.extractNodeText(nEvent, "description"), start, end, "")) ;
			}
			
		}
		return toReturnEvents;
	}

}
