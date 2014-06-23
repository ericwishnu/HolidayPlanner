package com.ewish.holidayplanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FreeScheduleActivity extends Activity {
	protected TextView mDateTxt;
	protected ListView mListView;
	ArrayAdapter<String> adapter;
	String dateString ;
	ArrayList<HashMap<String, String>> groupList = new ArrayList<HashMap<String, String>>();
	ArrayList<String> objectsId = new ArrayList<String>();
	HashMap<String, String> groupInfo = new HashMap<String, String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_free_schedule);
		declareLayout();
		Intent intent = getIntent();
		dateString = intent.getExtras().getString("date");
		
		
		mDateTxt = (TextView) findViewById(R.id.dateTxt);
		mDateTxt.setText(dateString);
	}
	@Override
	public void onResume() {
		super.onResume();
		loadGroups();
	}
	private void declareLayout() {
		mListView = (ListView) findViewById(R.id.listGroups2);
	}
	void loadGroups(){
		ParseUser user = ParseUser.getCurrentUser();
		ParseRelation<ParseObject> relation = user.getRelation(ParseConstant.KEY_GROUP_LIST);

		ParseQuery<ParseObject> query = relation.getQuery();
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> groups, ParseException arg1) {
				// Clear arraylist
				clearArrayList();
				for (ParseObject object : groups) {
					HashMap<String, String> group = new HashMap<String, String>();
					group.put(ParseConstant.KEY_NAME, object.getString(ParseConstant.KEY_NAME));
					group.put(ParseConstant.KEY_DESCRIPTION, object.getString(ParseConstant.KEY_DESCRIPTION));
					groupList.add(group);
					objectsId.add(object.getObjectId());

				}
				String[] keys = { ParseConstant.KEY_NAME,
						ParseConstant.KEY_DESCRIPTION };
				int[] ids = { android.R.id.text1, android.R.id.text2 };	

				SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), groupList, android.R.layout.simple_list_item_2, keys, ids);
				mListView.setAdapter(adapter);

				Toast.makeText(getApplicationContext(), "Size: " + groups.size(), Toast.LENGTH_SHORT).show();
			}

			private void clearArrayList() {
				groupList.clear();
				groupInfo.clear();
			}
		});
		mListView.setOnItemClickListener(groupListener);
	}
	
	OnItemClickListener groupListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String currentObjectID = objectsId.get(position);
			// Intent 
			Intent intent = new Intent(getApplicationContext(), CreateEventActivity.class);
			intent.putExtra("objectId", currentObjectID);
			intent.putExtra("date", dateString);
			
			startActivity(intent);
			
		}
	};
	
}
