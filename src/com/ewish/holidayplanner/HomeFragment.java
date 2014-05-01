package com.ewish.holidayplanner;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

public class HomeFragment extends Fragment {
	
	private Context context;
	private NumberPicker mPickerHeight;
	private NumberPicker mPickerWeight;

	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        context = rootView.getContext();
        
        
        
		
        return rootView;
    }
}
