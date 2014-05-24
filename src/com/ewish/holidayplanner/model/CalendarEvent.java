package com.ewish.holidayplanner.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CalendarEvent {
	private String _id;
	private String calendar_id;
	private String name_of_event;
	private String start_dates;
	private String end_dates;
	private String descriptions;
	private String organizer;
	private String availability;
	//calendar_od = email:1 birthday:2 publicholiday: 3
	//availability = free:0 busy:1 tentative:2 
	public CalendarEvent(String calendar_id, String _id, String name_of_event, String descriptions, String start_dates, String end_dates, String organizer,String availability) {
		super();
		this._id = _id;
		this.calendar_id = calendar_id;
		this.name_of_event = name_of_event;
		this.start_dates = start_dates;
		this.end_dates = end_dates;
		this.descriptions = descriptions;
		this.organizer = organizer;
		this.availability = availability;
	}

	public String getOrganizer() {
		return organizer;
	}

	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getCalendar_id() {
		return calendar_id;
	}

	public void setCalendar_id(String calendar_id) {
		this.calendar_id = calendar_id;
	}

	public String getName_of_event() {
		return name_of_event;
	}

	public void setName_of_event(String name_of_event) {
		this.name_of_event = name_of_event;
	}

	public String getStart_dates() {
		return start_dates;
	}

	public void setStart_dates(String start_dates) {
		this.start_dates = start_dates;
	}

	public String getEnd_dates() {
		return end_dates;
	}

	public void setEnd_dates(String end_dates) {
		this.end_dates = end_dates;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}



	public JSONObject getJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("eventId", _id);
			obj.put("calendarId", calendar_id);
			obj.put("descriptions", descriptions);
			obj.put("title", name_of_event);
			obj.put("dateStart", start_dates);
			obj.put("dateEnd", end_dates);
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
