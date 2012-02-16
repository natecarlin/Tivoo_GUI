public class Main {
	
	public static void main (String args[]) {
		TivooSystem s = new TivooSystem();
		//s.loadDukeCal("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/dukecal.xml");
		s.loadGoogleCal("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/googlecal.xml");
		System.out.println(s.myEvents.toString());
		s.filterByKeyword("meet with");
		s.outputSummaryAndDetailsPages("/Desktop");
	}
}
