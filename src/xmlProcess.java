import java.util.ArrayList;

import org.joda.time.DateTime;

import Process.Event;


public class xmlProcess {
	
	ArrayList<Event> myEvents;
	
	public xmlProcess (ArrayList<Event> inEvents){
		myEvents = inEvents;
	}

	public ArrayList<Event> process(String args){
		return keywordFilter(args);	
	}

	/*
	 * use keyword to filter events
	 */
	private ArrayList<Event> keywordFilter(String keyword){
		ArrayList<Event> temp = new ArrayList<Event>();
		for(Event current: myEvents){
			if(current.toString().contains(keyword)){
				temp.add(current);
			}			
		}
		return temp;

	}
	
	public static void main (String[] args){
		
		ArrayList<Event> test =new ArrayList<Event>();
		test.add(new Event("meet with TA", "perkins", "talk about TiVOO", new DateTime("2011-11-16T13:30:00"), new DateTime("2012-05-01T18:00:00"), ""));
		test.add(new Event("talk with TA", "bbb", "cd", new DateTime("2011-11-16T13:30:00"), new DateTime("2012-05-01T18:00:00"), ""));
		//test=new xmlProcess().process(test);
		
		for(Event m: test){
			System.out.println(m.toString());
			System.out.println(m.getStartTime().toString("dd-MM-yyyy HH:mm:ss") + " to " + m.getEndTime().toString("dd-MM-yyyy HH:mm:ss"));
		}
	}
}
