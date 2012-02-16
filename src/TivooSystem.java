import java.io.IOException;
import java.util.ArrayList;

import xmlParse.dukeCalParse;
import xmlParse.googleCalParse;

import HTMLoutput.HTMLdetailPage;
import HTMLoutput.HTMLsummaryPage;
import HTMLoutput.htmlOutput;
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
	
	public void outputSummaryAndDetailsPages(String localPathSummary) {
	    HTMLsummaryPage summaryPage = new HTMLsummaryPage(myEvents, localPathSummary);
        summaryPage.createHTMLpage();
	    
	    HTMLdetailPage detailPage = new HTMLdetailPage(myEvents, localPathSummary);
        detailPage.createHTMLpage(); 
        //NOTE: output cannot deal with multiple events of the same name as of yet.
        //TODO: implement multiple events of same name/ask Hewner if this is necessary
	}
}
