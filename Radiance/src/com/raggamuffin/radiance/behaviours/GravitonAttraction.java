// Author - Sinclair Ross.
// Date - 12/04/2014
// The Graviton attraction class makes the particle move towards the graviton as if caught in its gravitational pull.

package com.raggamuffin.radiance.behaviours;

import com.raggamuffin.radiance.logic.Graviton;
import com.raggamuffin.radiance.logic.Particle;

public class GravitonAttraction extends MovementBehaviour
{
	private Particle m_Particle;
	private Graviton m_Graviton;
	
	private float m_Pull;
	private float m_Drag;
	
	public GravitonAttraction()
	{
		
	}
	
	@Override
	public void Initialise(BehaviourPacket packet) 
	{
		m_Particle = packet.GetParticle();
		m_Graviton = packet.GetGraviton();	
		
		m_Pull = m_Graviton.GetPull();
		m_Drag = -m_Particle.GetDrag();
	}
	
	@Override
	public void Update(float deltaTime) 
	{
		// get the velocity of the particle
		float ParticleVelocityX = m_Particle.GetVelocity()[0];
		float ParticleVelocityY = m_Particle.GetVelocity()[1];
		float ParticleVelocityZ = m_Particle.GetVelocity()[2];

		// get a vector from the particle to the graviton
		float i = m_Graviton.GetPosition()[0] - m_Particle.GetPosition()[0];
		float j = m_Graviton.GetPosition()[1] - m_Particle.GetPosition()[1];
		float k = m_Graviton.GetPosition()[2] - m_Particle.GetPosition()[2];

		float Distance = (float)Math.sqrt(i*i + j*j + k*k);
		
		if(Distance == 0.0f)	// Guard against Divide by 0.
			Distance = 1.0f;
		
		// calculate the inverse square root of the distance from the graviton.
		float Inverse = 1.0f;
		
		// if the distance is less that one the value returned by the inverse function goes extremely  high causing a sudden burst of speed.
		// It looks kinda cool but isn't the effect i am looking for.
		if(Distance < 1.0f)
			Inverse = 1.0f;
		else
			Inverse = InvSqrt(Distance);

		// calculate the unit vector pointing to the graviton.
		i /= Distance;
		j /= Distance;
		k /= Distance;
		
		// add the acceleration due to gravity to the velocity of the particle.
		ParticleVelocityX += (i * Inverse * m_Pull);
		ParticleVelocityY += (j * Inverse * m_Pull);	
		ParticleVelocityZ += (k * Inverse * m_Pull);	
    
		// Calculate the force of drag acting on the particle
		float DragX = 0.0f;
		float DragY = 0.0f;
		float DragZ = 0.0f;
		
		float Speed = 0.0f;
		
		Speed = (float) Math.sqrt((ParticleVelocityX * ParticleVelocityX) + (ParticleVelocityY * ParticleVelocityY) + (ParticleVelocityZ * ParticleVelocityZ));

		DragX = m_Drag * ParticleVelocityX * Speed;
		DragY = m_Drag * ParticleVelocityY * Speed;
		DragZ = m_Drag * ParticleVelocityZ * Speed;
		
		// Apply drag to the velocity.
		ParticleVelocityX += DragX;
		ParticleVelocityY += DragY;
		ParticleVelocityZ += DragZ;
		
		// set the velocity of the particle and then move the particle.
		m_Particle.SetVelocity(ParticleVelocityX, ParticleVelocityY, ParticleVelocityZ);
		m_Particle.MoveObject(ParticleVelocityX * deltaTime, ParticleVelocityY * deltaTime, ParticleVelocityZ * deltaTime);
	}
	
	// Calculates the inverse square root of x.
	private float InvSqrt(float x) 
	{
		return Float.intBitsToFloat(0x5f3759d5 - (Float.floatToIntBits(x) >> 1));
	}
}
