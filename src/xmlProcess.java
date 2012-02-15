import java.util.ArrayList;

import org.joda.time.DateTime;

import Process.Event;


public class xmlProcess {

	public  ArrayList<Event> process(ArrayList<Event> myEvent){
		
		myEvent=keywordFilter(myEvent);

		return myEvent;		
	}

	/*
	 * use keyword to filter events
	 */
	private ArrayList<Event> keywordFilter(ArrayList<Event> myEvent){
		ArrayList<Event> temp = new ArrayList<Event>();
		for(Event current: myEvent){
			if(current.toString().contains("meet")){
				temp.add(current);
			}			
		}
		return temp;

	}
	
	public static void main (String[] args){
		
		ArrayList<Event> test =new ArrayList<Event>();
		test.add(new Event("meet with TA", "bbb", "cd", new DateTime("8"), new DateTime("10")));
		test.add(new Event("talk with TA", "bbb", "cd", new DateTime("8"), new DateTime("10")));
		test=new xmlProcess().process(test);
		
		for(Event m: test){
			System.out.println(m.toString());
		}
	}
}
