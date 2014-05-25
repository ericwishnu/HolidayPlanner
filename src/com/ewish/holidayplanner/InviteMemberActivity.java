package com.ewish.holidayplanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class InviteMemberActivity extends Activity {
	private EditText mUserNameField;
	private Button mInviteBtn;
	private String groupId;

	// ParseUser user ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_member);
		groupId = getIntent().getExtras()
				.getString(ParseConstant.KEY_OBJECT_ID);

		mUserNameField = (EditText) findViewById(R.id.usernameField);
		mInviteBtn = (Button) findViewById(R.id.inviteBtn);
		mInviteBtn.setOnClickListener(inviteClick);
	}

	OnClickListener inviteClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String email = mUserNameField.getText().toString();

			// get User from email that been input
			ParseQuery<ParseUser> userQuery = ParseQuery
					.getQuery(ParseConstant.TABLE_USER);
			userQuery.whereEqualTo(ParseConstant.KEY_EMAIL, email);
			userQuery.getFirstInBackground(new GetCallback<ParseUser>() {
				@Override
				public void done(final ParseUser targetUser, ParseException e) {
					if (e == null) {

						// get Group from groupID that pass from previous intent
						ParseQuery<ParseObject> groupQuery = ParseQuery
								.getQuery(ParseConstant.TABLE_GROUPS);
						groupQuery.whereEqualTo(ParseConstant.KEY_OBJECT_ID,
								groupId);
						groupQuery.getInBackground(groupId,
								new GetCallback<ParseObject>() {

									@Override
									public void done(final ParseObject group,ParseException e) {
										if (e == null) {
											ParseObject groupRelation = new ParseObject(ParseConstant.TABLE_GROUP_RELATION);
											groupRelation.put(ParseConstant.KEY_GROUP_ID,group);
											groupRelation.put(ParseConstant.KEY_USER_ID,targetUser);
											groupRelation.put(ParseConstant.KEY_STATUS,false);
											groupRelation.saveInBackground(new SaveCallback() {

														@Override
														public void done(ParseException e) {
															if (e == null) {
																//create instalation query
																ParseQuery<ParseInstallation> pushQuery = ParseInstallation.getQuery();
																pushQuery.whereEqualTo(ParseConstant.KEY_USER_ID, targetUser);
																
																//send push notif to query
																ParsePush push = new ParsePush();
																push.setQuery(pushQuery);
//																String message = ParseUser.getCurrentUser().getString(ParseConstant.KEY_NAME).toString() + 
//																		"Have invite you to "+ group.getString(ParseConstant.KEY_NAME).toString();
//																
																push.setMessage(ParseUser.getCurrentUser().getUsername()+"invited you to group");
																
																push.sendInBackground();
																
																Toast.makeText(getApplicationContext(),"Invited "+targetUser.getString(ParseConstant.KEY_EMAIL),	Toast.LENGTH_SHORT).show();
																finish();
															}

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
}
