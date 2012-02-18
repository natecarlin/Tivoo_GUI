import java.util.ArrayList;

import org.joda.time.DateTime;

import xmlParse.dukeCalParse;
import xmlParse.googleCalParse;

import HTMLoutput.HTMLdetailPage;
import HTMLoutput.HTMLsummaryPage;
import Process.Event;


public class TivooSystem {
	
	ArrayList<Event> myEvents;
	
	public TivooSystem(){
		myEvents = new ArrayList<Event>();
	}

	/*
	 * Loads a duke cal file into the Tivoo Systems
	 */
	public void loadDukeCal(String link){
		try {
			myEvents.addAll(new dukeCalParse(link).parse());
		} catch (Exception e) {
			System.out.println("File \"" + link + "\" could not be loaded");
		}
	}
	/*
	 * Loads a duke cal file into the Tivoo Systems
	 */
	public void loadGoogleCal(String link){
			try {
				myEvents.addAll(new googleCalParse(link).parse());
			} catch (Exception e) {
				System.out.println("File \"" + link + "\" could not be loaded");
			}

	}
	
	public void filterByKeyword(String keyword){
		myEvents = new xmlProcess(myEvents).process(keyword);
	}
	public void filterByTime(DateTime time){
		myEvents = new xmlProcess(myEvents).timeFilter(time);
	}
	
	public void outputSummaryAndDetailsPages(String localPathSummary) {
	    HTMLsummaryPage summaryPage = new HTMLsummaryPage(myEvents, localPathSummary);
        summaryPage.createHTMLpage();
	    
	    HTMLdetailPage detailPage = new HTMLdetailPage(myEvents, localPathSummary);
        detailPage.createHTMLpage(); 
        //TODO: what if name of event contains char not able to use for filename? ask Hewner
	}
}
