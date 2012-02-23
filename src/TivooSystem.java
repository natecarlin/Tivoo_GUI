import html_output.DetailPage;
import html_output.SummaryPage;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import xmlParse.XmlParser;

import Process.Event;


public class TivooSystem {
	
	List<Event> myEvents;
	
	public TivooSystem(){
		myEvents = new ArrayList<Event>();
	}

	/*
	 * Loads a  cal file into the Tivoo Systems
	 */
	public void loadCal(String link){
		try {
			myEvents.addAll(XmlParser.loadAndParse(link));
		} catch (Exception e) {
			System.out.println("File \"" + link + "\" could not be loaded");
		}
	}
	
	public void filterByKeyword(String keyword){
		myEvents = new EventCalendar(myEvents).searchCalendar(keyword);
	}
	public void filterByTime(DateTime time){
		myEvents = new EventCalendar(myEvents).eventsAtTime(time);
	}
	
	public void outputSummaryAndDetailsPages(String localPathSummary) {
	    if (myEvents.isEmpty()) {
	        throw new RuntimeException("Could not output html: the list myEvents is empty.");
	    }
	    
	    SummaryPage summaryPage = new SummaryPage(myEvents, localPathSummary);
	    summaryPage.createHTMLpage();
	    
	    DetailPage detailPage = new DetailPage(myEvents, localPathSummary);
        detailPage.createHTMLpage(); 
	}
}
