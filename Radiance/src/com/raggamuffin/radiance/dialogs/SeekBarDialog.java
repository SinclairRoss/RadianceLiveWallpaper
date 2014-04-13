// Author - Sinclair Ross.
// Date - 12/04/2014

// This class is a dialog containing a seekbar that allows the user to change the value of one preferences.
package com.raggamuffin.radiance.dialogs;

import com.raggamuffin.radiance.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SeekBarDialog extends DialogPreference
{
	private String PreferenceName = "";
	private int SeekBarMin = 0;
	private int SeekBarMax = 0;
	private String Note = "";
	
	private int m_Value = 0;
	private int m_Default = 0;
	
	private static SeekBar m_SeekBar;
	private static TextView m_TxtValue;
	private static TextView m_TxtNote;
	
	private static SharedPreferences m_Preferences;
	
	OnSeekBarChangeListener SeekBarListener = new OnSeekBarChangeListener()
	{
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) 
		{
			m_Value = progress + SeekBarMin;
			m_TxtValue.setText(Integer.toString(m_Value));
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
	
	@Override 
	public View onCreateDialogView()
	{
		setDialogLayoutResource(R.layout.seekbardialog_layout);
		
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.seekbardialog_layout, null);
	
		m_SeekBar = (SeekBar)view.findViewById(R.id.seekBar_SeekDialog);		// Gets the seekbar from the seekbar dialog layout so that it can be accessed in this class.
		m_TxtValue = (TextView)view.findViewById(R.id.textValue_SeekDialog); 	// Gets the TextView containg the value of this preference from the seekbar dialog layout so that it can be accessed in this class.
		m_TxtNote =(TextView)view.findViewById(R.id.textNote_SeekDialog);		// Gets the TextView containing the note from the seekbar dialog layout so that it can be accessed in this class.
		
		m_Preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
		m_Value = m_Preferences.getInt(PreferenceName, m_Default);
		
		// Sets the maximum value of the seek bar.
		m_SeekBar.setMax(SeekBarMax);
		
		// Sets the progress of the seekbar to the current value.
		m_SeekBar.setProgress(m_Value);
		m_TxtValue.setText(Integer.toString(m_Value));
		m_TxtNote.setText(Note);
		
		// Sets a listener that detects changes to the seekbar.
		m_SeekBar.setOnSeekBarChangeListener(SeekBarListener);

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
			SharedPreferences.Editor editor = m_Preferences.edit();
			editor.putInt(PreferenceName,m_Value);
			editor.commit();
		}
	}
	
	public SeekBarDialog(Context context, AttributeSet attrs) 
	{
		// Using custom xml attributes the preferences that this dialog affects can be set in xml.
		// aswell as other attributes such as the default position of the seek bars.
		
		super(context, attrs);
		
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.SeekBarDialog);
		
		final int N = a.getIndexCount();
		for(int i = 0; i < N; i++)
		{
			int attr = a.getIndex(i);
			switch(attr)
			{
				case R.styleable.SeekBarDialog_PreferenceName:
					PreferenceName = a.getString(i);
					break;
					
				case R.styleable.SeekBarDialog_SeekBarMin:
					SeekBarMin = a.getInt(i, -1);
					break;
				
				case R.styleable.SeekBarDialog_SeekBarMax:
					SeekBarMax = a.getInt(i, -1);
					break;
				
				case R.styleable.SeekBarDialog_Note:
					Note = a.getString(i);
					break;
				
				case R.styleable.SeekBarDialog_Default:
					m_Default = a.getInt(i,-1);
					break;
			}
		}

		SeekBarMax -= SeekBarMin;
		
		a.recycle();
	}
}
