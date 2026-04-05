package com.w3code.library.json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class BaseConfig {

    public static Base mCurrent;
    public static final String mTimeZone = "Europe/London";
    public static String mAppCode = "af3fcd2a-7215-4052-a610-131f7d8c2bcb";
    public static ArrayList<BaseGroup> mGroup = new ArrayList<>();
    public static boolean isCurrentEvent(String group, String start, String end) {
        return isCurrentDate(group) && isCurrentTime(start, end);
    }
    public static Boolean isCurrentDate(String group) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(mTimeZone));
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return     day == 1 && group.equals("Sunday")
                || day == 2 && group.equals("Monday")
                || day == 3 && group.equals("Tuesday")
                || day == 4 && group.equals("Wednesday")
                || day == 5 && group.equals("Thursday")
                || day == 6 && group.equals("Friday")
                || day == 7 && group.equals("Saturday");
    }
    public static Boolean isCurrentTime(String startTime, String endTime) {
        try {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm aa", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone(mTimeZone));
            String userTime = dateFormat.format(date);
            Date startDate = dateFormat.parse(startTime);
            assert startDate != null;
            startDate.setTime(startDate.getTime()-60000);
            Date endDate = dateFormat.parse(endTime);
            Date userDate = dateFormat.parse(userTime);
            assert userDate != null;
            return userDate.after(startDate) && userDate.before(endDate);
        } catch (ParseException e) {
            return false;
        }
    }
}