package com.ewish.holidayplanner;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ManageGroupActivity extends Activity {
	protected static final String TAG = ManageGroupActivity.class.getSimpleName();
	JSONArray listMember;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_group);
		Intent intent = getIntent();
		String objectId = intent.getExtras().get(ParseConstant.KEY_OBJECT_ID).toString();
		Toast.makeText(this, "objectId"+objectId, Toast.LENGTH_LONG).show();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstant.TABLE_GROUPS);
		query.getInBackground(objectId, new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject group, ParseException e) {
				if(e==null){
					listMember =  group.getJSONArray(ParseConstant.KEY_LIST_MEMBER);
					showMembers();
					//Toast.makeText(getApplicationContext(), "listMember"+listMember.toString(), Toast.LENGTH_LONG).show();
				}
				 
				
			}
		});
		
		
		
	}
	void inviteMember(){
		
	}
	
	void showMembers(){
		for(int i=0; i<listMember.length();i++){
			try {
				ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseConstant.TABLE_USER);
				query.getInBackground(listMember.getString(i), new GetCallback<ParseUser>() {
					
					@Override
					public void done(ParseUser user, ParseException arg1) {
						//TODO: Put ListView Of member
						Log.d(TAG,"username"+user.getUsername());
					}
				});
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "exception caught"+e.getMessage(),e);
			}
		}
		
	}
	
}
