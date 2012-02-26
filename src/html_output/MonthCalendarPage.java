package html_output;

import html_output.SummaryPage.SummaryPageFactory;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Html;

import Process.Event;

public class MonthCalendarPage extends CalendarPage {
    DateTime myStartDate;
    
    public MonthCalendarPage(List<Event> events, String path, DateTime startDate) {
        super(events, path);
        myStartDate = startDate;
        
    }
    
 public static class MonthCalendarPageFactory extends HtmlPageFactory {
        
        @Override
        public boolean isThisTypeOfPage(HtmlPageFactory factory) {
            if (factory.getClass().equals(new MonthCalendarPageFactory().getClass())) return true;
            return false;
        }
        
        /**
         * Factory method
         */
        public HtmlPage makePage(List<Event> events, String localPathSummary, DateTime startDate) {
            return new MonthCalendarPage(events, localPathSummary, startDate);
        }
        
    }

    /**
     * Create a calendar view that lists events for one month starting from myStartDate
     */
    @Override
    public boolean createHTMLpage() {
        Html html = makeHtmlObject();
        return makeFile(html, "/TiVOOmonthCalendarPage.html");
    }
    
    public Html makeHtmlObject() {
        Html html = new Html();
        Body body = new Body();
        
        HtmlUtility.addTitleH2("MonthCalendarPage", body);
        
        //add events to calendar
        DateTime endDate = myStartDate.plusMonths(1);
        int numDays = Days.daysBetween(myStartDate, endDate).getDays(); 
        super.addCalendarEvents(new DateTime(myStartDate), numDays, body);
        
        html.appendChild(body);
        return html;
    }

}
