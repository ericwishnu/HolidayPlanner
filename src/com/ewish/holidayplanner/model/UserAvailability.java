package com.ewish.holidayplanner.model;

import com.ewish.holidayplanner.R;

public class UserAvailability {
	private String name;
	private String email;
	private boolean availability;
	

	public UserAvailability(String name, String email, boolean availability) {
		super();
		this.name = name;
		this.email = email;
		this.availability = availability;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public int getImgUrl() {
		if (this.availability)
			return R.drawable.yes30;
		else
			return R.drawable.no30;

	}



}
