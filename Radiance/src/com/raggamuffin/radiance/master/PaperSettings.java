// Author - Sinclair Ross.
// Date - 12/04/2014


package com.raggamuffin.radiance.master;

import com.raggamuffin.radiance.R;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class PaperSettings extends PreferenceActivity implements OnSharedPreferenceChangeListener
{
	// To provide the user with more consistent preferences when customising their wallpaper
	// Multipliers are used to keep values within an acceptable range.
	public static final float PARTICLE_DRAG_MULTIPLIER = 0.000001f;
	public static final float GRAVITON_PULL_MULTIPLIER = 0.001f;
	public static final float GRAVITON_SPEED_MULTIPLIER = 0.1f;
	public static final float GRAVITON_COLOUR_RADIUS_MULTIPLER = 0.1f;
	public static final float OBJECT_BINDING_MULTIPLIER = 0.1f;
	
	public static final float CAMERA_DRAG_MULTIPLIER = 0.001f;
	public static final float CAMERA_MASS_MULTIPLIER = 0.1f;
	public static final float CAMERA_RADIUS_MULTIPLIER = 0.1f;
	
	public static final String PARTICLE_COUNT	= "pref_Particle_Count";
	public static final String PARTICLE_COLOUR  = "pref_Particle_Colour";
	public static final String PARTICLE_DRAG 	= "pref_Particle_Drag";
	public static final String PARTICLE_SCALE	= "pref_Particle_Scale";
	public static final String PARTICLE_BINDING = "pref_Particle_Binding";

	public static final String GRAVITON_COLOUR_RADIUS = "pref_Graviton_Colour_Radius";
	public static final String GRAVITON_COLOUR 	= "pref_Graviton_Colour";
	public static final String GRAVITON_PULL 	= "pref_Graviton_Pull";
	public static final String GRAVITON_SPEED 	= "pref_Graviton_Speed";
	public static final String GRAVITON_BINDING	= "pref_Graviton_Binding";
	
	public static final String CAMERA_DRAG 		= "pref_Camera_Drag";
	public static final String CAMERA_MASS		= "pref_Camera_Mass";
	public static final String CAMERA_ORBIT_RADIUS  = "pref_Camera_Orbit_Radius";
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) 
	{
		
	}

	@Override 
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
		PreferenceManager.setDefaultValues(this, R.xml.wallpaper_settings, false);
	}
	
	// This function unpacks rgba values from a packed integer.
	public static float[] UnpackColour(int packedValue)
	{
		float[] UnpackedColour = new float[4];
		
		UnpackedColour[0] = (float)(packedValue >> 16 & 0xff) / 255;
		UnpackedColour[1] = (float)(packedValue >> 8 & 0xff) / 255;
		UnpackedColour[2] = (float)(packedValue & 0xff) / 255;
		UnpackedColour[3] = (float)(packedValue >> 24 & 0xff) / 255;

		return UnpackedColour;
	}

	
}
