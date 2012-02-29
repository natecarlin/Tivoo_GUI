package html_output;

import com.hp.gagawa.java.elements.Html;

import Process.EventCalendar;

public class SortedEventsPage extends HtmlPage {

    public SortedEventsPage(String path) {
        super(path);
    }
    
    @Override
    public boolean createHTMLpage(EventCalendar events) {
        Html html = makeHtmlObject("Sorted Events", events);
        return makeFile(html, "/TiVOOSortedEventsPage.html");
    }

}
