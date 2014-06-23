package com.ewish.holidayplanner.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ewish.holidayplanner.R;
import com.ewish.holidayplanner.model.UserAvailability;

public class AvailabilityArrayAdapter extends ArrayAdapter<UserAvailability> {
	protected static final String TAG = AvailabilityArrayAdapter.class.getSimpleName();
	Context context;

	public AvailabilityArrayAdapter(Context context, int textViewResourceId,
			List<UserAvailability> items) {
		super(context, textViewResourceId, items);
		this.context = context;
	}

	/* private view holder class */
	private class ViewHolder {
		TextView mTitle;
		TextView mContent;
		ImageView mImage;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		UserAvailability userAvailability = (UserAvailability) getItem(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_availability, null);
			holder = new ViewHolder();
			holder.mTitle = (TextView) convertView.findViewById(R.id.titleField);
			holder.mContent = (TextView) convertView.findViewById(R.id.contentField);
			holder.mImage = (ImageView) convertView.findViewById(R.id.imageStatus); 
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		// show the data from database
		//Log.d(TAG, userAvailability.getGroup().getString("name"));
		holder.mTitle.setText(userAvailability.getName());
		holder.mContent.setText(userAvailability.getEmail());
		holder.mImage.setImageResource(userAvailability.getImgUrl());
		return convertView;
	}
}