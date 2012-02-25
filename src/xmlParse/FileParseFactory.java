package xmlParse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import Process.Event;

/**
 * @author Glenn Rivkees
 */

public abstract class FileParseFactory {
	
	public abstract boolean isThisCal(Document document);

	public abstract List<Event> parseEvents(Document document);
	
	/*
	 * Compile Xpath expressions from concrete calendar
	 */
	public Map<String, XPathExpression> compileXpath(Map<String, String> pathXpr){
		XPath xpath = XPathFactory.newInstance().newXPath();
		Map<String, XPathExpression> compiledExpressions = new HashMap<String, XPathExpression>();
		// Xpath compilation
		try {
			// Runs through expressions, compiles, and puts in xpr map
			for (String element: pathXpr.keySet()){
				compiledExpressions.put(element, xpath.compile(pathXpr.get(element)));
			}
		} catch (XPathExpressionException e) {
			throw new ParsingException("XPath expression failed to compile and/or evaluate", e);
		}
		return compiledExpressions;
	}

}