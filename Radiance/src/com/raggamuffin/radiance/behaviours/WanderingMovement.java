// Author - Sinclair Ross.
// Date - 12/04/2014
// This class makes the object it is attached to wander aimlessly.
// Used for the graviton movement.

package com.raggamuffin.radiance.behaviours;

import java.util.Random;

import com.raggamuffin.radiance.logic.GameObject;

public class WanderingMovement extends MovementBehaviour
{
	GameObject m_Object;
	private final Random m_Rand;
	
	float m_TotalRotation;
	float m_TotalDepth;	
	float m_SteeringLooseness;
	float m_HalfSteeringLooseness;
	
	float m_Speed;
	
	public WanderingMovement(float speed)
	{
		m_Speed = speed;

		m_Rand = new Random();
		m_TotalRotation = 0.0f;
	}
	
	@Override
	public void Initialise(BehaviourPacket packet)
	{
		m_Object = packet.GetObject();

		m_SteeringLooseness = (float)Math.toRadians(30);
		m_HalfSteeringLooseness = m_SteeringLooseness * 0.5f;
	}
	
	@Override
	public void Update(float deltaTime) 
	{
		// Generate a random rotation between the maximum and minimum steering limits and add that to the total rotation
		float Rotation = (m_SteeringLooseness * m_Rand.nextFloat()) - m_HalfSteeringLooseness;
		m_TotalRotation += Rotation;

		// Rotate the forward vector by the total rotaion of the steering behaviour in the x y plane.
		float ForwardX = (float)((-Math.sin(m_TotalRotation)));
		float ForwardY = (float)(( Math.cos(m_TotalRotation)));
		
		// Calculate the steering rotation in the z axis.
		float Depth = (m_SteeringLooseness * m_Rand.nextFloat()) - m_HalfSteeringLooseness;
		m_TotalDepth += Depth;
		
		if(m_TotalDepth > 1.0f)
			m_TotalDepth = 1.0f;

		if(m_TotalDepth < -1.0f)
			m_TotalDepth = -1.0f;
		
		// Set the forward vectors k component.
		float ForwardZ = m_TotalDepth;
		
		// Move the object to its new position.
		m_Object.MoveObject(ForwardX * m_Speed * deltaTime, ForwardY * m_Speed * deltaTime, ForwardZ * m_Speed * deltaTime);
	}

}
