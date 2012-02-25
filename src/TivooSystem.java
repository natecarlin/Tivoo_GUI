import html_output.ConflictingEventsPage;
import html_output.DayCalendarPage;
import html_output.DetailPage;
import html_output.HtmlPage;
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
	
//	public void outputHtmlPage(HtmlPageFactory factory, String localPathSummary, DateTime startDate) {
//	    if (myEvents.isEmpty()) {
//            throw new RuntimeException("Could not output html: the list myEvents is empty.");
//	    }
//	    
//	    List<HtmlPage> allHtmlPages = new ArrayList<HtmlPage>();
//	    allHtmlPages.add(new SummaryPage(myEvents, localPathSummary));
//	    allHtmlPages.add(new DetailPage(myEvents, localPathSummary));
//	    allHtmlPages.add(new SortedEventsPage(myEvents, localPathSummary));
//	    allHtmlPages.add(new ConflictingEventsPage(myEvents, localPathSummary));
//	    allHtmlPages.add(new DayCalendarPage(myEvents, localPathSummary, startDate));
//	    allHtmlPages.add(new WeekCalendarPage(myEvents, localPathSummary, startDate));
//	    allHtmlPages.add(new MonthCalendarPage(myEvents, localPathSummary, startDate));
//	    
//	    for (HtmlPage page : allHtmlPages) {
//	        if (factory.isMyTypeOfThing(page)) {
//	            page.createHTMLpage();
//	        }
//	    }
//	    
//	}
	
	public void outputSummaryAndDetailsPages(String localPathSummary) {
	    if (myEvents.isEmpty()) {
	        throw new RuntimeException("Could not output html: the list myEvents is empty.");
	    }
	    
	    HtmlPage summaryPage = new SummaryPage(myEvents, localPathSummary);
	    summaryPage.createHTMLpage();
	    
	    DetailPage detailPage = new DetailPage(myEvents, localPathSummary);
        detailPage.createHTMLpage(); 
	}
	
	public void outputConflictingEventsPage(String localPathSummary) {
	    if (myEvents.isEmpty()) {
            throw new RuntimeException("Could not output html: the list myEvents is empty.");
        }
	    
	    HtmlPage conflictingEventsPage = new ConflictingEventsPage(myEvents, localPathSummary);
	    conflictingEventsPage.createHTMLpage();
	    
	}
	
	public void outputSortedEventsPage(String localPathSummary) {
        if (myEvents.isEmpty()) {
            throw new RuntimeException("Could not output html: the list myEvents is empty.");
        }
        HtmlPage sortedEventsPage = new SortedEventsPage(myEvents, localPathSummary);
        sortedEventsPage.createHTMLpage();
        }
	
	public void outputCalendarPages(String localPathSummary, DateTime startDate) {
	    if (myEvents.isEmpty()) {
            throw new RuntimeException("Could not output html: the list myEvents is empty.");
        }
        HtmlPage monthCalendarPage = new MonthCalendarPage(myEvents, localPathSummary, startDate);
        monthCalendarPage.createHTMLpage();
        
        HtmlPage weekCalendarPage = new WeekCalendarPage(myEvents, localPathSummary, startDate);
        weekCalendarPage.createHTMLpage();
        
        HtmlPage dayCalendarPage = new DayCalendarPage(myEvents, localPathSummary, startDate);
        dayCalendarPage.createHTMLpage();
	}
}
