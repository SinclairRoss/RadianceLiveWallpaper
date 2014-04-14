// Author - Sinclair Ross.
// Date - 12/04/2014


package com.raggamuffin.radiance.master;

import com.raggamuffin.radiance.renderer.GLRenderer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class GLWallpaper extends GLWallpaperService
{
	public static int REQUEST_RENDER = 0;
	public static int REQUEST_INPUT = 1;

	public Context m_Context;

	public GLWallpaper()
	{
		super(); 
	}
	
	@Override
	public Engine onCreateEngine() 
	{
		return new WallPaperEngine();
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		m_Context = this;
	}

	@Override 
	public void onDestroy()
	{
		super.onDestroy();
	}

	private class WallPaperEngine extends GLEngine
	{
		private GLRenderer m_Renderer;
		private MasterThread m_MasterThread;
		private RendererPacket m_RendererPacket;
		private Handler m_Handler;
		private GestureDetector m_GestureDetector;
		private Bundle m_InputBundle;

		public WallPaperEngine()
		{	
			super();

			m_InputBundle = new Bundle();
		}
		
		@Override
		public void onCreate(SurfaceHolder surfaceHolder) 
		{
			super.onCreate(surfaceHolder);
			
			// Defines the gesture listener.
			OnGestureListener GestureMethod = new OnGestureListener() 
		     {
		     	public boolean onDown(MotionEvent e) 
		     	{
		     		return false;
		     	}
		     	
		     	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) 
		     	{
		     		// This helps prevent accidental flings from being registered.
		     		if(velocityX > 1000)
		     		{	     			
		     			
		     			// Places the velocity of the fling into a bundle to be sent to the master thread.
		     			m_InputBundle.putFloat("FlingVelocity", velocityX);
						m_MasterThread.SetInputBundle(m_InputBundle);		
	
		     			return true;
		     		}
		     		else if(velocityX < -1000)
		     		{
		     			m_InputBundle.putFloat("FlingVelocity", velocityX);
						m_MasterThread.SetInputBundle(m_InputBundle);		
						
		     			return true;
		     		}	
		     		return false;
		     	}
		     	
		     	public void onLongPress(MotionEvent e)
		     	{
		     		
		     	}
		     	
		     	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
		     	{
		     		return false;
		     	}
		     	
		     	public void onShowPress(MotionEvent e) 
		     	{
		     		
		     	}
		     
		     	public boolean onSingleTapUp(MotionEvent e) 
		     	{
		     		return false;
		     	}
		     	
		     }; 
		     
		    m_GestureDetector = new GestureDetector(getApplicationContext(),GestureMethod);
			
		    
		    // This allows for the Master thread to send a message back to the wallpaper engine 
		    // and request that open gl renders an image.
			m_Handler = new Handler(Looper.getMainLooper())
			{
				@Override
				public void handleMessage(Message msg)
				{
					super.handleMessage(msg);
					
					switch(msg.what)
					{
						case 0:
							requestRender();
							m_Handler.removeMessages(REQUEST_RENDER);	// Prevents build up of request render messages.
							break;	
					}
				}
			};

			// The render packet contains references to objects that are to be rendered.
			// Set by the game logic and used by the renderer.
			m_RendererPacket = new RendererPacket();
			m_MasterThread = new MasterThread(m_RendererPacket, m_Handler, m_Context);
			m_MasterThread.start();

			m_Renderer = new GLRenderer(m_RendererPacket);	
			
			setEGLContextClientVersion(2);		// Sets openGLES version 2.
			setRenderer(m_Renderer);			// Set the renderer for drawing on GLSurfaceView.
			setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY); // Must call requestRender().			
		}

		// If the application is destroyed this safely destroys the app.
		public void onDestroy()
		{
			super.onDestroy();
			
			boolean Retry = true;		// allows the while loop to start.
			
			while(Retry)				// while the game thread has not yet been blocked.
			{
				try
				{
					Retry = false;		// stops the while loop.
					m_MasterThread.StopRunning();
					m_MasterThread.join();	// Blocks the game thread.
				}
				catch(InterruptedException e)
				{
					
				}
				finally
				{
					m_MasterThread.interrupt();	// If all else fails interrupt the thread.
				}
			}	
		}
		
		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height)
		{
			super.onSurfaceChanged(holder, format, width, height);
		}
		
		@Override
		public void onSurfaceCreated(SurfaceHolder holder)
		{
			super.onSurfaceCreated(holder);
		}
		
		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder)
		{
			super.onSurfaceDestroyed(holder);
		}
	
		// When the visibility of the wallpaper changes this function will pause and resume the master thread.
		public void onVisibilityChanged(boolean visible)
		{
			super.onVisibilityChanged(visible);
			
			if(visible)			
			{
				m_MasterThread.ResumeThread();
			}
			else
			{
				m_MasterThread.PauseThread();
			}
		}	
		
		 @Override
	     public void onTouchEvent(MotionEvent e)
	     { 
			 // Calls the gesture detector on touch events.
			 m_GestureDetector.onTouchEvent(e);
	     }
		 
	}
}
