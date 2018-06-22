package messenger.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatTest {

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		int i = cal.getWeekYear();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("hh:mm:ss");
		
		System.out.println(format.format(date));
		System.out.println(format2.format(date));
		

	}

}
