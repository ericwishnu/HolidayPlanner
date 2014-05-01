package com.ewish.holidayplanner;

import java.text.DecimalFormat;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalCoachFragment extends Fragment implements OnClickListener {

	private Context context;
	private String input = "";
	public PersonalCoach personal;
	public PersonalCoachFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		final View rootView = inflater.inflate(R.layout.fragment_personal_coach,
				container, false);
		context = rootView.getContext();

		final TextView lblWeightPersonalInfo = (TextView) rootView.findViewById(R.id.lblWeightContent);
		final TextView lblHeightPersonalInfo = (TextView) rootView.findViewById(R.id.lblHeightContent);
		// Put listener on
		// btnEditPersonalInfo
		ImageButton btnEditPersonalInfo = (ImageButton) rootView	.findViewById(R.id.btnEditPersonalInfo);
		btnEditPersonalInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.prompts, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				final EditText weightInput = (EditText) promptsView.findViewById(R.id.txtWeightInput);
				final EditText heightInput = (EditText) promptsView.findViewById(R.id.txtHeightInput);
				
				final TextView mBMI = (TextView) rootView.findViewById(R.id.labelBMIContent);
				final TextView mBMIString = (TextView) rootView.findViewById(R.id.labelBMIString);
				// set dialog message
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								
								// get user input and set it to result
								// edit text
								lblWeightPersonalInfo.setText(weightInput.getText().toString());
								lblHeightPersonalInfo.setText(heightInput.getText().toString());
								//Store data to object
								double weight = Double.parseDouble(weightInput.getText().toString());
								double height = Double.parseDouble(heightInput.getText().toString());
								
								personal= new PersonalCoach(weight,height);
								double bmi = personal.getBMI();
								DecimalFormat f = new DecimalFormat("##.0");
							     
								Toast.makeText(context, "BMI"+f.format(bmi), Toast.LENGTH_LONG).show();
								
								mBMI.setText(f.format(bmi));
								mBMIString.setText(personal.getBMIString(bmi));
								mBMIString.setTextColor(personal.getBMIColor(bmi));
							}
						})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			}
		});
		

		return rootView;
	}

	@Override
	public void onClick(View v) {

	}


}
