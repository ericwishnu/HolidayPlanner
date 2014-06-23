package com.ewish.holidayplanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ManageGroupActivity extends Activity {
	protected static final String TAG = ManageGroupActivity.class
			.getSimpleName();
	String objectId;
	ArrayList<HashMap<String, String>> membersList = new ArrayList<HashMap<String, String>>();
	ArrayList<String> objectsId = new ArrayList<String>();
	ListView mListMember;
	protected Button mLeaveGroupButton;
	private ParseUser currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_group);
		mListMember = (ListView) findViewById(R.id.listMember);
		mLeaveGroupButton = (Button) findViewById(R.id.leaveGroupButton);
		currentUser = ParseUser.getCurrentUser();
		Intent intent = getIntent();
		objectId = intent.getExtras().get(ParseConstant.KEY_OBJECT_ID)
				.toString();
		Toast.makeText(this, "objectId" + objectId, Toast.LENGTH_LONG).show();

		mLeaveGroupButton.setOnClickListener(leaveOnClick);

		ParseQuery<ParseObject> query = ParseQuery
				.getQuery(ParseConstant.TABLE_GROUPS);

		query.getInBackground(objectId, new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject group, ParseException e) {
				if (e == null) {
					ParseRelation<ParseUser> relation = group
							.getRelation(ParseConstant.KEY_MEMBER_LIST);
					ParseQuery<ParseUser> query = relation.getQuery();
					query.findInBackground(new FindCallback<ParseUser>() {

						@Override
						public void done(List<ParseUser> members, ParseException e) {
							if (e == null) {
								for (ParseUser member : members) {
									HashMap<String, String> map = new HashMap<String, String>();

									map.put(ParseConstant.KEY_NAME, member.getString(ParseConstant.KEY_NAME));
									map.put(ParseConstant.KEY_EMAIL, member.getEmail());
									membersList.add(map);
									objectsId.add(member.getObjectId());

								}
								String[] keys = { ParseConstant.KEY_NAME,
										ParseConstant.KEY_EMAIL };
								int[] ids = { android.R.id.text1,
										android.R.id.text2 };

								SimpleAdapter adapter = new SimpleAdapter(
										getBaseContext(), membersList,
										android.R.layout.simple_list_item_2,
										keys, ids);
								mListMember.setAdapter(adapter);

								Toast.makeText(getBaseContext(),
										"Size: " + members.size(),
										Toast.LENGTH_LONG).show();
							}
						}
					});

				}

			}
		});

	}

	OnClickListener leaveOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			new AlertDialog.Builder(ManageGroupActivity.this)
					.setTitle("Leave")
					.setMessage("Do you really want to leave from this group?")
					
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int whichButton) {

									ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstant.TABLE_GROUPS);

									query.getInBackground(objectId, new GetCallback<ParseObject>() {

												@Override
												public void done(final ParseObject group,ParseException e) {
													if (e == null) {
														ParseUser moderator = (ParseUser) group.get(ParseConstant.KEY_MODERATOR_ID);
														// IF MODERATOR
														if (moderator.hasSameId(ParseUser.getCurrentUser())) {
															Log.d(TAG,"MODERATOR");
															//ParseQuery<ParseUser> listMember = ParseQuery.getQuery(ParseConstant.TABLE_GROUPS);


															//List<ParseUser> groupMembers = group.getList(ParseConstant.KEY_MEMBER_LIST);
															//Log.d(TAG, "GROUP SIZE: "+ groupMembers.size());
															//
															ParseQuery<ParseObject> groupRelationQuery = ParseQuery.getQuery(ParseConstant.TABLE_GROUP_RELATION);
															groupRelationQuery.whereEqualTo(ParseConstant.KEY_GROUP_ID, group);
															groupRelationQuery.findInBackground(new FindCallback<ParseObject>() {

																@Override
																public void done(List<ParseObject> groupRelations, ParseException e) {
																	ParseObject.deleteAllInBackground(groupRelations, new DeleteCallback() {
																		
																		@Override
																		public void done(ParseException e) {
																			if(e==null){
																				group.deleteInBackground(new DeleteCallback() {
																					
																					@Override
																					public void done(ParseException e) {
																						if(e==null){
																							finish();
																							Toast.makeText(getApplicationContext(), "Group Deleted", Toast.LENGTH_SHORT).show();
																						}
																						
																					}
																				});
																			}
																			
																		}
																	});
																	
																}
															});
															
															
																													
														} else {
															// IF NOT MODERATOR
															Log.d(TAG, "NOT MODERATOR");
															// remove user from
															// group table
															ParseRelation<ParseUser> listMember = group.getRelation(ParseConstant.KEY_MEMBER_LIST);
															listMember.remove(ParseUser.getCurrentUser());
															group.saveInBackground(new SaveCallback() {

																@Override
																public void done(ParseException e) {
																	if (e == null) {
																		// remove
																		// group
																		// from
																		// user
																		// object
																		
																		ParseQuery<ParseObject> groupRelation = ParseQuery.getQuery(ParseConstant.TABLE_GROUP_RELATION);
																		groupRelation.whereEqualTo(ParseConstant.KEY_GROUP_ID, group);
																		groupRelation.whereEqualTo(ParseConstant.KEY_USER_ID, currentUser);
																		groupRelation.getFirstInBackground(new GetCallback<ParseObject>() {
																			
																			@Override
																			public void done(ParseObject groupRel, ParseException e) {
																				if(e==null){
																					groupRel.deleteEventually();
																					ParseRelation<ParseObject> relationGroupList = currentUser.getRelation(ParseConstant.KEY_GROUP_LIST);
																					relationGroupList.remove(group);
																					currentUser.saveInBackground(new SaveCallback() {

																								@Override
																								public void done(
																										ParseException e) {
																									if (e == null) {
																										Toast.makeText(
																												getApplicationContext(),
																												"Group Leaved",
																												Toast.LENGTH_SHORT)
																												.show();
																									} else {
																										loggingException(e);
																									}
																								}
																							});
																				}else{
																					loggingException(e);
																				}
																				
																			}
																		});
																		
																
																	} else {
																		loggingException(e);
																	}

																}
															});
														}
													}

												}

											});

								}
							}).setNegativeButton(android.R.string.no, null).show();

		}
	};

	public View inviteMember(View view) {
		Intent intent = new Intent(this, InviteMemberActivity.class);
		intent.putExtra(ParseConstant.KEY_OBJECT_ID, objectId);
		startActivity(intent);
		return view;
	}

	public void loggingException(ParseException e) {
		Log.e(TAG, "Exception caught! :" + e.getMessage(), e);
	}

}
