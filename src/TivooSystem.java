import html_output.ConflictingEventsPage;

import html_output.DayCalendarPage;
import html_output.DetailPage;
import html_output.HtmlPage;
import html_output.HtmlPageFactory;
import html_output.MonthCalendarPage;
import html_output.SortedEventsPage;

import html_output.SummaryPage;
import html_output.WeekCalendarPage;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import xmlParse.ParsingException;
import xmlParse.XmlParser;

import Process.Event;
import Process.EventCalendar;


public class TivooSystem {
	
	private EventCalendar myEvents;
	
	public TivooSystem(){
		myEvents = new EventCalendar();
	}

	/**
	 * Loads a  cal file into the Tivoo Systems
	 */
	public void loadCal(String link){
		try {
			myEvents.addAll(new XmlParser(link).loadAndParse());
		} catch (ParsingException e) {
			System.err.println(e.getMessage());
			System.err.println("File \"" + link + "\" could not be loaded");
		}
	}
	
	public EventCalendar getEventCalendar(){
		return myEvents;
	}
	

	public void outputHtmlPage(HtmlPage page) {
	    if (myEvents.getList().isEmpty()) {
            throw new RuntimeException("Could not output html: the list myEvents is empty.");
	    }
	    page.createHTMLpage(myEvents);
	    
	    //TODO: update sorting in HtmlPage to use EventCalendar.  Also make makeHtmlOutput() accept EventCalendar instead of List<Event> events.
	}
	
}
