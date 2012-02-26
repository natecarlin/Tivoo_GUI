import html_output.ConflictingEventsPage;

import html_output.ConflictingEventsPage.ConflictingEventsPageFactory;
import html_output.DayCalendarPage;
import html_output.DayCalendarPage.DayCalendarPageFactory;
import html_output.DetailPage;
import html_output.DetailPage.DetailPageFactory;
import html_output.HtmlPage;
import html_output.HtmlPageFactory;
import html_output.MonthCalendarPage;
import html_output.MonthCalendarPage.MonthCalendarPageFactory;
import html_output.SortedEventsPage;
import html_output.SortedEventsPage.SortedEventsPageFactory;
import html_output.SummaryPage;
import html_output.SummaryPage.SummaryPageFactory;
import html_output.WeekCalendarPage;
import html_output.WeekCalendarPage.WeekCalendarPageFactory;

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
	
	public void outputHtmlPage(HtmlPageFactory factory, String localPathSummary, DateTime startDate) {
	    if (myEvents.isEmpty()) {
            throw new RuntimeException("Could not output html: the list myEvents is empty.");
	    }
	    
	    List<HtmlPageFactory> allHtmlPageFactories = new ArrayList<HtmlPageFactory>();
	    allHtmlPageFactories.add(new SummaryPage.SummaryPageFactory());
	    allHtmlPageFactories.add(new DetailPageFactory());
	    allHtmlPageFactories.add(new SortedEventsPageFactory());
	    allHtmlPageFactories.add(new ConflictingEventsPageFactory());
	    allHtmlPageFactories.add(new DayCalendarPageFactory());
	    allHtmlPageFactories.add(new WeekCalendarPageFactory());
	    allHtmlPageFactories.add(new MonthCalendarPageFactory());
	    
	    for (HtmlPageFactory hpf : allHtmlPageFactories) {
	        if (hpf.isThisTypeOfPage(factory)) {
	            HtmlPage page = hpf.makePage(myEvents, localPathSummary, startDate);
	            page.createHTMLpage();
	            break;
	        }
	    }
	    
	}
	
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
