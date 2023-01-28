package utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

	
	public static String getDate(String dt, int num) throws ParseException {
//		String dt = "2008-01-01";  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.DATE, num);  // number of days to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		return dt;
	}
	
	public static long timeDiff(String time1, String time2) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("h:mm a", Locale.US);
		Date date1 = format.parse(time1);
		Date date2 = format.parse(time2);
		long difference = date2.getTime() - date1.getTime(); 
		return difference;
	}
}
