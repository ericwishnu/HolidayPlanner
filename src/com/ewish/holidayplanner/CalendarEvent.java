package com.ewish.holidayplanner;

public class CalendarEvent {
	private String calendar_id;
	private String title;
	private String descriptions;
	private String startDates;
	private String endDates;
	private String availability;
	
	
	public CalendarEvent(String calendar_id, String title, String descriptions, String startDates,
			String endDates,  String availability) {
		super();
		this.calendar_id = calendar_id;
		this.title = title;
		this.startDates = startDates;
		this.endDates = endDates;
		this.descriptions = descriptions;
		this.availability = availability;
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
	
	
}
