package com.udav.chesstimer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SettingsActivity extends Activity implements OnClickListener {
	
	private Spinner hourSpinner;
	private Spinner minSpinner;
	private Spinner secSpinner;
	private ArrayAdapter<Integer> hourAdapter, minAdapter, secAdapter;
	
	private Button buttonSave;
	private Button buttonCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		buttonSave = (Button)findViewById(R.id.buttonSave);
		buttonCancel = (Button)findViewById(R.id.buttonCancel);
		buttonSave.setOnClickListener(this);
		buttonCancel.setOnClickListener(this);
		
		hourSpinner = (Spinner)findViewById(R.id.hourSpinner);
		minSpinner = (Spinner)findViewById(R.id.minSpinner);
		secSpinner = (Spinner)findViewById(R.id.secSpinner);
		
		ArrayList<Integer> hourList = new ArrayList<Integer>();
		for (int i=0; i <= 10; i++){
			hourList.add(i);
		}
		hourAdapter = new ArrayAdapter<Integer>(getBaseContext(), android.R.layout.simple_spinner_item, hourList);
		hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		hourSpinner.setAdapter(hourAdapter);
		
		ArrayList<Integer> minSecList = new ArrayList<Integer>();
		for (int i=0; i <= 60; i++){
			minSecList.add(i);
		}
		minAdapter = new ArrayAdapter<Integer>(getBaseContext(), android.R.layout.simple_spinner_item, minSecList);
		minAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		minSpinner.setAdapter(minAdapter);
		
		secAdapter = new ArrayAdapter<Integer>(getBaseContext(), android.R.layout.simple_spinner_item, minSecList);
		secAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		secSpinner.setAdapter(secAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonSave :
				long milliseconds = hourAdapter.getItem(hourSpinner.getSelectedItemPosition())*60*60*1000+
				minAdapter.getItem(minSpinner.getSelectedItemPosition())*60*1000+
				secAdapter.getItem(secSpinner.getSelectedItemPosition())*1000;
				
				Intent intent = new Intent();
				Bundle extras = new Bundle();
				extras.putLong(MainActivity.SET_TIME_KEY, milliseconds);
				intent.putExtras(extras);
				setResult(RESULT_OK, intent);
				finish();
				break;
			case R.id.buttonCancel:
				setResult(RESULT_CANCELED);
				finish();
		}
		
	}
	
	

}
