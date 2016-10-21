package com.usaa.acaitp.bashscriptcrazy.russhanneman;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by christophercoffee on 10/15/16.
 */

public class CM_dates {
    public String getCurrentTime()
    {
        String finalTime = "";
        Calendar cal = Calendar.getInstance();

        int millisecond = cal.get(Calendar.MILLISECOND);
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        //12 hour format
        int hour = cal.get(Calendar.HOUR);
        //24 hour format
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);

        String ampm = "";
        String finalHr = "";
        String finalMin = "";

        if(hourofday < 12)
        {
            ampm = "AM";
        }
        else
        {
            ampm = "PM";
        }

        if(hourofday > 12)
        {
            int subHour = hourofday - 12;
            finalHr = String.valueOf(subHour);
        }
        else  if(hourofday == 0)
        {
            finalHr = String.valueOf(12);
        }
        else
        {
            finalHr = String.valueOf(hourofday);
        }

        if(minute < 10)
        {
            finalMin = "0" + minute;
        }
        else
        {
            finalMin = String.valueOf(minute);
        }


        finalTime = finalHr + ":" + finalMin + " " + ampm;

        return finalTime;
    }

    public String getCurrentDate()
    {
        String finalDate = "";
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int dayofmonth = cal.get(Calendar.DAY_OF_MONTH);
        String dayLongName = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        String monthLongName = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        finalDate = monthLongName + " " + dayofmonth + " " + year + "," + dayLongName;


        return finalDate;
    }

    public void setEvent(Context context, ContentValues cv, ContentValues cv2)
    {
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        // Perform action on click

        startCalendar.set(cv.getAsInteger("year1"), cv.getAsInteger("month1"), cv.getAsInteger("day1"), cv.getAsInteger("hour"), cv.getAsInteger("min"));
        endCalendar.set(cv2.getAsInteger("year2"), cv2.getAsInteger("month2"), cv2.getAsInteger("day2"), cv2.getAsInteger("hour2"), cv2.getAsInteger("min2"));

        //	Calendar beginTime = Calendar.getInstance();
        //	beginTime.set(calendar.YEAR, calendar.MONTH, calendar.DAY_OF_MONTH, calendar.HOUR_OF_DAY, calendar.MINUTE);
        //	Calendar endTime = Calendar.getInstance();
        //	endTime.set(pYear2, pMonth2, pDay2, tpH, tpM);

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startCalendar.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endCalendar.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, cv.getAsString("title"))
                .putExtra(CalendarContract.Events.DESCRIPTION, cv.getAsString("description"))
                .putExtra(CalendarContract.Events.EVENT_LOCATION, cv.getAsString("description"))
                .putExtra(Intent.EXTRA_EMAIL, cv.getAsString("email"));
        context.startActivity(intent);
    }
}
