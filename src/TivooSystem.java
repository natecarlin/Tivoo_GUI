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


public class TivooSystem {
	
	List<Event> myEvents;
	
	public TivooSystem(){
		myEvents = new ArrayList<Event>();
	}

	/**
	 * Loads a  cal file into the Tivoo Systems
	 */
	public void loadCal(String link){
		try {
			myEvents.addAll(new XmlParser(link).loadAndParse());
		} catch (ParsingException e) {
			System.out.println("File \"" + link + "\" could not be loaded");
		}
	}
	
	public void filterByKeyword(String keyword){
		myEvents = new EventCalendar(myEvents).searchCalendar(keyword);
	}
	public void filterByTime(DateTime time){
		myEvents = new EventCalendar(myEvents).eventsAtTime(time);
	}
	
	public void outputHtmlPage(HtmlPage page) {
	    if (myEvents.isEmpty()) {
            throw new RuntimeException("Could not output html: the list myEvents is empty.");
	    }
	    page.createHTMLpage(myEvents);
	    
	    //TODO: update sorting in HtmlPage to use EventCalendar.  Also make makeHtmlOutput() accept EventCalendar instead of List<Event> events.
	}
	
}
