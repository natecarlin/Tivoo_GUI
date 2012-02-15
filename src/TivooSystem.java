import java.io.IOException;
import java.util.ArrayList;

import HTMLoutput.htmlOutput;
import Process.Event;


public class TivooSystem {
	
	ArrayList<Event> myEvents;
	
	public TivooSystem(){

	}

	/*
	 * Loads a file into the Tivoo Systems
	 */
	public void loadFile(String link){
		try {
			myEvents = xmlParse.parse(link);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void filterByKeyword(String keyword){
		myEvents = new xmlProcess(myEvents).process(keyword);
	}
	
	public void outputSummaryAndDetailsPages(String localPathSummary, String localPathDetails){
		try {
			htmlOutput.makeOutput(myEvents, localPathSummary);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
