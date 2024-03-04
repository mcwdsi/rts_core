package rts_core;



import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;

import edu.uams.dbmi.rts.time.TemporalReference;
import edu.uams.dbmi.util.iso8601.Iso8601Date;
import edu.uams.dbmi.util.iso8601.Iso8601Date.DateConfiguration;
import edu.uams.dbmi.util.iso8601.Iso8601DateFormatter;
import edu.uams.dbmi.util.iso8601.Iso8601TimeZoneFormatter;
import junit.framework.TestCase;

public class TemporalReferenceTest extends TestCase {

		@Test
		public void testUtcIdentifierForDate() {
			Iso8601Date d = new Iso8601Date(DateConfiguration.YEAR_MONTH_DAY, 2016, 12, 1);
			TemporalReference tr = new TemporalReference(d, TimeZone.getTimeZone("UTC"));
			assertEquals(tr.toString(), "2016-12-01_Z");
		}
		
		@Test
		public void testUtcIdentifierForMonth() {
			Iso8601Date d = new Iso8601Date(DateConfiguration.YEAR_MONTH, 2016, 12);
			TemporalReference tr = new TemporalReference(d, TimeZone.getTimeZone("UTC"));
			assertEquals(tr.toString(), "2016-12_Z");
		}
		
		@Test
		public void testUtcIdentifierForYear() {
			Iso8601Date d = new Iso8601Date(DateConfiguration.YEAR, 2016);
			TemporalReference tr = new TemporalReference(d, TimeZone.getTimeZone("UTC"));
			assertEquals(tr.toString(), "2016_Z");
		}
			
		@Test
		public void testUtcIdentifierForCentury() {
			Iso8601Date d = new Iso8601Date(DateConfiguration.CENTURY, 20);
			TemporalReference tr = new TemporalReference(d, TimeZone.getTimeZone("UTC"));
			assertEquals(tr.toString(), "20_Z");
		}
		
		
		@Test
		public void testLocalTimeZoneForDate() {

			Calendar c = Calendar.getInstance();
			TimeZone tz = c.getTimeZone();
			Iso8601Date d = new Iso8601Date(Iso8601Date.DateConfiguration.YEAR_MONTH_DAY, 
					c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH));
			Iso8601DateFormatter df = new Iso8601DateFormatter();
			String idBase = df.format(d);

			long offsetMillis = tz.getOffset(c.getTimeInMillis());
			String tzOffset;
			if (offsetMillis == 0) {
				tzOffset = "Z";
			} else {
				long offsetHour = offsetMillis / 3600000L;
				long offsetMinutes = (offsetMillis % 3600000L) / 60000L;
				if (offsetMinutes < 0) offsetMinutes = -offsetMinutes;
				tzOffset = Iso8601TimeZoneFormatter.formatTimeZone((int)offsetHour, (int)offsetMinutes);
			}
			
			String comparisonId = idBase + "_" + tzOffset;
			
			TemporalReference tr = new TemporalReference(d, tz);
			
			assertEquals(comparisonId, tr.toString());
		}
}
