package com.ewish.holidayplanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class GroupFragment extends Fragment {
	private Context context;
	protected Button mCreateGroupBtn;
	protected ListView mListView;
	ArrayAdapter<String> adapter;
	ArrayList<HashMap<String, String>> groupList = new ArrayList<HashMap<String, String>>();
	ArrayList<String> objectsId = new ArrayList<String>();
	HashMap<String, String> groupInfo = new HashMap<String, String>();
	
	
	

	public GroupFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_group,
				container, false);
		context = rootView.getContext();
		declareLayout(rootView);
		mCreateGroupBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createGroup();
			}
		});

		return rootView;
	}

	private void declareLayout(final View rootView) {
		mCreateGroupBtn = (Button) rootView.findViewById(R.id.createGroupBtn);
		mListView = (ListView) rootView.findViewById(R.id.listGroup);
	}

	@Override
	public void onResume() {
		super.onResume();
		getGroup();
	}

	public void createGroup() {
		Intent intent = new Intent(context, AddGroupActivity.class);
		startActivityForResult(intent, RequestConstant.REQUEST_ADD_GROUP);
	}

	public void getGroup() {
		ParseUser user = ParseUser.getCurrentUser();
		ParseRelation<ParseObject> relation = user
				.getRelation(ParseConstant.KEY_GROUP_LIST);

		ParseQuery<ParseObject> query = relation.getQuery();
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> groups, ParseException arg1) {
				// Clear arraylist
				clearArrayList();
				for (ParseObject object : groups) {
					HashMap<String, String> group = new HashMap<String, String>();

					group.put(ParseConstant.KEY_NAME,
							object.getString(ParseConstant.KEY_NAME));
					group.put(ParseConstant.KEY_DESCRIPTION,
							object.getString(ParseConstant.KEY_DESCRIPTION));
					groupList.add(group);

					objectsId.add(object.getObjectId());

				}
				String[] keys = { ParseConstant.KEY_NAME,
						ParseConstant.KEY_DESCRIPTION };
				int[] ids = { android.R.id.text1, android.R.id.text2 };

				SimpleAdapter adapter = new SimpleAdapter(getActivity(),
						groupList, android.R.layout.simple_list_item_2, keys,
						ids);
				mListView.setAdapter(adapter);

				Toast.makeText(context, "Size: " + groups.size(),
						Toast.LENGTH_LONG).show();
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
			Intent intent = new Intent(getActivity(), ManageGroupActivity.class);
			intent.putExtra("objectId", currentObjectID);
			startActivity(intent);
			
		}
	};

}
