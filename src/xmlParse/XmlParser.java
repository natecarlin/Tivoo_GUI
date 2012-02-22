package xmlParse;

import java.util.ArrayList;
import java.util.List;

import Process.Event;

public class XmlParser {

	static public List<Event> loadAndParse(String url) {
		
		XmlFile document = new XmlFile(url); 
		
		// initialize list of file types
		List<FileParseFactory> kindsOfFiles = new ArrayList<FileParseFactory>();
		kindsOfFiles.add(new GoogleCalFileFactory());
		kindsOfFiles.add(new DukeCalFileFactory());
		// find expression type, and then call its parser
		for (FileParseFactory expressionKind : kindsOfFiles) {
			if (expressionKind.isThisKindOfThing(document.getDoc()))
				return expressionKind.parseEvents(document.getDoc());
		}
		throw new RuntimeException("Filetype not recognized");
	}
	
}
