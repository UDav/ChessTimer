package com.udav.chesstimer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class SettingsActivity extends Activity implements OnClickListener {
	
	private EditText editTextSetTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		editTextSetTime = (EditText)findViewById(R.id.editTextSetTime);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonSave :
				
				break;
		
		}
		
	}
	
	

}
