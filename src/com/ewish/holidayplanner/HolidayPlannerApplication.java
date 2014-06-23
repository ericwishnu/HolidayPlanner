package com.ewish.holidayplanner;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

public class HolidayPlannerApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "k4DTebPx4FNWs8mdnXMIrURhJ3pTXvK2cb2yS4I9","p0OdnVmP9i8OUfF8J9jBavjFphkRp6b7ISBXsRye");
		
		PushService.setDefaultPushCallback(getApplicationContext(), MainActivity.class);
		
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		//Log.d("TAG",""+ParseUser.getCurrentUser());
		
		if(currentUser!=null){
//			ParseInstallation installation = ParseInstallation.getCurrentInstallation();
//			installation.put(ParseConstant.KEY_USER_ID, ParseUser.getCurrentUser());
//			installation.saveInBackground();
		}else{
			//ParseInstallation.getCurrentInstallation().saveInBackground();
		}
		
		//
		
		
		
		//ParseInstallation.getCurrentInstallation().saveInBackground();
		Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
		
		

	}

}
