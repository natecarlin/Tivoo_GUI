package html_output;

import html_output.SummaryPage.SummaryPageFactory;

import java.util.List;

import org.joda.time.DateTime;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.Html;

import Process.Event;

public class SortedEventsPage extends HtmlPage {

    public SortedEventsPage(List<Event> events, String path) {
        super(events, path);
    }
    
 public static class SortedEventsPageFactory extends HtmlPageFactory {
        
        @Override
        public boolean isThisTypeOfPage(HtmlPageFactory factory) {
            if (factory.getClass().equals(new SortedEventsPageFactory().getClass())) return true;
            return false;
        }
        
        /**
         * Factory method
         */
        public HtmlPage makePage(List<Event> events, String localPathSummary, DateTime startDate) {
            return new SortedEventsPage(events, localPathSummary);
        }
        
    }
    
    @Override
    public boolean createHTMLpage() {
        Html html = makeHtmlObject();
        return makeFile(html, "/TiVOOSortedEventsPage.html");
    }

    private Html makeHtmlObject() {
        Html html = new Html();
        Body body = new Body();
   
        HtmlUtility.addTitleH2("Sorted Events", body);     
        for (Event e : getMyEvents()) {
            HtmlUtility.addEventInfo(e,body);
        }
        html.appendChild(body);
        return html;
    }

}
