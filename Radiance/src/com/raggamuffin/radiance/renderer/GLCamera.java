// Author - Sinclair Ross.
// Date - 12/04/2014

package com.raggamuffin.radiance.renderer;

import com.raggamuffin.radiance.logic.OrbitalCamera;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public class GLCamera 
{
	public static final String TAG = "DEBUG GLCamera";
	
	// The view and projection matrices.
	private final float[] m_ViewMatrix = new float[16];
	private final float[] m_ProjMatrix = new float[16];
	
	private float[] m_Position;		
	private float[] m_LookAt;
	private float[] m_Up;
	
	// The near and far planes of the frustum.
	private float m_Near;
	private float m_Far;
	
	// The camera in game logic that this camera represents.
	OrbitalCamera m_Anchor;

	public GLCamera(OrbitalCamera camera)
	{
		Log.e(TAG, "GLCamera");
		
		m_Anchor = camera;
	
		m_Position = new float[3];
		m_Position[0] = 0.0f;
		m_Position[1] = 0.0f;
		m_Position[2] = -1.0f;
		
		m_LookAt = new float[3];
		m_LookAt[0] = 0.0f;
		m_LookAt[1] = 0.0f;
		m_LookAt[2] = 0.0f;

		m_Up = new float[3];
		m_Up[0] = 0.0f;
		m_Up[1] = 1.0f;
		m_Up[2] = 0.0f;
		
		m_Near = 1.0f;
		m_Far = 100.0f;
		
		// Set the look at matrix.
		Matrix.setLookAtM(m_ViewMatrix, 0,   m_Position[0], m_Position[1], m_Position[2],   m_LookAt[0], m_LookAt[1], m_LookAt[2],   m_Up[0], m_Up[1], m_Up[2]);
	}
	
	public void Update()
	{	
		// Get the position of the orbital camera.
		m_Position 	= m_Anchor.GetPosition();
			
		// Set the look at matrix.
		Matrix.setLookAtM(m_ViewMatrix, 0,   m_Position[0], m_Position[1], m_Position[2],   m_LookAt[0], m_LookAt[1], m_LookAt[2],   m_Up[0], m_Up[1], m_Up[2]);
	}
	
	// On the event of the viewport changing this function sets the new dimentions of the viewport and sets the camera frustum.
	public void ViewPortChanged(int width, int height)
	{
		Log.e(TAG, "ViewPortChanged");
		
		float AspectRatio = (float)width/(float)height;
		GLES20.glViewport(0, 0, width, height);
		Matrix.frustumM(m_ProjMatrix, 0, -AspectRatio, AspectRatio, -1, 1, m_Near, m_Far);
	}
	
	public float[] GetPosition()
	{
		return m_Position;
	}
	
	public float[] GetViewMatrix()
	{
		return m_ViewMatrix;
	}
	
	public float[] GetProjectionMatrix()
	{
		return m_ProjMatrix;
	}
}
