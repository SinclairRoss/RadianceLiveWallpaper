// Author - Sinclair Ross.
// Date - 12/04/2014
// Binding behaviours and the classes that inherit from this define how the colour of a particle behaves.

package com.raggamuffin.radiance.behaviours;

public abstract class ColourBehaviour 
{
	protected float[] m_Colour;
	
	protected ColourBehaviour()
	{
		m_Colour = new float[4];
		m_Colour[0] = 1.0f;
		m_Colour[1] = 1.0f;
		m_Colour[2] = 1.0f;
		m_Colour[3] = 1.0f;
	}
	
	public abstract void Initialise(BehaviourPacket packet);
	public abstract void Update();
	
	protected void BindColours()
	{
		if(m_Colour[0] > 1.0f)
			m_Colour[0] = 1.0f;
		
		if(m_Colour[0] < 0.0f)
			m_Colour[0] = 0.0f;
		
		if(m_Colour[1] > 1.0f)
			m_Colour[1] = 1.0f;
		
		if(m_Colour[1] < 0.0f)
			m_Colour[1] = 0.0f;
		
		if(m_Colour[2] > 1.0f)
			m_Colour[2] = 1.0f;
		
		if(m_Colour[3] < 0.0f)
			m_Colour[3] = 0.0f;
		
		if(m_Colour[3] > 1.0f)
			m_Colour[3] = 1.0f;
		
		if(m_Colour[3] < 0.0f)
			m_Colour[3] = 0.0f;
	}
}
