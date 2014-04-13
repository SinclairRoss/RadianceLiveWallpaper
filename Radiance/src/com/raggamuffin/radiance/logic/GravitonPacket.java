// Author - Sinclair Ross.
// Date - 12/04/2014

// Contains all the information a graviton needs to initialise.

package com.raggamuffin.radiance.logic;

import com.raggamuffin.radiance.behaviours.BindingBehaviour;
import com.raggamuffin.radiance.behaviours.MovementBehaviour;

public class GravitonPacket 
{
	private float[] m_Position;
	private float[] m_Colour;
	
	private float m_Speed;
	public float m_Pull;
	
	private MovementBehaviour m_MovementBehaviour;
	private BindingBehaviour m_BindingBehaviour;
	
	public GravitonPacket()
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
		
		m_Speed = 0.0f;
		m_Pull = 0.0f;
		
		m_MovementBehaviour = null;
		m_BindingBehaviour = null;
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
	
	///// Speed \\\\\
	public float GetSpeed()
	{
		return m_Speed;
	}
	
	public void SetSpeed(float Speed)
	{
		m_Speed = Speed;
	}
	
	///// Pull \\\\\
	public float GetPull()
	{
		return m_Pull;
	}
	
	public void SetPull(float pull)
	{
		m_Pull = pull;
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
}
