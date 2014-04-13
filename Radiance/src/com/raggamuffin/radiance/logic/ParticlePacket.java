// Author - Sinclair Ross.
// Date - 12/04/2014

// Contains all the informatin a particle needs to initialise.

package com.raggamuffin.radiance.logic;

import com.raggamuffin.radiance.behaviours.BindingBehaviour;
import com.raggamuffin.radiance.behaviours.ColourBehaviour;
import com.raggamuffin.radiance.behaviours.MovementBehaviour;
import com.raggamuffin.radiance.behaviours.ScalingBehaviour;

public class ParticlePacket 
{
	private float[] m_Position;
	private float[] m_Colour;
	
	private float m_Drag;
	private float m_Scale;
	
	private Graviton m_Graviton;
	private OrbitalCamera m_Camera;

	private ColourBehaviour m_ColourBehaviour;
	private MovementBehaviour m_MovementBehaviour;
	private BindingBehaviour m_BindingBehaviour;
	private ScalingBehaviour m_ScalingBehaviour;
	
	public ParticlePacket()
	{
		m_Position = new float[3];
		m_Position[0] = 0.0f;
		m_Position[1] = 0.0f;
		m_Position[2] = 0.0f;
		
		m_Colour = new float[4];
		m_Colour[0] = 1.0f;
		m_Colour[1] = 1.0f;
		m_Colour[2] = 1.0f;
		m_Colour[3] = 1.0f;
		
		m_Drag = 0.0f;
		
		m_Graviton = null;
		m_Camera = null;
		
		m_ColourBehaviour = null;
		m_MovementBehaviour = null;
		m_BindingBehaviour = null;
		m_ScalingBehaviour = null;
	}
	
	///// Position \\\\\
	public float[] GetPosition()
	{
		return m_Position;
	}
	
	public void SetPosition(float[] Position)
	{
		m_Position[0] = Position[0];
		m_Position[1] = Position[1];
		m_Position[2] = Position[2];
	}
	
	///// Colour \\\\\
	public float[] GetColour()
	{
		return m_Colour;
	}
	
	public void Setcolour(float[] Colour)
	{
		m_Colour[0] = Colour[0];
		m_Colour[1] = Colour[1];
		m_Colour[2] = Colour[2];
		m_Colour[3] = Colour[3];
	}
	
	///// Drag \\\\\
	public float GetDrag()
	{
		return m_Drag;
	}
	
	public void SetDrag(float Drag)
	{
		m_Drag = Drag;
	}
	
	///// Graviton \\\\\
	public Graviton GetGraviton()
	{
		return m_Graviton;
	}
	
	public void SetGraviton(Graviton Grav)
	{
		m_Graviton = Grav;
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
	
	///// Colour Behaviour \\\\\
	public ColourBehaviour GetColourBehaviour()
	{
		return m_ColourBehaviour;
	}
	
	public void SetColourBehaviour(ColourBehaviour behaviour)
	{
		m_ColourBehaviour = behaviour;
	}
	
	///// Movement Behaviour \\\\\
	public MovementBehaviour GetMovementBehaviour()
	{
		return m_MovementBehaviour;
	}
	
	public void SetMovementBehaviour(MovementBehaviour behaviour)
	{
		m_MovementBehaviour = behaviour;
	}
	
	///// BindingBehaviour \\\\\
	public BindingBehaviour GetBindingBehaviour()
	{
		return m_BindingBehaviour;
	}
	
	public void SetBindingBehaviour(BindingBehaviour behaviour)
	{
		m_BindingBehaviour = behaviour;
	}
	
	///// Touch Behaviour \\\\\	
	public ScalingBehaviour GetScalingBehaviour()
	{
		return m_ScalingBehaviour;
	}
	
	public void SetScalingBehaviour(ScalingBehaviour behaviour)
	{
		m_ScalingBehaviour = behaviour;
	}

	///// Scale \\\\\
	public float GetScale()
	{
		return m_Scale;
	}
	
	public void SetScale(float scale)
	{
		m_Scale = scale;
	}
}

