package com.ewish.holidayplanner.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.parse.ParseUser;

public class CalendarEvent {
	private String calendar_id;
	private String title;
	private String descriptions;
	private String startDates;
	private String endDates;
	private String availability;
	private String organizer;
	private ParseUser creator;
	
	
	public CalendarEvent(String calendar_id, String title, String descriptions, String startDates,
			String endDates,  String availability, String organizer) {
		super();
		this.calendar_id = calendar_id;
		this.title = title;
		this.startDates = startDates;
		this.endDates = endDates;
		this.descriptions = descriptions;
		this.availability = availability;
		this.organizer = organizer;
	}
	public String getCalendar_id() {
		return calendar_id;
	}
	public void setCalendar_id(String calendar_id) {
		this.calendar_id = calendar_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartDates() {
		return startDates;
	}
	public void setStartDates(String startDates) {
		this.startDates = startDates;
	}
	public String getEndDates() {
		return endDates;
	}
	public void setEndDates(String endDates) {
		this.endDates = endDates;
	}
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public String getOrganizer() {
		return organizer;
	}
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	public ParseUser getCreator() {
		return creator;
	}
	public void setCreator(ParseUser creator) {
		this.creator= creator;
	}	

	public JSONObject getJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			
			obj.put("calendarId", calendar_id);
			obj.put("title", title);
			obj.put("descriptions", descriptions);
			obj.put("dateStart", startDates);
			obj.put("dateEnd", endDates);
			obj.put("organizer", organizer);
			obj.put("availability", availability);

		} catch (JSONException e) {
			Log.e("TAG",
					"DefaultListItem.toString JSONException: " + e.getMessage(),
					e);
		}
		return obj;
	}

}
