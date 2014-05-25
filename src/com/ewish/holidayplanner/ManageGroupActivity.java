package com.ewish.holidayplanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class ManageGroupActivity extends Activity {
	protected static final String TAG = ManageGroupActivity.class.getSimpleName();
	String objectId;
	ArrayList<HashMap<String, String>> membersList = new ArrayList<HashMap<String, String>>();
	ArrayList<String> objectsId = new ArrayList<String>();
	ListView mListMember;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_group);
		mListMember = (ListView) findViewById(R.id.listMember);
		
		Intent intent = getIntent();
		objectId = intent.getExtras().get(ParseConstant.KEY_OBJECT_ID).toString();
		Toast.makeText(this, "objectId"+objectId, Toast.LENGTH_LONG).show();
		
		
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstant.TABLE_GROUPS);
		query.getInBackground(objectId, new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject group, ParseException e) {
				if(e==null){
					ParseRelation<ParseUser> relation =  group.getRelation(ParseConstant.KEY_MEMBER_LIST);
					ParseQuery<ParseUser> query = relation.getQuery();
					query.findInBackground(new FindCallback<ParseUser>() {

						@Override
						public void done(List<ParseUser> members,ParseException e) {
							if(e==null){
								for (ParseUser member: members) {
									HashMap<String, String> map = new HashMap<String, String>();

									map.put(ParseConstant.KEY_NAME,member.getString(ParseConstant.KEY_NAME));
									map.put(ParseConstant.KEY_EMAIL,member.getEmail());
									membersList.add(map);
									objectsId.add(member.getObjectId());

								}
								String[] keys = { ParseConstant.KEY_NAME,ParseConstant.KEY_EMAIL };
								int[] ids = { android.R.id.text1, android.R.id.text2 };

								SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),
										membersList, android.R.layout.simple_list_item_2, keys,
										ids);
								mListMember.setAdapter(adapter);

								Toast.makeText(getBaseContext(), "Size: " + members.size(),
										Toast.LENGTH_LONG).show();
							}
						}
					});
					
					//Toast.makeText(getApplicationContext(), "listMember"+listMember.toString(), Toast.LENGTH_LONG).show();
				}
				 
				
			}
		});
		
		
		
	}
	
	public View inviteMember(View view){
		Intent intent = new Intent(this, InviteMemberActivity.class);
		intent.putExtra(ParseConstant.KEY_OBJECT_ID, objectId);
		startActivity(intent);
		return view;
	}
	
	
	
	
}
