package com.ewish.holidayplanner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ewish.holidayplanner.adapter.AvailabilityArrayAdapter;
import com.ewish.holidayplanner.model.UserAvailability;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class CreateEventActivity extends Activity {
	protected static final String TAG = CreateEventActivity.class.getSimpleName();
	protected Button mCreateEventButton;
	private String groupObjectId;
	
	private ListView mListMemberAvailability;
	protected TextView mDates;
	
	ArrayList<HashMap<String, String>> groupList = new ArrayList<HashMap<String, String>>();
	AvailabilityArrayAdapter adapter;
	String dateString;
	List <UserAvailability> usersAvailabilities;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		Intent intent = getIntent();
		groupObjectId = intent.getExtras().getString("objectId");
		dateString = intent.getExtras().getString("date");
		
		mDates = (TextView) findViewById(R.id.selectedDateTxt);
		mCreateEventButton = (Button) findViewById(R.id.createEventButton);
		mListMemberAvailability = (ListView) findViewById(R.id.listMemberAvailability);
		mDates.setText(dateString);
		//memberList = new ArrayList<ParseUser>();
		usersAvailabilities = new ArrayList<UserAvailability>();
		loadMembers();
		
		mCreateEventButton.setOnClickListener(createEventOnClick);
		
	}
	
	OnClickListener createEventOnClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			
			try {
				Date date;
				date = df.parse(dateString);
				
				GregorianCalendar calDate = (GregorianCalendar) GregorianCalendar.getInstance();
				calDate.setTime(date);
				
				Intent calIntent = new Intent(Intent.ACTION_INSERT); 
				calIntent.setType("vnd.android.cursor.item/event");    
				//calIntent.putExtra(Events.TITLE, "My House Party"); 
				//calIntent.putExtra(Events.EVENT_LOCATION, "My Beach House"); 
				//calIntent.putExtra(Events.DESCRIPTION, "A Pig Roast on the Beach"); 
		
				//GregorianCalendar calDate = new GregorianCalendar(2012, 7, 15);
				calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true); 
				calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,calDate.getTimeInMillis()); 
				calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calDate.getTimeInMillis()); 
				 
				startActivity(calIntent);
				
			} catch (java.text.ParseException e) {
				Log.e(TAG, "Caught exception : " + e.getMessage() , e);
			}
			
			
		
			
			
		}
	};
	void loadMembers(){
		Log.d(TAG, "Group : "+ groupObjectId);
		// Get all group where objectId = gorupObjectId
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstant.TABLE_GROUPS);
		
		query.getInBackground(groupObjectId, new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject group, ParseException e) {
				if(e==null){
					
					//get all memberlist that store in listMember Column
					ParseRelation<ParseUser> relation = group.getRelation(ParseConstant.KEY_MEMBER_LIST);
					ParseQuery<ParseUser> members = relation.getQuery();
					members.findInBackground(new FindCallback<ParseUser>() {
						
						@Override
						public void done(final List<ParseUser> memberList, ParseException e) {
							if(e==null){
								// Select all events where the Creator equals to listMember from group
								ParseQuery<ParseObject> queryEvents = ParseQuery.getQuery(ParseConstant.TABLE_EVENTS);								
								queryEvents.whereContainedIn(ParseConstant.KEY_CREATOR, memberList);						
								queryEvents.findInBackground(new FindCallback<ParseObject>() {

									@Override
									public void done(final List<ParseObject> events, ParseException e) {
										if(e==null){
											usersAvailabilities.clear();
											boolean availability[] = new boolean[memberList.size()];
											for(int i = 0 ; i < availability.length ; i++){
												availability[i]=true;
											}
											for(int j = 0 ; j < events.size() ; j++){
												try{
													ParseObject event = events.get(j);
													SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
													Date startDate = sdf.parse(event.getString(ParseConstant.KEY_DATE_START)); //2014-06-17
													Date endDate = sdf.parse(event.getString(ParseConstant.KEY_DATE_END));//2014-06-26
													Date pickedDate = sdf.parse(dateString);//2014-06-21
													Log.d(TAG, "picked: " + pickedDate.toString() + " start: "+startDate.toString()+ " end: " + endDate.toString());
													//if date colition on each event
													if(pickedDate.compareTo(startDate) >= 0 && pickedDate.compareTo(endDate) <=0 ){
														Log.d(TAG, "OCCUPIED by " + event.getString(ParseConstant.KEY_ORGANIZER));
														Log.d(TAG, "event: "+event.get(ParseConstant.KEY_TITLE).toString() + "organizer : " + event.getString(ParseConstant.KEY_ORGANIZER));
														Log.d(TAG, "memberlist size: " + memberList.size());
														for(int k=0 ; k< memberList.size();k++){
															//error here
															ParseUser creator = (ParseUser) event.get(ParseConstant.KEY_CREATOR);
															
															if(memberList.get(k).hasSameId(creator)){
																availability[k] = false;
																
																Log.d(TAG, "index: "+ k +"set FALSE");
															}
														}
													}
													
												
												}
												catch(Exception ex){
														Log.e(TAG, "Caught exception : "+e.getMessage(),e);
												}
											}
											for(int i = 0 ; i<availability.length;i++){
												usersAvailabilities.add(new UserAvailability(memberList.get(i).getString(ParseConstant.KEY_NAME), 
														memberList.get(i).getString(ParseConstant.KEY_EMAIL), availability[i]));
												
												
												Log.d(TAG,memberList.get(i).getString(ParseConstant.KEY_EMAIL) +" status:"+ availability[i]);
											}
											adapter= new AvailabilityArrayAdapter(getApplicationContext(), R.id.listMemberAvailability, usersAvailabilities);
											mListMemberAvailability.setAdapter(adapter);
										}else{
											loggingException(e);
										}
										
									}
									
								});
							}else{
								loggingException(e);
							}
							
						}
					});
					
					
					
					
				}
				else{
					loggingException(e);
				}
			}
			
		});
	}
	void checkEvent(List<ParseObject> events){
		for(int j = 0 ; j < events.size() ; j++){
			ParseObject event = events.get(j);
	
			
		}
	}
	void loadData(){
		
	}
	void createEvent(){
		
	}
	void loggingException(ParseException e){
		Log.e(TAG, "Exception caught! :" + e.getMessage(), e);
	}
}
