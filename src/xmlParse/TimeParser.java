package xmlParse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

/*
 * @author Gang Song
 */

public class TimeParser {
	
	/*
	 * create Joda Time from a specific period (a subnode of an event like
	 * 'start' or 'end') in an event (this method is used for parsing
	 * DukeCalendar)
	 */
	public DateTime getDukeCalTime(String content) {
		
		DateTimeFormatterBuilder myBuilder=new DateTimeFormatterBuilder().appendYear(4, 4).appendMonthOfYear(2).appendDayOfMonth(2);
		if(content.length()==15){
			myBuilder.appendLiteral('T').appendHourOfDay(2).appendMinuteOfHour(2).appendSecondOfMinute(2);
		}
		DateTimeFormatter myFormat=myBuilder.toFormatter();
		DateTime myTime=myFormat.parseDateTime(content);
		return myTime;
	}
	
	/*
	 * create a DateTime Object from a string in Google Calendar
	 */

	public DateTime getGoogleCalTime(String content, String period) {

		String[] sections = content.split(" ");

		if (sections[0].equals("Recurring")) {
			String date = sections[3];
			String time = sections[4];
			DateTime dt = new DateTime(date + "T" + time);
			if (period.equals("start"))
				return dt;
			else
				return dt.plusSeconds(Integer.parseInt(sections[6].substring(0,
						4)));
		} else {
			DateTimeFormatterBuilder myFormatBuilder = new DateTimeFormatterBuilder();
			myFormatBuilder.appendMonthOfYearShortText().appendDayOfMonth(1)
					.appendLiteral(',').appendYear(4, 4);
			DateTimeFormatter myFormat;

			String myYear;
			String myTime;
			String myMonth = sections[2];
			String myDay = sections[3];
			if (sections[4].contains("<")) {
				myYear = sections[4].substring(0, sections[4].indexOf("<"));
				myFormat = myFormatBuilder.toFormatter();
				return myFormat.parseDateTime(myMonth + myDay + myYear);
			}
			myYear = sections[4];

			if (period.equals("start")) {

				myTime = sections[5];
			} else {
				myTime = sections[7].substring(0, sections[7].indexOf("&"));
			}

			myFormatBuilder = checkAndBuildHourFormat(myFormatBuilder, myTime);
			myFormat = myFormatBuilder.toFormatter();
			return myFormat.parseDateTime(myMonth + myDay + myYear + myTime);
		}

	}

	/*
	 * construct a DateTimeFormatterBuilder according to different hour and
	 * minute format
	 */
	private DateTimeFormatterBuilder checkAndBuildHourFormat(
			DateTimeFormatterBuilder myBuilder, String myHourAndMinute) {
		if (myHourAndMinute.contains(":"))
			return myBuilder.appendClockhourOfHalfday(1).appendLiteral(':')
					.appendMinuteOfHour(2).appendHalfdayOfDayText();
		else
			return myBuilder.appendClockhourOfHalfday(1)
					.appendHalfdayOfDayText();

	}

}
