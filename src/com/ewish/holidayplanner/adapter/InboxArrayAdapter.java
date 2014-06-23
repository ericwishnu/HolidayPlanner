package com.ewish.holidayplanner.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ewish.holidayplanner.ParseConstant;
import com.ewish.holidayplanner.R;
import com.ewish.holidayplanner.model.Inbox;

public class InboxArrayAdapter extends ArrayAdapter<Inbox> {
	protected static final String TAG = InboxArrayAdapter.class.getSimpleName();
	Context context;

	public InboxArrayAdapter(Context context, int textViewResourceId,
			List<Inbox> items) {
		super(context, textViewResourceId, items);
		this.context = context;
	}

	/* private view holder class */
	private class ViewHolder {
		TextView mTitle;
		TextView mContent;

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Inbox inbox = (Inbox) getItem(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_inbox, null);
			holder = new ViewHolder();
			holder.mTitle = (TextView) convertView.findViewById(R.id.titleField);
			holder.mContent = (TextView) convertView.findViewById(R.id.contentField);

			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		// show the data from database
		Log.d(TAG, inbox.getGroup().getString("name"));
		
		holder.mTitle.setText(inbox.getGroup().getString(ParseConstant.KEY_NAME));
		holder.mContent.setText(inbox.getGroup().getString(ParseConstant.KEY_NAME) + " invite you to group.");
		return convertView;
	}
}