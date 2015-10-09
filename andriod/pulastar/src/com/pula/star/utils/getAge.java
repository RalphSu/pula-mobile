package com.pula.star.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 根据用户生日来获取用户的年龄 生日格式：YYYY-MM-DD
 * 
 * @author
 * 
 */
public class getAge {

	public static int getUserAge(String time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dbDate = (Date) dateFormat.parse(time);
			
			return getAge(dbDate);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		return 4; //default value
		
	}

	public static int getAge(Date dateOfBirth) {
		int age = 0;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		if (dateOfBirth != null) {
			now.setTime(new Date());
			born.setTime(dateOfBirth);

			if (born.after(now)) {
				throw new IllegalArgumentException(
						"Can't be born in the future");
			}

			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
				age -= 1;
			}
		}
		return age;
	}

}