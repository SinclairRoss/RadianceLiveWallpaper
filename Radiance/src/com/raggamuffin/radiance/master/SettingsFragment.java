// Author - Sinclair Ross.
// Date - 12/04/2014

package com.raggamuffin.radiance.master;

import com.raggamuffin.radiance.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.wallpaper_settings);
	}
}

