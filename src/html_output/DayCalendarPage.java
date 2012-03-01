package html_output;

import org.joda.time.DateTime;
import org.joda.time.Days;

import process.EventCalendar;


import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Html;

public class DayCalendarPage extends CalendarPage {
    public static final String PAGE_TITLE = "Day Calendar Page";
    
    public DayCalendarPage(String path, DateTime startDate) {
        super(path, startDate, startDate.plusDays(1));
    }

    /**
     * Create a calendar view that lists events for one month starting from myStartDate
     */
    @Override
    public String createHTMLpage(EventCalendar events) {
        Html html = makeHtmlObject(PAGE_TITLE, events);
        return makeFile(html, "/TiVOOdayCalendarPage.html");
    }

    @Override
    public String getMyFileName() {
        return "/TiVOOdayCalendarPage.html";
    }

    @Override
    public Html makeHtmlObject(EventCalendar events) {
        return super.makeHtmlObject(PAGE_TITLE, events);
    }

}
