package edu.uams.dbmi.rts.time;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.ParticularReference;
import edu.uams.dbmi.rts.RtsDeclaration;
import edu.uams.dbmi.util.iso8601.Iso8601Date;
import edu.uams.dbmi.util.iso8601.Iso8601DateFormatter;
import edu.uams.dbmi.util.iso8601.Iso8601TimeZoneFormatter;
import edu.uams.dbmi.util.iso8601.Iso8601Date.DateConfiguration;
import edu.uams.dbmi.util.iso8601.Iso8601DateTime;
import edu.uams.dbmi.util.iso8601.Iso8601DateTimeFormatter;
import edu.uams.dbmi.util.iso8601.Iso8601Time;
import edu.uams.dbmi.util.iso8601.Iso8601UnitTime;
import edu.uams.dbmi.util.iso8601.TimeUnit;

public class TemporalReference extends ParticularReference implements RtsDeclaration {
	String identifier;
	Iui calendarSystemIui;

	public static final Iui ISO_IUI;
	public static final Iui RTS_TR_IUI;
	
	static {
		ISO_IUI = Iui.createFromString("D4AF5C9A-47BA-4BF4-9BAE-F13A8ED6455E");
		RTS_TR_IUI =  Iui.createFromString("DB2282A4-631F-4D2C-940F-A220C496F6BE");
	}
	
	public TemporalReference(String identifier, Iui calendarSystemIui) {
		this.identifier = identifier;
		setCalendarSystemIui(calendarSystemIui);
	}

	protected void setCalendarSystemIui(Iui calendarSystemIui) {
		if (calendarSystemIui == null) {
			throw new IllegalArgumentException("calendar system IUI cannot be null: " + calendarSystemIui);
		}
		this.calendarSystemIui = calendarSystemIui;
		if (!this.calendarSystemIui.equals(ISO_IUI) && !this.calendarSystemIui.equals(RTS_TR_IUI)) {
			System.out.println("WARNING: Calendar system iui is neither the standard for ISO date/time strings" +
				" nor the standard for RT systems themselves.");
		}
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
		this.calendarSystemIui = ISO_IUI;	
	}

	public TemporalReference(Iso8601DateTime dateTime) {
		//regardless of whether time zone is specified, when we ask
		//  the date/time object for a Calendar object, it comes 
		//  with one (if not specified, it's the local one).
		Calendar c = dateTime.getCalendar();
		GregorianCalendar cZ = (GregorianCalendar)Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cZ.setTimeInMillis(c.getTimeInMillis());
		//building from Calendar ensures we get a time zone
		Iso8601DateTime dtZ = new Iso8601DateTime(cZ);
		Iso8601DateTimeFormatter dtf = new Iso8601DateTimeFormatter();
		
		this.identifier = dtf.format(dtZ);
		this.calendarSystemIui = ISO_IUI;
	}

	public TemporalReference(Iso8601DateTime dateTime, TimeZone tz) {
		GregorianCalendar c = (GregorianCalendar)dateTime.getCalendar(); 
		//opposite of offset because we're converting from local to UTC, not 
		// the other way around (offset is what you do to UTC to get local).
		c.add(Calendar.MILLISECOND, -tz.getOffset(c.getTimeInMillis()));
		Iso8601DateTime txDt = new Iso8601DateTime(c);			
		Iso8601DateTimeFormatter dtf = new Iso8601DateTimeFormatter();
		this.identifier = dtf.format(txDt);
		this.calendarSystemIui = ISO_IUI;
	}
	
	public String toString() {
		return identifier;
	}
	
	public boolean isIso() {
		return this.calendarSystemIui.equals(ISO_IUI);
	}

	public Iui getCalendarSystemIui() {
		return this.calendarSystemIui;
	}
}
