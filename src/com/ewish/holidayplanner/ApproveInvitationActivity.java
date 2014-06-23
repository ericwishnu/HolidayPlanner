package com.ewish.holidayplanner;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ApproveInvitationActivity extends Activity {
	String inboxObjId;
	String inboxTitle;
	String inboxContent;
	TextView mInboxTitle;
	TextView mInboxContent;
	Button mRejectButton;
	Button mApproveButton;
	ParseObject groupFrom;
	ParseUser userTargeted;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_approve_invitation);
		inboxObjId = getIntent().getExtras().getString(ParseConstant.KEY_OBJECT_ID);
		inboxTitle = getIntent().getExtras().getString("title");
		inboxContent= getIntent().getExtras().getString("content");
		
		mRejectButton = (Button) findViewById(R.id.inboxRejectButton);
		mApproveButton = (Button) findViewById(R.id.inboxApproveButton);
		mInboxTitle = (TextView) findViewById(R.id.inboxTitleField);
		mInboxContent = (TextView) findViewById(R.id.inboxContentField);
		
		mInboxTitle.setText(inboxTitle);
		mInboxContent.setText(inboxContent);
		
		//OnClickListener
		mApproveButton.setOnClickListener(approveOnClick);
		mRejectButton.setOnClickListener(rejectOnClick);
	}
	
	OnClickListener approveOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//ParseObject groupRelation = new ParseObject(ParseConstant.TABLE_GROUP_RELATION);
			
			ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstant.TABLE_GROUP_RELATION);
			 
			query.include(ParseConstant.KEY_GROUP_ID);
			query.include(ParseConstant.KEY_USER_ID);
			
			query.getInBackground(inboxObjId, new GetCallback<ParseObject>() {

				@Override
				public void done(ParseObject groupRelation, ParseException e) {
					if(e==null){
						 groupFrom = groupRelation.getParseObject(ParseConstant.KEY_GROUP_ID);
						 userTargeted = groupRelation.getParseUser(ParseConstant.KEY_USER_ID);
						groupRelation.put(ParseConstant.KEY_STATUS, true);
						
						groupRelation.saveInBackground(new SaveCallback() {
							@Override
							public void done(ParseException e) {
								if(e==null){
									ParseRelation<ParseObject> relation = groupFrom.getRelation(ParseConstant.KEY_MEMBER_LIST);
									relation.add(userTargeted);
									groupFrom.saveInBackground(new SaveCallback() {
										
										@Override
										public void done(ParseException arg0) {
											ParseUser currentUser = ParseUser.getCurrentUser();
											ParseRelation<ParseObject> userRelation = currentUser.getRelation(ParseConstant.KEY_GROUP_LIST);
											userRelation.add(groupFrom);
		;									currentUser.saveInBackground();
											Toast.makeText(getApplicationContext(), "Approved",Toast.LENGTH_SHORT).show();
											finish();
											
										}
									});
								}
								
							}
						});
						
					}
				}
				
			});
			
		}

		
	};
	
	OnClickListener rejectOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstant.TABLE_GROUP_RELATION);
			 
			query.include(ParseConstant.KEY_GROUP_ID);
			query.include(ParseConstant.KEY_USER_ID);
			//query.
			query.getInBackground(inboxObjId, new GetCallback<ParseObject>() {

				@Override
				public void done(ParseObject arg0, ParseException arg1) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			
		}
		
	
	};
}
