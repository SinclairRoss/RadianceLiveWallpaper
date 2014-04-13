// Author - Sinclair Ross.
// Date - 12/04/2014
// This class simply interpolates the colour of the particle between its colour and the colour of the graviton based on the distance
// from the graviton.
package com.raggamuffin.radiance.behaviours;

import com.raggamuffin.radiance.logic.GameObject;
import com.raggamuffin.radiance.logic.Graviton;

public class InterpolateColour extends ColourBehaviour
{
	private GameObject m_Object;	// The object thats colour is changing.
	private Graviton m_Graviton;	
	
	private float[] m_OriginalColour;
	private float[] m_MaxColour;
	
	private float m_Interpolation;
	private float m_Radius;
	
	public InterpolateColour(float Radius)
	{
		super();
		
		m_Interpolation = 0.0f;	
		m_Radius = Radius;
		
		m_OriginalColour = new float[4];
		m_OriginalColour[0] = 0.0f;
		m_OriginalColour[1] = 0.0f;
		m_OriginalColour[2] = 0.0f;
		m_OriginalColour[3] = 0.0f;
		
		m_MaxColour = new float[4];
		m_MaxColour[0] = 0.0f;
		m_MaxColour[1] = 0.0f;
		m_MaxColour[2] = 0.0f;
		m_MaxColour[3] = 0.0f;
	}
	
	@Override
	public void Initialise(BehaviourPacket packet)
	{
		m_Object = packet.GetObject();
		m_Graviton = packet.GetGraviton();
		
		m_OriginalColour[0] = m_Object.GetColour()[0];
		m_OriginalColour[1] = m_Object.GetColour()[1];
		m_OriginalColour[2] = m_Object.GetColour()[2];
		m_OriginalColour[3] = m_Object.GetColour()[3];
		
		m_MaxColour[0] = m_Graviton.GetColour()[0];
		m_MaxColour[1] = m_Graviton.GetColour()[1];
		m_MaxColour[2] = m_Graviton.GetColour()[2];
		m_MaxColour[3] = m_Graviton.GetColour()[3];
	}
	
	@Override
	public void Update() 
	{
		// Set the initial colour of the particle.
		m_Colour[0] = m_OriginalColour[0];
		m_Colour[1] = m_OriginalColour[1];
		m_Colour[2] = m_OriginalColour[2];
		m_Colour[3] = m_OriginalColour[3];

		// calculate a value between 0 and 1 that represents how far along the change in colours is.
		m_Interpolation = NormaliseValue(m_Radius, GetDistanceFromGraviton());
		
		// keeps the interpolation value within bounds.
		if(m_Interpolation < 0.0f)
			m_Interpolation = 0.0f;
		
		if(m_Interpolation > 1.0f)
			m_Interpolation = 1.0f;

		// performs the linear interpolation calculation for each colour channel.
		m_Colour[0] = LinearInterpolation(m_OriginalColour[0],m_MaxColour[0],m_Interpolation);
		m_Colour[1] = LinearInterpolation(m_OriginalColour[1],m_MaxColour[1],m_Interpolation);
		m_Colour[2] = LinearInterpolation(m_OriginalColour[2],m_MaxColour[2],m_Interpolation);
		m_Colour[3] = LinearInterpolation(m_OriginalColour[3],m_MaxColour[3],m_Interpolation);
	
		// bind the colours between 0 and 1.
		BindColours();

		// finally set the colour of the particle to the colour we calculated.
		m_Object.SetColour(m_Colour);
	}
	
	// returns the distance from the graviton.
	private float GetDistanceFromGraviton()
	{
		float i = m_Graviton.GetPosition()[0] - m_Object.GetPosition()[0];
		float j = m_Graviton.GetPosition()[1] - m_Object.GetPosition()[1];
		float k = m_Graviton.GetPosition()[2] - m_Object.GetPosition()[2];

		return (float)Math.sqrt(i*i + j*j + k*k);
	}
	
	// interpolates between two values.
	private float LinearInterpolation(float min, float max, float point)
	{
		return (max * (1.0f - point) + (min * point));
	}
	
	// return a value normalised between 0 and max
	private float NormaliseValue(float max, float value)
	{	
		if(max <= 0.0f)	// Guards against divide by 0 errors.
			return 1.0f;
		else
			return (value) / (max);
		
	}
}
