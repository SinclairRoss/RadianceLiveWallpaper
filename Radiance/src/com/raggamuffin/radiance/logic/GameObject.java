// Author - Sinclair Ross.
// Date - 12/04/2014

package com.raggamuffin.radiance.logic;

import java.util.Random;

import com.raggamuffin.radiance.behaviours.BindingBehaviour;
import com.raggamuffin.radiance.behaviours.ColourBehaviour;
import com.raggamuffin.radiance.behaviours.MovementBehaviour;
import com.raggamuffin.radiance.behaviours.ScalingBehaviour;

public abstract class GameObject 
{
	protected float[] m_Position;
	protected float[] m_Velocity;
	protected float[] m_Forward;
	
	protected float[] m_Colour;
	
	protected float m_OriginalScale;
	protected float m_Scale;
	
	protected Random m_Rand;
	
	ColourBehaviour m_ColourBehaviour;
	MovementBehaviour m_MovementBehaviour;
	BindingBehaviour m_BindingBehaviour;
	ScalingBehaviour m_ScalingBehaviour;
	
	public GameObject()
	{
		m_Position = new float[3];
		m_Position[0] = 0.0f;
		m_Position[1] = 0.0f;
		m_Position[2] = 1.0f;
		
		m_Velocity = new float[3];
		m_Velocity[0] = 0.0f;
		m_Velocity[1] = 0.0f;
		m_Velocity[2] = 0.0f;
		
		m_Forward = new float[3];
		m_Forward[0] = 0.0f;
		m_Forward[1] = 1.0f;
		m_Forward[2] = 0.0f;
		
		m_Colour = new float[4];
		m_Colour[0] = 1.0f;
		m_Colour[1] = 1.0f;
		m_Colour[2] = 1.0f;
		m_Colour[3] = 1.0f;
		
		m_OriginalScale = 0.0f;
		m_Scale = 0.0f;
	}
	
	public abstract void Update(float deltaTime);
	
	public float[] GetPosition()
	{
		return m_Position;
	}
	
	public void SetPosition(float x, float y, float z)
	{
		m_Position[0] = x;
		m_Position[1] = y;
		m_Position[2] = z;
	}
	
	public void MoveObject(float deltaX, float deltaY, float deltaZ)
	{
		m_Position[0] += deltaX;
		m_Position[1] += deltaY;
		m_Position[2] += deltaZ;
	}
	
	public float[] GetVelocity()
	{
		return m_Velocity;
	}

	public void SetVelocity(float x, float y, float z)
	{
		m_Velocity[0] = x;
		m_Velocity[1] = y;
		m_Velocity[2] = z;
	}
	
	public float[] GetColour()
	{
		return m_Colour;
	}	
	
	public void SetColour(float[] colour)
	{
		m_Colour[0] = colour[0];
		m_Colour[1] = colour[1];
		m_Colour[2] = colour[2];
		m_Colour[3] = colour[3];
	}
	
	public float[] GetForward()
	{
		return m_Forward;
	}
	
	public void SetForward(float[] forward)
	{
		m_Forward[0] = forward[0];
		m_Forward[1] = forward[1];
	}
	
	public void SetForward(float i, float j)
	{
		m_Forward[0] = i;
		m_Forward[1] = j;
	}

	public float GetScale()
	{
		return m_Scale;
	}
	
	public void SetScale(float scale)
	{
		m_Scale = scale;
	}
	
	public float GetOriginalScale()
	{
		return m_OriginalScale;
	}
	
	public void SetOriginalScale(float scale)
	{
		m_OriginalScale = scale;
	}
}
