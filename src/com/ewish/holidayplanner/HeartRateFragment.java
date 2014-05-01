package com.ewish.holidayplanner;

import com.ewish.holidayplanner.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HeartRateFragment extends Fragment {
	
	public HeartRateFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_heart_rate, container, false);
         
        return rootView;
    }
}
