package com.udav.chesstimer;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingsFragments extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
	}
	
}