package com.ewish.holidayplanner.model;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class Inbox {
	private ParseObject group;
	private ParseUser user;

	private Boolean status;
	public Inbox(ParseObject group, ParseUser user, Boolean status) {
		super();
		this.group = group;
		this.user = user;
		this.status = status;
	}
	public ParseObject getGroup() {
		return group;
	}
	public void setGroup(ParseObject group) {
		this.group = group;
	}
	public ParseUser getUser() {
		return user;
	}
	public void setUser(ParseUser user) {
		this.user = user;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
