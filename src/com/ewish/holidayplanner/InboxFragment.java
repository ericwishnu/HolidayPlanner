package com.ewish.holidayplanner;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ewish.holidayplanner.adapter.InboxArrayAdapter;
import com.ewish.holidayplanner.model.Inbox;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class InboxFragment extends Fragment {
	protected final static String TAG = InboxFragment.class.getSimpleName();
	Context context;
	ListView mInboxList;
	ArrayList<Inbox> inboxes;
	ArrayList<String> inboxObjId;
	private InboxArrayAdapter mAdapter;
	public InboxFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_inbox, container,
				false);
		context = rootView.getContext();
		mInboxList = (ListView) rootView.findViewById(R.id.listInbox);
		inboxObjId = new ArrayList<String>();
		inboxes = new ArrayList<Inbox>();
		return rootView;
	}

	@Override
	public void onResume() {	
		super.onResume();
		inboxObjId.clear();
		inboxes.clear();
		Log.d(TAG, "OnResume");
		//Get inbox from GroupRelation Table
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstant.TABLE_GROUP_RELATION);
		query.whereEqualTo(ParseConstant.KEY_USER_ID,ParseUser.getCurrentUser());
		query.whereEqualTo(ParseConstant.KEY_STATUS, false);
		query.include(ParseConstant.KEY_GROUP_ID);
		query.include(ParseConstant.KEY_USER_ID);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(final List<ParseObject> inbox, ParseException e) {
				if (e == null) {
					//ArrayList<Inbox> inboxTemp = new ArrayList<Inbox>();
					
					for(int i = 0 ; i< inbox.size();i++){
						inboxObjId.add(inbox.get(i).getObjectId());
						ParseObject groupT = inbox.get(i).getParseObject(ParseConstant.KEY_GROUP_ID);
						ParseUser userT = inbox.get(i).getParseUser(ParseConstant.KEY_USER_ID);
						Boolean statusT= inbox.get(i).getBoolean(ParseConstant.KEY_STATUS);
						
						//Log.d(TAG, userT.getString("name")+" | "+groupT.getString("name"));
						Inbox temp = new Inbox(groupT, userT, statusT);
						inboxes.add(temp);
						
						//inboxTemp
					}
					//inboxes = inboxTemp;
					mAdapter= new InboxArrayAdapter(context, R.id.listInbox, inboxes);
					mInboxList.setAdapter(mAdapter);
					mInboxList.setOnItemClickListener(inboxClicked);
				}

			}
		});
		
		
		
	}
	OnItemClickListener inboxClicked = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(context, ApproveInvitationActivity.class);
			
			Log.d(TAG, "objId size: "+ inboxObjId.size());
			intent.putExtra(ParseConstant.KEY_OBJECT_ID, inboxObjId.get(position));
			intent.putExtra("title", inboxes.get(position).getGroup().getString(ParseConstant.KEY_NAME) );
			intent.putExtra("content", inboxes.get(position).getGroup().getString(ParseConstant.KEY_NAME) + " invite you to group.");
			startActivity(intent);
			
		}

	
		
	};


}
