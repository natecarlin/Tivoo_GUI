package html_output;

import html_output.HtmlUtility;


import java.util.List;

import org.joda.time.DateTime;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Html;
import Process.Event;

/**
 *  @author Antares Yee
 */

public class DetailPage extends HtmlPage {
    public static final String DETAIL_DIR_PATH = "DetailDir";
    
    public DetailPage(List<Event> events, String path) {
        super(events, path);
        makeDirFromPath(DETAIL_DIR_PATH);
    }
    
    public static class DetailPageFactory extends HtmlPageFactory {
        
        @Override
        public boolean isThisTypeOfPage(HtmlPageFactory factory) {
            if (factory.getClass().equals(new DetailPageFactory().getClass())) return true;
            return false;
        }
        
        /**
         * Factory method
         */
        public HtmlPage makePage(List<Event> events, String localPathSummary, DateTime startDate) {
            return new DetailPage(events, localPathSummary);
        }
        
    }
    
    /**
     * Creates a .html detail page for each event in myDetailDirPath.
     */
    @Override
    public boolean createHTMLpage() {
        for (Event e : getMyEvents()) {
            Html html = makeHtmlObject(e);
            makeFile(html, DETAIL_DIR_PATH + HtmlUtility.makeFileName(e));
        }
        return true;
    }
    
    /**
     * Returns Html object for detail page for one Event e
     */
    private Html makeHtmlObject(Event e) {
        Html html = new Html();
        Body body = new Body();
        
        HtmlUtility.addEventInfo(e, body);
        html.appendChild(body);
        return html;
    }
}
