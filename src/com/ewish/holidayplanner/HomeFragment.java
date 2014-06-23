package com.ewish.holidayplanner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.ewish.holidayplanner.model.CalendarEvent;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class HomeFragment extends Fragment {
	protected final static String TAG = HomeFragment.class.getSimpleName();
	private Context context;
	private Button mPickButton;
	private Button mPushEventDataButton;
	private TextView mWelcomeTxt;
	private TextView mWelcomeEmailTxt;
	private Calendar cal;
	private int curDay;
	private int curMonth;
	private int curYear;
	View rootView;
	String dateString;
	int mSelectedDay;
	int mSelectedMonth;
	int mSelectedYear;
	Handler handler;
	ArrayList<String> event;
	ArrayList<CalendarEvent> eventCollection;
	public GregorianCalendar month, itemmonth;
	public ArrayList<String> items;
	ArrayList<String> date;
	ArrayList<String> desc;
	ParseUser currentUser;
	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_home, container, false);
		currentUser = ParseUser.getCurrentUser();
		context = rootView.getContext();
		mPickButton = (Button) rootView.findViewById(R.id.pickDateButton);
		mPushEventDataButton = (Button) rootView.findViewById(R.id.pushEventDataButton);
		mWelcomeTxt = (TextView) rootView.findViewById(R.id.welcomeTxt);
		mWelcomeEmailTxt = (TextView) rootView.findViewById(R.id.welcomeEmailTxt);
		mPickButton.setOnClickListener(pickButtonOnClick);
		mPushEventDataButton.setOnClickListener(pushButtonOnClick);

		month = (GregorianCalendar) GregorianCalendar.getInstance();
		itemmonth = (GregorianCalendar) month.clone();
		handler = new Handler();
		items = new ArrayList<String>();
		event = new ArrayList<String>();
		date = new ArrayList<String>();
		desc = new ArrayList<String>();
		handler = new Handler();
		//handler.post(calendarUpdater);
		
		Locale.setDefault(Locale.US);

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		String welcomeMsg = "Welcome, " + currentUser.getString(ParseConstant.KEY_NAME);
		mWelcomeTxt.setText(welcomeMsg);
		mWelcomeEmailTxt.setText(currentUser.getEmail().toString());
		
	}
	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			items.clear();

			// Printe dates of the current week
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			String itemvalue;

			eventCollection = Utility.readCalendarEventCollection(rootView.getContext());
			pushEvent();
			
			Log.d("=====Event====", eventCollection.size()+"");


		}

	};
	OnClickListener pushButtonOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			handler.post(calendarUpdater);

		}
	};

	OnClickListener pickButtonOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			cal = Calendar.getInstance();
			curDay = cal.get(Calendar.DAY_OF_MONTH);
			curMonth = cal.get(Calendar.MONTH);
			curYear = cal.get(Calendar.YEAR);
//
			DatePickerDialog picker = new DatePickerDialog(context,
					datePickerListener, curYear, curMonth, curDay);
			picker.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					Intent intent = new Intent(context,
							FreeScheduleActivity.class);
					intent.putExtra("date", dateString);
					intent.putExtra("selectedDay", mSelectedDay);
					intent.putExtra("selectedMonth", mSelectedMonth);
					intent.putExtra("selectedYear", mSelectedYear);
					startActivity(intent);
				}
			});
			picker.show();
			// new DatePickerDialog(context,
			// datePickerListener, year, curMonth, curDay);

		}
	};

	public void pushEvent(){
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstant.TABLE_EVENTS);
		query.whereEqualTo(ParseConstant.KEY_CREATOR, ParseUser.getCurrentUser());
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> oldEvents, ParseException arg1) {
				
				ParseObject.deleteAllInBackground(oldEvents, new DeleteCallback() {
					
					@Override
					public void done(ParseException e) {
						if(e==null){
							List<ParseObject> listEvents = new ArrayList<ParseObject>();	
							Log.d(TAG, ""+event.size());
						for(int i=0; i<eventCollection.size();i++){
							ParseObject eventTemp = new ParseObject(ParseConstant.TABLE_EVENTS);
							CalendarEvent event = eventCollection.get(i);
							if(!event.getOrganizer().equals("#contacts@group.v.calendar.google.com")){
							eventTemp.put(ParseConstant.KEY_CALENDAR_ID, event.getCalendar_id());
							eventTemp.put(ParseConstant.KEY_TITLE, event.getTitle());
							eventTemp.put(ParseConstant.KEY_DESCRIPTION, event.getDescriptions());
							eventTemp.put(ParseConstant.KEY_DATE_START, event.getStartDates());
							eventTemp.put(ParseConstant.KEY_DATE_END, event.getEndDates());
							eventTemp.put(ParseConstant.KEY_AVAILABILITY, event.getAvailability());
							eventTemp.put(ParseConstant.KEY_ORGANIZER, event.getOrganizer());
							//eventTemp.put(ParseConstant.KEY_GROUP_ID, e);
							eventTemp.put(ParseConstant.KEY_CREATOR, ParseUser.getCurrentUser());
							listEvents.add(eventTemp);}
						}
						
						ParseObject.
						saveAllInBackground(listEvents, new SaveCallback() {
							
							@Override
							public void done(ParseException e) {
								if(e!=null)
									Log.d(TAG, e.getMessage());
								
							}
						});
						}
						
					}
				});
				
			}
		});

		
		
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			mSelectedDay = selectedDay;
			mSelectedMonth = selectedMonth;
			mSelectedYear = selectedYear;
			// Intent intent = new Intent(context, FreeScheduleActivity.class);
			dateString = selectedYear + "-" + (selectedMonth + 1) + "-"
					+ selectedDay;
			// intent.putExtra("date", date);

			// Log.d(TAG, date);
		}
	};

}
