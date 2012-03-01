package html_output;

import org.joda.time.DateTime;
import org.joda.time.Days;

import process.EventCalendar;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Html;


public class MonthCalendarPage extends CalendarPage {
    public static final String PAGE_TITLE = "Month Calendar Page";
    
    public MonthCalendarPage(String path, DateTime startDate) {
        super(path, startDate, startDate.plusMonths(1));
        myStartDate = startDate;
        
    }

    @Override
    public String getMyFileName() {
        return "/TiVOOmonthCalendarPage.html";
    }
    
    @Override
    public Html makeHtmlObject(EventCalendar events) {
        return super.makeHtmlObject(PAGE_TITLE, events);
    }


}
