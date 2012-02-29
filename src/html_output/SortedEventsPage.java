package html_output;

import process.EventCalendar;

import com.hp.gagawa.java.elements.Html;


public class SortedEventsPage extends HtmlPage {

    public SortedEventsPage(String path) {
        super(path);
    }
    
    @Override
    public String createHTMLpage(EventCalendar events) {
        Html html = makeHtmlObject("Sorted Events", events);
        return makeFile(html, "/TiVOOSortedEventsPage.html");
    }

}
