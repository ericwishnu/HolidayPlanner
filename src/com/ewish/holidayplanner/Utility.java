package com.ewish.holidayplanner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract.Events;
import android.util.Log;

import com.ewish.holidayplanner.model.CalendarEvent;
import com.parse.ParseUser;
public class Utility {
	
	public static ArrayList<CalendarEvent> eventCollections = new ArrayList<CalendarEvent>();


	public static ArrayList<CalendarEvent> readCalendarEventCollection(Context context) {
		Cursor cursor = context.getContentResolver()
				.query(Uri.parse("content://com.android.calendar/events"),
						new String[] { "calendar_id", "title", "description",
								"dtstart", "dtend", "availability","organizer" }, null, null, null);
		cursor.moveToFirst();
		// fetching calendars name
		String CNames[] = new String[cursor.getCount()];
		
		// fetching calendars id
		eventCollections.clear();
		for (int i = 0; i < CNames.length; i++) {
			//store event to Object instead
	
			 String descriptions="";
	
			 
			if(cursor.getInt(5)==Events.AVAILABILITY_BUSY){
				//description could be null so, to make it not null
				if(cursor.getString(2)!=null){
					descriptions = cursor.getString(2);
				}
			
				
			eventCollections.add(new CalendarEvent(cursor.getString(0), cursor.getString(1), descriptions, 
					getDate(cursor.getLong(3)), getDate(cursor.getLong(4)), cursor.getString(5) ,cursor.getString(6)));
			Log.d("UTILITY",cursor.getInt(5) + " : "+cursor.getString(1)  );
			}
			//Log.d("UTILITY",cursor.getInt(5) + " : "+cursor.getString(1)  );
			CNames[i] = cursor.getString(1);
			cursor.moveToNext();

		}
		return eventCollections;
	}
	public static String getDate(long milliSeconds) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
	}
}
