import java.io.IOException;

import org.joda.time.DateTime;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class TimeParser extends xmlParse{

	
	/*
	 * create Joda Time from a specific period (a subnode of an event like 'start' or 'end')
	 * in an event
	 */
	public static DateTime createTime(Node nEvent, String period) throws IOException {
		NodeList children = nEvent.getChildNodes();
		Node myTime = null;
		for(int j = 0; j < children.getLength(); j++){
			Node child = children.item(j);
			if(child.getNodeName().equals(period) ){
				myTime=child;
				break;
			}					
		}
		if(myTime==null)
			throw new IOException("No time on this event");
		DateTime dt =new DateTime(getIntOfTime(myTime, "year"), getIntOfTime(myTime, "month"),getIntOfTime(myTime, "day"),getIntOfTime(myTime, "hour24"),getIntOfTime(myTime, "minute"),0,0);
	   return dt;
	}
	
	/*
	 * return an integer of specific time scale
	 */
	private static int getIntOfTime(Node myTime, String scale) {
		return Integer.parseInt(extractNodeText(myTime, scale));
	}
	
}
