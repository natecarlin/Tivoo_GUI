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
    
    public DetailPage(String path) {
        super(path);
        makeDirFromPath(DETAIL_DIR_PATH);
    }
    
    //Push this up, make override.
    /**
     * Creates a .html detail page for each event in myDetailDirPath.
     */
    @Override
    public boolean createHTMLpage(List<Event> events) {
        for (Event e : events) {
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
