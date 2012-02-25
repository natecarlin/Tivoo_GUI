import html_output.MonthCalendarPage;

import org.joda.time.DateTime;

public class Main {
	
	public static void main (String args[]) {
		TivooSystem s = new TivooSystem();
		//s.loadCal("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/dukecal.xml");
		s.loadCal("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/googlecal.xml");
		//s.filterByKeyword("a");
		
		s.outputCalendarPages(System.getProperty("user.home") + "/Desktop/", new DateTime(2011, 9, 17, 0, 0));
		//s.outputConflictingEventsPage(System.getProperty("user.home") + "/Desktop/");
		//s.outputSortedEventsPage(System.getProperty("user.home") + "/Desktop/");
		//s.outputSummaryAndDetailsPages(System.getProperty("user.home") + "/Desktop/");

		//System.out.println(s.myEvents.toString());
		//DateTime dt=new DateTime("2011-11-16T13:30:00");
		//s.filterByTime(dt);
	}
}
