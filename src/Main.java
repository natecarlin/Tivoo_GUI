import java.io.IOException;
import java.util.ArrayList;
import HTMLoutput.htmlOutput;
import Process.Event;



public class Main {
	
	public static void main (String args[]) throws IOException {
		TivooSystem s = new TivooSystem();
		s.loadFile("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/dukecal.xml.xml");
		s.filterByKeyword("Lemur");
		s.outputSummaryAndDetailsPages("Desktop/TiVOOoutput.html", "output/details_dir");
	}
}
