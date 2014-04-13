// Author - Sinclair Ross.
// Date - 12/04/2014
// This class contains references to objects that a gameobjects behaviours need to perform various tasks.
// Similar to the renderer packet.

package com.raggamuffin.radiance.behaviours;

import com.raggamuffin.radiance.logic.GameObject;
import com.raggamuffin.radiance.logic.Graviton;
import com.raggamuffin.radiance.logic.OrbitalCamera;
import com.raggamuffin.radiance.logic.Particle;

public class BehaviourPacket 
{
	GameObject m_Object;
	Particle m_Particle;
	Graviton m_Graviton;
	OrbitalCamera m_Camera;
	
	public BehaviourPacket()
	{
		m_Object = null;
		m_Particle = null;
		m_Graviton = null;
		m_Camera = null;
	}
	
	///// Object \\\\\
	public GameObject GetObject()
	{
		return m_Object;
	}
	
	public void SetObject(GameObject obj)
	{
		m_Object = obj;
	}
	
	///// Particle \\\\\
	public Particle GetParticle()
	{
		return m_Particle;
	}
	
	public void SetParticle(Particle particle)
	{
		m_Particle = particle;
	}
	
	///// Graviton \\\\\
	public Graviton GetGraviton()
	{
		return m_Graviton;
	}
	
	public void SetGraviton(Graviton Gravs)
	{
		m_Graviton = Gravs;
	}
	
	///// Orbital Camera \\\\\
	public OrbitalCamera GetOrbitalCamera()
	{
		return m_Camera;
	}
	
	public void SetOrbitalCamera(OrbitalCamera camera)
	{
		m_Camera = camera;
	}
}
