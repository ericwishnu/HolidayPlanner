 package com.ewish.holidayplanner;

import android.app.Application;

import com.parse.Parse;

public class HolidayPlannerApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		 Parse.initialize(this, "k4DTebPx4FNWs8mdnXMIrURhJ3pTXvK2cb2yS4I9", "p0OdnVmP9i8OUfF8J9jBavjFphkRp6b7ISBXsRye");
	}

}
