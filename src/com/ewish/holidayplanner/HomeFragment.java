package com.ewish.holidayplanner;

import java.util.Locale;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class HomeFragment extends Fragment {
protected final static String TAG = HomeFragment.class.getSimpleName();
	private Context context;

	View rootView;
	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		context=rootView.getContext();
		Locale.setDefault(Locale.US);


		return rootView;
	}


	
}
