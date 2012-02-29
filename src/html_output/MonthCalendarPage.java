package html_output;

import org.joda.time.DateTime;
import org.joda.time.Days;

import process.EventCalendar;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Html;


public class MonthCalendarPage extends CalendarPage {
    DateTime myStartDate;
    
    public MonthCalendarPage(String path, DateTime startDate) {
        super(path);
        myStartDate = startDate;
        
    }
    
    public Html makeHtmlObject(EventCalendar events) {
        Html html = new Html();
        Body body = new Body();
        
        addTitleH2("MonthCalendarPage", body);
        
        //add events to calendar
        DateTime endDate = myStartDate.plusMonths(1);
        int numDays = Days.daysBetween(myStartDate, endDate).getDays(); 
        addCalendarEvents(events, new DateTime(myStartDate), numDays, body);
        
        html.appendChild(body);
        return html;
    }

    @Override
    public String getMyFileName() {
        return "/TiVOOmonthCalendarPage.html";
    }

}
