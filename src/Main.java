import java.io.IOException;
import java.util.ArrayList;
import HTMLoutput.htmlOutput;
import Process.Event;



public class Main {
	
	public static void main (String args[]) throws IOException {
		ArrayList<Event> myEvents = xmlParse.parse("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/dukecal.xml");
		myEvents=new xmlProcess().process(myEvents);
		htmlOutput.makeOutput(myEvents);
		
		System.out.println(myEvents.toString());
	}
}
