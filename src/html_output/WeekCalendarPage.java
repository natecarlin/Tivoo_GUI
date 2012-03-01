package html_output;

import org.joda.time.DateTime;
import org.joda.time.Days;

import process.EventCalendar;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Html;


public class WeekCalendarPage extends CalendarPage {
    public static final String PAGE_TITLE = "Week Calendar Page";
    
    public WeekCalendarPage(String path, DateTime startDate) {
        super(path, startDate, startDate.plusWeeks(1));
    }

    @Override
    public String getMyFileName() {
        return "/TiVOOweekCalendarPage.html";
    }

    @Override
    public Html makeHtmlObject(EventCalendar events) {
        return super.makeHtmlObject(PAGE_TITLE, events);
    }


}
