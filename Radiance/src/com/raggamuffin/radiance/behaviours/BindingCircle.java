// Author - Sinclair Ross.
// Date - 12/04/2014
// The binding circle class binds the particle to a sphere with an origin set at 0,0,0

package com.raggamuffin.radiance.behaviours;

import com.raggamuffin.radiance.logic.GameObject;

public class BindingCircle extends BindingBehaviour
{
	GameObject m_Object;
	float m_BindingRadius;
	
	public BindingCircle(float bindingRadius)
	{
		m_BindingRadius = bindingRadius;
	}
	
	@Override
	public void Initialise(BehaviourPacket packet)
	{
		m_Object = packet.GetObject();
	}
	
	@Override
	public void Update() 
	{
		// Calculate a vector from the center of the world to the particle
		float i = m_Object.GetPosition()[0];
		float j = m_Object.GetPosition()[1];
		float k = m_Object.GetPosition()[2];
		
		float UnitI = 0.0f;
		float UnitJ = 0.0f;
		float UnitK = 0.0f;
		
		float Length = (float)Math.sqrt(i*i + j*j + k*k);

		
		// if the length of that vector is greater than the binding radius 
		// create a vector the size of the binding radius pointing towards the particle
		// and set the position of the particle to the end point of that vector.
		if(Length > m_BindingRadius)
		{
			UnitI = i / Length;
			UnitJ = j / Length;
			UnitK = k / Length;
	
			m_Object.SetPosition(UnitI * m_BindingRadius, UnitJ * m_BindingRadius, UnitK * m_BindingRadius);
		}
	}

}
