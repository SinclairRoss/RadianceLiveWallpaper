// Author - Sinclair Ross.
// Date - 12/04/2014

package com.raggamuffin.radiance.master;

import com.raggamuffin.radiance.logic.LogicMaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

public class MasterThread extends Thread
{
	public static String TAG = "GameThread";
	
	private LogicMaster m_Logic;
	private boolean m_Running;
	private boolean m_Paused;
	private Handler m_Handler;
	
	private Long m_StartTime;
	private Long m_EndTime;
	
	private Message m_Message;
	private Bundle m_InputBundle;
	
	private static SharedPreferences m_Preferences;

	
	// If the shared preferences has been changed this function resets the wallpaper and re creates objects with their new settings.
	OnSharedPreferenceChangeListener SPChanged = new OnSharedPreferenceChangeListener()
	{
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) 
		{
			if(m_Logic != null)
				m_Logic.SpawnGameObjects();
		}
	};

	public MasterThread(RendererPacket packet, Handler handler, Context context)
	{
		super();
		Log.e(TAG,"GameThread");

		m_Logic = new LogicMaster(packet, context);
		
		m_Running = true;
		m_Handler = handler;
		
		m_StartTime = System.currentTimeMillis();
		m_EndTime = 0L;
		
		m_InputBundle = new Bundle();
		
		m_Preferences = PreferenceManager.getDefaultSharedPreferences(context);
		m_Preferences.registerOnSharedPreferenceChangeListener(SPChanged);
	}

	@Override 
	public void run()
	{
		while(m_Running)
		{
			// Calculates the time between the last update call and this one.
			m_EndTime = m_StartTime;
			m_StartTime = System.currentTimeMillis();

			if(!m_Paused)
			{
				m_Logic.Update((m_StartTime - m_EndTime), m_InputBundle);
				
				// Sends a message requesting a render as the game logic has just been updated.
				m_Handler.sendMessage(ComposeMessage(GLWallpaper.REQUEST_RENDER));
			}

		}
		
		m_Preferences.unregisterOnSharedPreferenceChangeListener(SPChanged);
	}
	
	public Message ComposeMessage(int what)
	{
		m_Message = Message.obtain();
		m_Message.what = what;
	
		return m_Message;
	}
	
	public synchronized void SetInputBundle(Bundle bundle)
	{
		m_InputBundle = bundle;
	}
	
	public void PauseThread()
	{
		Log.e(TAG,"PauseThread");
		m_Paused = true;
	}
	
	public void ResumeThread()
	{
		Log.e(TAG,"ResumeThread");
		m_Paused = false;
	}
	
	public void StopRunning()
	{
		m_Running = false;
	}
}
