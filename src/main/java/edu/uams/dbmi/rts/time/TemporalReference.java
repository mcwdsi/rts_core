package edu.uams.dbmi.rts.time;

import java.util.GregorianCalendar;
import java.util.TimeZone;

import edu.uams.dbmi.rts.ParticularReference;
import edu.uams.dbmi.util.iso8601.Iso8601Date;
import edu.uams.dbmi.util.iso8601.Iso8601DateFormatter;
import edu.uams.dbmi.util.iso8601.Iso8601TimeZoneFormatter;
import edu.uams.dbmi.util.iso8601.Iso8601Date.DateConfiguration;

public class TemporalReference extends ParticularReference {
	String identifier;
	boolean isIso;
	
	public TemporalReference(String identifier, boolean isIso) {
		this.identifier = identifier;
		this.isIso = isIso;
	}
	
	/**
	 * Get a temporal reference to the 24 hour period denoted by today's date, 
	 *  assuming the time zone given.
	 * 
	 * @param date
	 * @param tz
	 */
	public TemporalReference(Iso8601Date date, TimeZone tz) {
		//We need to get the offset for the timezone given the date specified
		GregorianCalendar c = (date.isDay()) ? date.getCalendarForDay() : date.getCalendarForFirstDayInInterval();
		//The id will be an ISO8601 formatted date.
		Iso8601DateFormatter.FormatOptions opt = new Iso8601DateFormatter.FormatOptions();
		if (date.isCentury()) opt.setConfiguration(DateConfiguration.CENTURY);
		else if (date.isYear()) opt.setConfiguration(DateConfiguration.YEAR);
		else if (date.isMonth()) opt.setConfiguration(DateConfiguration.YEAR_MONTH);
		else if (date.isWeek()) opt.setConfiguration(DateConfiguration.YEAR_WEEK);
		else opt.setConfiguration(DateConfiguration.YEAR_MONTH_DAY);
		//We're going to use extended version
		opt.setExtended(true);
		opt.setForceSignedYear(false);
		opt.setYearDigits(4);
		Iso8601DateFormatter df = new Iso8601DateFormatter(opt);
		int offset = tz.getOffset(c.getTimeInMillis());
		if (!tz.getID().equals("UTC") && offset != 0) {
			int hour = offset / 3600000;
			int min = (offset % 3600000) / 60000;
			//for dates, we use underscore for easy splitting off of time zone info later
			identifier = df.format(date) + "_" + Iso8601TimeZoneFormatter.formatTimeZone(hour, min);
		} else {
			//for dates, we use underscore for easy splitting off of time zone info later
			identifier = df.format(date) + "_Z";
		}
		isIso = true;
		
	}
	
	public String toString() {
		return identifier;
	}
	
	public boolean isIso() {
		return isIso;
	}
}
