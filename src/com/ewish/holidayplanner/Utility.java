package com.ewish.holidayplanner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

public class Utility {
	protected static String TAG = Utility.class.getSimpleName();
	public static ArrayList<String> calendarID = new ArrayList<String>();
	public static ArrayList<String> nameOfEvent = new ArrayList<String>();
	public static ArrayList<String> startDates = new ArrayList<String>();
	public static ArrayList<String> endDates = new ArrayList<String>();
	public static ArrayList<String> descriptions = new ArrayList<String>();
	public static ArrayList<String> organizier = new ArrayList<String>();
	public static ArrayList<String> _id = new ArrayList<String>();
	
	
	public static ArrayList<String> readCalendarEvent(Context context) {
		Cursor cursor = context.getContentResolver()
				.query(Uri.parse("content://com.android.calendar/events"),new String[] { "calendar_id", "title", "description",
								"dtstart", "dtend", "eventLocation" ,"organizer","_id", "original_id","original_sync_id"}, null,
						null, null);
		ParseCloud.callFunctionInBackground("hello", new HashMap<String, Object>(), new FunctionCallback<String>() {
			  public void done(String result, ParseException e) {
			    if (e == null) {
			      // result is "Hello world!"
			    }
			  }
			});
		cursor.moveToFirst();

		// fetching calendars name
		String CNames[] = new String[cursor.getCount()];
		// Log.d(TAG, CNames[0].toString());
		// fetching calendars id
		calendarID.clear();
		nameOfEvent.clear();
		startDates.clear();
		endDates.clear();
		organizier.clear();
		descriptions.clear();
		_id.clear();
		
		for (int i = 0; i < CNames.length; i++) {
			calendarID.add(cursor.getString(0).trim());
			nameOfEvent.add(cursor.getString(1).trim());
			startDates.add(getDate(Long.parseLong(cursor.getString(3))));
			endDates.add(getDate(Long.parseLong(cursor.getString(4))));
			descriptions.add(cursor.getString(2).trim());
			organizier.add(cursor.getString(6).trim());
			_id.add(cursor.getString(7));
			
			CNames[i] = cursor.getString(1);

			cursor.moveToNext();

			// if(!cursor.isNull(i)){

		}
		return nameOfEvent;
	}

	
	public static String getDate(long milliSeconds) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
	}
}
