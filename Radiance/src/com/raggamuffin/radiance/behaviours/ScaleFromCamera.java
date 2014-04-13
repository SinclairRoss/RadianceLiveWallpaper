// Author - Sinclair Ross.
// Date - 12/04/2014
// This class calculates the scale of a particle depending on its distance from the camera.
// The shape used to draw this is a glPoint. A glPoint has only one vertice so performing a matrix scaling operation doesn't do very much.

package com.raggamuffin.radiance.behaviours;

import com.raggamuffin.radiance.logic.GameObject;
import com.raggamuffin.radiance.logic.OrbitalCamera;

public class ScaleFromCamera extends ScalingBehaviour
{
	private GameObject m_Anchor;
	private OrbitalCamera m_Eye;
	
	private float m_OriginalScale;
	
	public ScaleFromCamera()
	{
		m_OriginalScale = 0.0f;
	}
	
	@Override
	public void Initialise(BehaviourPacket packet) 
	{
		m_Anchor = packet.GetObject();
		m_OriginalScale = m_Anchor.GetOriginalScale();
		m_Eye = packet.GetOrbitalCamera();
	}

	@Override
	public void Update() 
	{
		// Calculate the inverse square of the distance from the graviton.
		float Distance = GetDistanceFromEye();
		float Inverse = 1.0f;
		
		if(Distance < 1.0f)
		{
			Inverse = 1.0f;
		}
		else
		{
			Inverse = InvSqrt(Distance);
		}
		
		// multiply the inverse square of the distance to the camera by the original size of the particle.
		m_Anchor.SetScale(m_OriginalScale * Inverse);
	}
	
	private float InvSqrt(float x) 
	{
		return Float.intBitsToFloat(0x5f3759d5 - (Float.floatToIntBits(x) >> 1));
	}
	
	// returns the distance from the camera.
	private float GetDistanceFromEye()
	{
		float i = m_Eye.GetPosition()[0] - m_Anchor.GetPosition()[0];
		float j = m_Eye.GetPosition()[1] - m_Anchor.GetPosition()[1];
		float k = m_Eye.GetPosition()[2] - m_Anchor.GetPosition()[2];
		
		return (float) Math.sqrt((i*i) + (j*j) + (k*k));
	}

}
