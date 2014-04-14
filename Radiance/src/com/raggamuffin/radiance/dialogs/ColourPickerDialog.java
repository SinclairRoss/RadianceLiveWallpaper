// Author - Sinclair Ross.
// Date - 12/04/2014

// This class is a dialog that allows the user to pick a colour using sliders
// and updates the shared preferences of the app by bit packing the rgba values into one integer

package com.raggamuffin.radiance.dialogs;

import com.raggamuffin.radiance.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ColourPickerDialog extends DialogPreference
{
	private String PreferenceName = "";
	
	private int m_Default;
	private int m_PackedValue;
	private int m_RedValue;
	private int m_BlueValue;
	private int m_GreenValue;
	private int m_AlphaValue;
	
	private static final int m_SeekBarMax = 255; 
	
	private static TextView m_TxtSampleBox;
	
	private static SeekBar m_RedSeekBar;
	private static SeekBar m_GreenSeekBar;
	private static SeekBar m_BlueSeekBar;
	private static SeekBar m_AlphaSeekBar;
	
	private static TextView m_TxtRedValue;
	private static TextView m_TxtGreenValue;
	private static TextView m_TxtBlueValue;
	private static TextView m_TxtAlphaValue;

	private static SharedPreferences m_Preferences;
	
	OnSeekBarChangeListener SeekBarListener = new OnSeekBarChangeListener()
	{
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) 
		{
			if(seekBar == m_RedSeekBar)
			{
				m_RedValue = progress;
				m_TxtRedValue.setText(Integer.toString(m_RedValue));
			}
			
			if(seekBar == m_GreenSeekBar)
			{
				m_GreenValue = progress;
				m_TxtGreenValue.setText(Integer.toString(m_GreenValue));
			}
			
			if(seekBar == m_BlueSeekBar)
			{
				m_BlueValue = progress;
				m_TxtBlueValue.setText(Integer.toString(m_BlueValue));
			}
			
			if(seekBar == m_AlphaSeekBar)
			{
				m_AlphaValue = progress;
				m_TxtAlphaValue.setText(Integer.toString(m_AlphaValue));
			}
			
			PackPreference();
			
			// converts the packed integer into hex.
			String Hex = Integer.toHexString(m_PackedValue);
			Hex = String.format("%08X",m_PackedValue & 0xFFFFFFFF);
			m_TxtSampleBox.setBackgroundColor(Color.parseColor("#" + Hex));
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) 
		{
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) 
		{

		}
	};
	
	public ColourPickerDialog(Context context, AttributeSet attrs) 
	{
		// Using custom xml attributes the preferences that this dialog affects can be set in xml.
		// aswell as other attributes such as the default position of the seek bars.
		super(context, attrs);
		
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.SeekBarDialog);
		
		final int N = a.getIndexCount();
		Log.e("Shit","N: " + Integer.toString(N));
		for(int i = 0; i < N; i++)
		{
			int attr = a.getIndex(i);
			Log.e("Shit","Index: " + Integer.toString(attr));
			switch(attr)
			{
				case R.styleable.SeekBarDialog_PreferenceName:
					PreferenceName = a.getString(i);
					break;
					
				case R.styleable.SeekBarDialog_Default:
					m_Default = a.getInt(i, -1);
					Log.e("Shit","Default: " + Integer.toString(m_Default));
					break;
			}
		}
		a.recycle();
	}
	
	@Override 
	public View onCreateDialogView()
	{
		setDialogLayoutResource(R.layout.colourpickerdialog_layout);
		
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.colourpickerdialog_layout, null);
		
		// Gets the seekbars and textview showing the colour the user has picked from 
		// the colour picker dialog layout and allows me to alter them programmatically.
		m_TxtSampleBox = (TextView)view.findViewById(R.id.colourSample_ColourPickerDialog);
		m_RedSeekBar = (SeekBar)view.findViewById(R.id.redSeekBar_ColourPickerDialog);
		m_GreenSeekBar = (SeekBar)view.findViewById(R.id.greenSeekBar_ColourPickerDialog);
		m_BlueSeekBar = (SeekBar)view.findViewById(R.id.blueSeekBar_ColourPickerDialog);
		m_AlphaSeekBar = (SeekBar)view.findViewById(R.id.alphaSeekBar_ColourPickerDialog);
		
		// Sets the maximum values for the seek bar.
		m_RedSeekBar.setMax(m_SeekBarMax);
		m_GreenSeekBar.setMax(m_SeekBarMax);
		m_BlueSeekBar.setMax(m_SeekBarMax);
		m_AlphaSeekBar.setMax(m_SeekBarMax);
		
		// Gets the textviews from the colour picker dialog layout and allows me to
		// alter them programmatically.
		m_TxtRedValue = (TextView)view.findViewById(R.id.redTextValue_ColourPickerDialog);
		m_TxtGreenValue = (TextView)view.findViewById(R.id.greenTextValue_ColourPickerDialog);
		m_TxtBlueValue = (TextView)view.findViewById(R.id.blueTextValue_ColourPickerDialog);
		m_TxtAlphaValue = (TextView)view.findViewById(R.id.alphaTextValue_ColourPickerDialog);

		// gets the appropriate value from the preferences and unpacks it.
		m_Preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
		UnpackPreference();
		
		// Puts the packed value into hex format and sets the colour of the sample box to that colour.
		PackPreference();
		String Hex = Integer.toHexString(m_PackedValue);
		Hex = String.format("%08X",m_PackedValue & 0xFFFFFFFF);
		m_TxtSampleBox.setBackgroundColor(Color.parseColor("#" + Hex));
		
		// Sets the text representing the intensity of rgba values
		m_TxtRedValue.setText(Integer.toString(m_RedValue));
		m_TxtGreenValue.setText(Integer.toString(m_GreenValue));
		m_TxtBlueValue.setText(Integer.toString(m_BlueValue));
		m_TxtAlphaValue.setText(Integer.toString(m_AlphaValue));
		
		// Sets the value of the seek bars to their appropriate positions.
		m_RedSeekBar.setProgress(m_RedValue);
		m_GreenSeekBar.setProgress(m_GreenValue);
		m_BlueSeekBar.setProgress(m_BlueValue);
		m_AlphaSeekBar.setProgress(m_AlphaValue);
		
		// Sets listeners for changes to the seekbar.
		m_RedSeekBar.setOnSeekBarChangeListener(SeekBarListener);
		m_GreenSeekBar.setOnSeekBarChangeListener(SeekBarListener);
		m_BlueSeekBar.setOnSeekBarChangeListener(SeekBarListener);
		m_AlphaSeekBar.setOnSeekBarChangeListener(SeekBarListener);
		
		return view;
	}
	
	@Override
	protected void onDialogClosed(boolean Result)
	{
		// If the dialog closes using the ok button
		// save the preferences.
		// else
		// do not save preferences.
		if(Result)
		{
			PackPreference();
			
			SharedPreferences.Editor editor = m_Preferences.edit();
			editor.putInt(PreferenceName, m_PackedValue);
			editor.commit();
		}
	}
	
	// puts the rgba values into a packed int for easy transport.
	private void PackPreference()
	{
		m_PackedValue = 0;

		m_PackedValue += m_AlphaValue << 24;
		m_PackedValue += m_RedValue   << 16;
		m_PackedValue += m_GreenValue << 8;
		m_PackedValue += m_BlueValue;
	}
	
	
	// Unpacks rgba values from the preferences so the sliders can easily be set to 
	// their proper positions.
	private void UnpackPreference()
	{
		int packedValue = m_Preferences.getInt(PreferenceName, m_Default);
		 
		Log.e("Shit",Integer.toString(m_PackedValue));
		Log.e("Shit",Integer.toString(m_Default));
		
		m_AlphaValue 	= packedValue >> 24 & 0xff;
		m_RedValue 		= packedValue >> 16 & 0xff;
		m_GreenValue 	= packedValue >> 8 & 0xff;
		m_BlueValue 	= packedValue & 0xff;
	}
	
}
