// Author - Sinclair Ross.
// Date - 12/04/2014

// Contains all the information a camera needs to initialise. 

package com.raggamuffin.radiance.logic;

public class CameraPacket 
{
	private float m_OrbitRadius;
	private float m_Mass;
	private float m_Drag;
	
	public CameraPacket()
	{
		m_OrbitRadius = 0.0f;
		m_Mass = 0.0f;
		m_Drag = 0.0f;
	}
	
	///// Orbit Radius \\\\\
	public float GetOrbitRadius()
	{
		return m_OrbitRadius;
	}
	
	public void SetOrbitRadius(float radius)
	{
		m_OrbitRadius = radius;
	}
	
	///// Mass \\\\\
	public float GetMass()
	{
		return m_Mass;
	}
	
	public void SetMass(float mass)
	{
		m_Mass = mass;
	}
	
	///// Drag \\\\\
	public float GetDrag()
	{
		return m_Drag;
	}
	
	public void SetDrag(float drag)
	{
		m_Drag = drag;
	}
}
