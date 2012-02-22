package xmlParse;

import java.util.List;

import org.w3c.dom.Document;

import Process.Event;

public abstract class FileParseFactory {
	
	public abstract boolean isThisKindOfThing(Document document);

	public abstract List<Event> parseEvents(Document document);

}