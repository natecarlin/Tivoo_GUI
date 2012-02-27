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
	
	
	public void outputHtmlPage(HtmlPageFactory factory, String localPathSummary, DateTime startDate) {
	    if (myEvents.getList().isEmpty()) {
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
	            HtmlPage page = hpf.makePage(myEvents.getList(), localPathSummary, startDate);
	            page.createHTMLpage();
	            break;
	        }
	    }
	    
	}
	
	public void outputSummaryAndDetailsPages(String localPathSummary) {
	    if (myEvents.getList().isEmpty()) {
	        throw new RuntimeException("Could not output html: the list myEvents is empty.");
	    }
	    
	    HtmlPage summaryPage = new SummaryPage(myEvents.getList(), localPathSummary);
	    summaryPage.createHTMLpage();
	    
	    DetailPage detailPage = new DetailPage(myEvents.getList(), localPathSummary);
        detailPage.createHTMLpage(); 
	}
	
	public void outputConflictingEventsPage(String localPathSummary) {
	    if (myEvents.getList().isEmpty()) {
            throw new RuntimeException("Could not output html: the list myEvents is empty.");
        }
	    
	    HtmlPage conflictingEventsPage = new ConflictingEventsPage(myEvents.getList(), localPathSummary);
	    conflictingEventsPage.createHTMLpage();
	    
	}
	
	public void outputSortedEventsPage(String localPathSummary) {
        if (myEvents.getList().isEmpty()) {
            throw new RuntimeException("Could not output html: the list myEvents is empty.");
        }
        HtmlPage sortedEventsPage = new SortedEventsPage(myEvents.getList(), localPathSummary);
        sortedEventsPage.createHTMLpage();
        }
	
	public void outputCalendarPages(String localPathSummary, DateTime startDate) {
	    if (myEvents.getList().isEmpty()) {
            throw new RuntimeException("Could not output html: the list myEvents is empty.");
        }
        HtmlPage monthCalendarPage = new MonthCalendarPage(myEvents.getList(), localPathSummary, startDate);
        monthCalendarPage.createHTMLpage();
        
        HtmlPage weekCalendarPage = new WeekCalendarPage(myEvents.getList(), localPathSummary, startDate);
        weekCalendarPage.createHTMLpage();
        
        HtmlPage dayCalendarPage = new DayCalendarPage(myEvents.getList(), localPathSummary, startDate);
        dayCalendarPage.createHTMLpage();
	}
}
