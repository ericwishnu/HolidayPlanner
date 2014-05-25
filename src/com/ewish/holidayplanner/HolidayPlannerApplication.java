 package com.ewish.holidayplanner;

import android.app.Application;

import com.google.android.gms.internal.in;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

public class HolidayPlannerApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		 Parse.initialize(this, "k4DTebPx4FNWs8mdnXMIrURhJ3pTXvK2cb2yS4I9", "p0OdnVmP9i8OUfF8J9jBavjFphkRp6b7ISBXsRye");
		// PushService.setDefaultPushCallback(this, NotificationReciever.class);
		 ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		 installation.put(ParseConstant.KEY_USER_ID, ParseUser.getCurrentUser());
		 installation.saveInBackground();
		 
	}

}
