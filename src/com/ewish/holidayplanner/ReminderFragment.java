package com.ewish.holidayplanner;

import com.ewish.holidayplanner.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ReminderFragment extends Fragment {
	protected Button mReminderIntentButton;
	

	public ReminderFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_reminder, container, false);
		
		mReminderIntentButton = (Button) rootView.findViewById(R.id.reminderIntentButton);
		mReminderIntentButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), MainReminder.class);
				startActivity(intent);
			}
		});
		return rootView;
	}
}
