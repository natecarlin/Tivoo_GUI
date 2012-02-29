package html_output;

import org.joda.time.DateTime;
import org.joda.time.Days;

import process.EventCalendar;


import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Html;

public class DayCalendarPage extends CalendarPage {
    DateTime myStartDate;
    
    public DayCalendarPage(String path, DateTime startDate) {
        super(path);
        myStartDate = startDate;
    }

    /**
     * Create a calendar view that lists events for one month starting from myStartDate
     */
    @Override
    public String createHTMLpage(EventCalendar events) {
        Html html = makeHtmlObject(events);
        return makeFile(html, "/TiVOOdayCalendarPage.html");
    }
    
    public Html makeHtmlObject(EventCalendar events) {
        Html html = new Html();
        Body body = new Body();
        
        addTitleH2("DayCalendarPage", body);
        
        //add events to calendar
        DateTime endDate = myStartDate.plusDays(1);
        int numDays = Days.daysBetween(myStartDate, endDate).getDays(); 
        addCalendarEvents(events, new DateTime(myStartDate), numDays, body);
        
        html.appendChild(body);
        return html;
    }

}
