package edu.uams.dbmi.rts.time;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.UUID;

import edu.uams.dbmi.rts.RtsDeclaration;
import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.uui.Uui;
import edu.uams.dbmi.util.iso8601.Iso8601Date;
import edu.uams.dbmi.util.iso8601.Iso8601DateTime;
import edu.uams.dbmi.util.iso8601.Iso8601DateTimeFormatter;
import edu.uams.dbmi.util.iso8601.Iso8601Time;
import edu.uams.dbmi.util.iso8601.Iso8601UnitTime;
import edu.uams.dbmi.util.iso8601.TimeUnit;

/**
 * A reference to a temporal region, either zero or one dimensional.  For those temporal entities that
 * 	can be denoted with typical date/time strings, we allow an unambiguous ISO8601 representation (requires
 *  time zone) to serve as the unique identifier for the interval / point.  To be a point, it has to be the 
 *  most specific ISO8601 string possible, e.g., 2017-01-01T00:00.000Z
 *  
 * @author hoganwr
 * 
 * Note that there is an interesting dichotomy in conventions for referring to dates vs. for referring to 
 * 	times.
 * 
 * Dates are inherently ambiguous: one string of the form "2017-01-01" can refer to ~41 different temporal
 * 	regions depending on context, where context is essentially time zone (the Java TimeZone class returns
 * 	myriad time zones with ~41 unique time zone offset values--as of 2017-01-01 EST). For example,
 *  in the EST time zone, 2017-01-01 refers to a different 24 hour interval than in UTC, CET, PST, etc.  
 * 
 * 
 * So, to have an
 * 	unambiguous identifier for 24-hour intervals, we have to ADD time zone information to the date string.
 * 	Hence, the constructor that takes a date value also requires a time zone.  If you're stuck, the best
 * 	two choices seem like they'd typically be: time zone of locale where this code is running or UTC.
 * 
 * Date/times, on the other hand, can have ~41 standard, ISO8601 strings that all refer to the same
 *   interval: for example, 2017-01-01T12:00:00 may refer to 2017-01-01T12:00:00Z, 2017-01-01T12:00:00-01:00,
 *   2017-01-01T12:00:00-02:00,...,2017-01-01T12:00:00+01:00, 2017-01-01T12:00:00+02:00, and so on.  So
 *   in opposition to the situation with dates, where one string refers to multiple temporal regions, 
 *   for a date/time, you can have ~41 strings that refer to one temporal region!!  
 *   Hence, the constructor that takes a date/time object normalizes them all to UTC, and uses UTC
 *   as the unique identifier. If the time zone is not specified, we assume UTC.
 * 
 * 
 */
public class TemporalRegion extends RtsDeclaration {


	Iui calendarSystemIui;
	Uui type;
	TemporalReference tr;
	
	public static Uui ZERO_D_REGION_TYPE = new Uui("http://purl.obolibrary.org/obo/BFO_0000148");
	public static Uui ONE_D_REGION_TYPE = new Uui("http://purl.obolibrary.org/obo/BFO_0000038");
	
	public static final TemporalRegion MAX_TEMPORAL_REGION;
	
	public static final Iui ISO_IUI;
	public static final Iui RTS_TR_IUI;
	
	static {
		ISO_IUI = Iui.createFromString("D4AF5C9A-47BA-4BF4-9BAE-F13A8ED6455E");
		RTS_TR_IUI =  Iui.createFromString("DB2282A4-631F-4D2C-940F-A220C496F6BE");
		MAX_TEMPORAL_REGION = new TemporalRegion(
					UUID.fromString("26F1052B-311D-43B1-9ABC-B4E2EDD1B283"), 
						TemporalRegion.ONE_D_REGION_TYPE);
	}
	
	
	public TemporalRegion(TemporalReference tr, Uui type, Iui namingSystemIui) {
		this.tr = tr;
		if (tr.isIso && !namingSystemIui.equals(ISO_IUI))
			throw new IllegalArgumentException("If temporal reference isIso, then calendar system iui must be" +
					"the Iui for Gregorian calendar: " + ISO_IUI.toString());
		if (!tr.isIso && namingSystemIui.equals(ISO_IUI))
			throw new IllegalArgumentException("If temporal reference not isIso, then calendar system iui cannot be" +
					"the Iui for Gregorian calendar: " + ISO_IUI.toString());
		if (namingSystemIui == null) {
			throw new IllegalArgumentException("calendar system IUI cannot be null: " + namingSystemIui);
		}
		this.calendarSystemIui = namingSystemIui;
		this.type = type;
	}
	
	public TemporalRegion(Iso8601DateTime dateTime) {
		//regardless of whether time zone is specified, when we ask
		//  the date/time object for a Calendar object, it comes 
		//  with one (if not specified, it's the local one).
		Calendar c = dateTime.getCalendar();
		GregorianCalendar cZ = (GregorianCalendar)Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cZ.setTimeInMillis(c.getTimeInMillis());
		//building from Calendar ensures we get a time zone
		Iso8601DateTime dtZ = new Iso8601DateTime(cZ);
		Iso8601DateTimeFormatter dtf = new Iso8601DateTimeFormatter();
		
		tr = new TemporalReference(dtf.format(dtZ), true);
		calendarSystemIui = ISO_IUI;
		Iso8601Time t = dateTime.getTime();
		type = ONE_D_REGION_TYPE;
		if (t instanceof Iso8601UnitTime) {
			Iso8601UnitTime ut = (Iso8601UnitTime)t;
			if (ut.getUnit().equals(TimeUnit.MILLISECOND) || 
					ut.getUnit().equals(TimeUnit.NANOSECOND) ||
					ut.getUnit().equals(TimeUnit.MICROSECOND))
				type = ZERO_D_REGION_TYPE;
		}
	}
	
	public TemporalRegion(Iso8601DateTime dateTime, TimeZone tz) {
		//if the time zone is already specified, then illegal argument exception
		if (dateTime.getTime().isTimeZoneSpecified()) {
			throw new IllegalArgumentException("the DateTime object already has a time zone specified.");
		}
		/* The task is to take the date/time, assume it's for the given time zone,
		 *  and convert it to UTC to ensure unique identifier for that time.
		 *  If we ask the dateTime for a Calendar, it comes with a TimeZone built in. 
		 *  Changing the time zone on the Calendar object to the one specified will 
		 *  change the date/time itself, before we even do any conversion to UTC.
		 *  We need to build a Calendar object with the date/time and the time zone
		 *  given as argument first.
		 *  
		 *  Now, if the time zone is not specified, per the Iso8601Time class, the 
		 *  	time zone is assumed to be UTC.  So what we get back for 2017-01-01T12:00:00
		 *  	is a calendar for 2017-01-01T12:00:00Z.  Now suppose the time zone we get
		 *  	as an argument to this method is MST, which is 7 hours behind UTC (-07:00).  
		 *  
		 *  We want the identifier to be UTC time for 2017-01-01T12:00:00-07:00.  That turns
		 *  	out to be 2017-01-01T19:00:00Z.  So what we do is just add the opposite of
		 *  	the offset for the time zone to the calendar, create a date/time object
		 *  	from the calendar, and format it!
		 *  
		 */
		
		//gets a copy so we can change it without affecting the date/time object
		GregorianCalendar c = (GregorianCalendar) dateTime.getCalendar(); 
		//opposite of offset because we're converting from local to UTC, not 
		// the other way around (offset is what you do to UTC to get local).
		c.add(Calendar.MILLISECOND, -tz.getOffset(c.getTimeInMillis()));
		Iso8601DateTime txDt = new Iso8601DateTime(c);		
				
		Iso8601DateTimeFormatter dtf = new Iso8601DateTimeFormatter();
		tr = new TemporalReference(dtf.format(txDt), true);
				
		calendarSystemIui = ISO_IUI;
		//System.out.println(calendarSystemIui);
		type = ONE_D_REGION_TYPE;
		Iso8601Time t = dateTime.getTime();
		if (t instanceof Iso8601UnitTime) {
			Iso8601UnitTime ut = (Iso8601UnitTime)t;
			if (ut.getUnit().equals(TimeUnit.MILLISECOND) || 
					ut.getUnit().equals(TimeUnit.NANOSECOND) ||
					ut.getUnit().equals(TimeUnit.MICROSECOND))
				type = ZERO_D_REGION_TYPE;
		}
	}
	
	public TemporalRegion(Uui type) {
		//what to do for identifier if it's not ISO?
		// options: (1) IUI, (2) generic UUID (although Iui pretty much just wraps a UUID),
		//   (3) something else. 
		// I can see just adding a "t" in front of the IUI for an IC to specify the temporal
		//  projection of the lifetime / history of that IC.
		// importance of global uniqueness is not as high but still I think of data exchange 
		// among future RT-based systems, and wonder how things would be handled if two of 
		// them assign the same string as temporal reference to two wildly different
		// temporal regions.
		
		// So leaning towards #2, where IUI has the status of something higher than just a 
		// mere UUID.
		
		tr = new TemporalReference(UUID.randomUUID().toString(), false);
		//DB2282A4-631F-4D2C-940F-A220C496F6BE refers to general RTS temporal reference
		calendarSystemIui = RTS_TR_IUI;
		this.type = type;
	}
	

	public TemporalRegion(UUID id, Uui type) {
		tr = new TemporalReference(UUID.randomUUID().toString(), false);
		this.type = type;
		calendarSystemIui = RTS_TR_IUI;
	}

	
	public TemporalRegion(Iso8601Date db, TimeZone tz) {
		tr = new TemporalReference(db, tz);
		this.type = ONE_D_REGION_TYPE;
		this.calendarSystemIui = ISO_IUI;
	}

	public TemporalReference getTemporalReference() {
		return tr;
	}
	
	public boolean isISO() {
		return tr.isIso();
	}
	
	public Iui getCalendarSystemIui() {
		return calendarSystemIui;
	}
	
	public Uui getTemporalType() {
		return type;
	}
}
