package com.ewish.holidayplanner;

import android.os.Bundle;
import android.preference.PreferenceActivity;
/* This class is not used yet */
public class LocationPreferences extends PreferenceActivity{

	@Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
	}
}
