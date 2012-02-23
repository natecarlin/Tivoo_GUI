package xmlParse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import Process.Event;

/**
 * @author Glenn Rivkees
 */

public class XmlParser {
	
	String myUrl;
	
	public XmlParser(String url){
		myUrl = url;
	}

	public List<Event> loadAndParse() {
		
		Document document = xmlFileFromUrl(myUrl); 
		
		// initialize list of file types
		List<FileParseFactory> kindsOfFiles = new ArrayList<FileParseFactory>();
		kindsOfFiles.add(new GoogleCalFileFactory());
		kindsOfFiles.add(new DukeCalFileFactory());
		// find expression type, and then call its parser
		for (FileParseFactory expressionKind : kindsOfFiles) {
			if (expressionKind.isThisCal(document))
				return expressionKind.parseEvents(document);
		}
		throw new RuntimeException("Filetype not recognized");
	}
	
	/*
	 * Load Doc from URL, parse, and save to instance var
	 */
	public Document xmlFileFromUrl(String link) {
		Document toRetDoc;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware(false);
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			toRetDoc = dBuilder.parse(link);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw new ParsingException("ParserConfigurationException");
		} catch (SAXException e) {
			e.printStackTrace();
			throw new ParsingException("SAXException");
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParsingException("IOException");
		}
		toRetDoc.getDocumentElement().normalize();
		return toRetDoc;
	}
	
}
