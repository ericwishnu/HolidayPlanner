package com.ewish.holidayplanner;

import org.json.JSONArray;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class AddGroupActivity extends Activity {
	protected static final String TAG = AddGroupActivity.class.getSimpleName();
	private EditText mGroupNameField;
	private EditText mGroupDescField;
	private Button mAddGroupButton;
	String groupId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_group);
		declareLayout();
		mAddGroupButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addGroup();
			}
		});

	}

	private void declareLayout() {
		mGroupNameField = (EditText) findViewById(R.id.groupNameField);
		mGroupDescField = (EditText) findViewById(R.id.groupDescField);
		mAddGroupButton = (Button) findViewById(R.id.addGroupButton);
	}

	private void addGroup() {
		// Create group object
		final ParseObject group = new ParseObject(ParseConstant.TABLE_GROUPS);
		group.put(ParseConstant.KEY_NAME, mGroupNameField.getText().toString());
		group.put(ParseConstant.KEY_DESCRIPTION, mGroupDescField.getText()
				.toString());
		//String currentUserObjID = ParseUser.getCurrentUser().getObjectId();
		ParseRelation<ParseObject> memberRelation = group.getRelation(ParseConstant.KEY_MEMBER_LIST);
		memberRelation.add(ParseUser.getCurrentUser());
	
		groupId = group.getObjectId();
		
		group.put(ParseConstant.KEY_MODERATOR_ID, ParseUser.getCurrentUser());
		group.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				if(e==null){
					//add Relation group to groupList _User 
					ParseUser user = ParseUser.getCurrentUser();
					ParseRelation<ParseObject> relation = user.getRelation(ParseConstant.KEY_GROUP_LIST);
					relation.add(group);
					
					user.saveInBackground(new SaveCallback() {
						
						@Override
						public void done(ParseException e) {
							if (e == null) {
								//insert to group Relation
								String groupId = group.getObjectId();
								ParseQuery<ParseObject> queryCurGroup = ParseQuery.getQuery(ParseConstant.TABLE_GROUPS);
								queryCurGroup.getInBackground(groupId, new GetCallback<ParseObject>() {
									
									@Override
									public void done(ParseObject currentGroup, ParseException e) {
										if(e==null){
											ParseObject groupRelation = new ParseObject(ParseConstant.TABLE_GROUP_RELATION);
											groupRelation.put(ParseConstant.KEY_GROUP_ID, currentGroup);
											groupRelation.put(ParseConstant.KEY_USER_ID, ParseUser.getCurrentUser());
											groupRelation.put(ParseConstant.KEY_STATUS, true);
											groupRelation.saveInBackground(new SaveCallback() {
											
												@Override
												public void done(ParseException e) {
													if(e == null){
														Toast.makeText(getBaseContext(), "Group created",Toast.LENGTH_LONG).show();
														setResult(Activity.RESULT_OK);
														finish();
													}
													
													
												}
											});
										}
										
									}
								});
										
										
							
							
							}
							else{
								parseErrorDialog(e);
							}
						}
					});
				}
				else{
					parseErrorDialog(e);
				}
				
				
			}
		});
		

	}
	protected void parseErrorDialog(ParseException e) {
		Log.e(TAG, e.getMessage());
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(e.getMessage()).setTitle("Error!")
				.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
