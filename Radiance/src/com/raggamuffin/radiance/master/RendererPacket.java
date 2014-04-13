// Author - Sinclair Ross.
// Date - 12/04/2014

package com.raggamuffin.radiance.master;

import java.util.Vector;

import com.raggamuffin.radiance.logic.OrbitalCamera;
import com.raggamuffin.radiance.logic.Particle;

public class RendererPacket 
{
	// References to objects that the renderer requires.
	private Vector<Particle> m_Particles;
	private OrbitalCamera m_Camera;
	
	public RendererPacket()
	{
		m_Particles = null;
		m_Camera = null;
	}
	
	///// Particles \\\\\
	public Vector<Particle> GetParticles()
	{
		return m_Particles;
	}
	
	public void SetParticles(Vector<Particle> Particles)
	{
		m_Particles = Particles;
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
	
	///// Destroy \\\\\
	public void Destroy()
	{
		m_Particles.clear();
		m_Particles = null;
	}
}
