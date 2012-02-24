package html_output;

import html_output.HtmlUtility;

import java.util.List;
import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Text;
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
        
        addEventInfo(e, body);
        html.appendChild(body);
        return html;
    }
    
    /**
     * Add info of Event to body.
     */
    private boolean addEventInfo(Event e, Body body) {
        HtmlUtility.addTitleH2(e.getName(), body);
        HtmlUtility.addEventTime(e, body);
        HtmlUtility.addEventDescription(e, body);
        return true;
    }
}
