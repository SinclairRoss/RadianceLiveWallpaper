package com.raggamuffin.radiance.logic;

import android.os.Bundle;

public class OrbitalCamera 
{
	private float[] m_Position;			// Position of the camera.
	private float[] m_Up;				// The Up vector of the camera.
	private float[] m_LookAt;			// What the camera is looking at.
	
	private float m_Velocity;			// The velocity of the camera.
	private float m_Force;				// The force applied to the camera.
	private float m_Acceleration;		// The acceleration of the camera.
	private float m_DragCoefficient;		// The force of drag acting on the camera.

	private float m_Mass;				// The mass of the camera.
	private float m_OrbitRadius;		// The distance from the point the camera is orbiting.
	private float m_Theta;				// The total rotation of the camera around the center.	
	
	public OrbitalCamera()
	{
		m_Position = new float[3];
		m_Position[0] = 0.0f;
		m_Position[1] = 0.0f;
		m_Position[2] = 0.0f;
		
		m_Up = new float[3];		
		m_Up[0] = 0.0f;
		m_Up[1] = 1.0f;
		m_Up[2] = 0.0f;
		
		m_LookAt = new float[3];		
		m_LookAt[0] = 0.0f;
		m_LookAt[1] = 0.0f;
		m_LookAt[2] = 0.0f;	
		
		m_Velocity = 0.0f;
		m_Force = 0.0f;
		m_Acceleration = 0.0f;
		
		m_Theta = 0.0f;
	}
	
	public void Initialise(CameraPacket packet)
	{
		m_DragCoefficient = packet.GetDrag();
		
		m_Mass = packet.GetMass();
		m_OrbitRadius = packet.GetOrbitRadius(); 
	}
	
	public void Update(float deltaTime, Bundle inputBundle)
	{
		UnbundleInput(inputBundle);
		
		CalculateAcceleration();
		CalculateVelocity();
		ApplyDrag();
		Orbit(deltaTime);
	}
	
	// The camera recieves a force in either the positive or negative direction.
	private void UnbundleInput(Bundle inputBundle)
	{
		ApplyForce(inputBundle.getFloat("FlingVelocity"));
		inputBundle.putFloat("FlingVelocity", 0.0f);
	}
	
	// Force is applied to the camera.
	// As the camera can only orbit around the camera force only needs to be calculated for one dimention.
	private void ApplyForce(float force)
	{
		m_Force = force * 0.01f;
	}
	
	// The acceleration due to the applied force is calculated using F = ma
	private void CalculateAcceleration()
	{
		if(m_Force != 0.0f)
		{
			m_Acceleration = (m_Force / m_Mass);
		}
		else
		{
			m_Acceleration = 0.0f;
		}
	}
	
	// The acceleration is added to the velocity of the camera.
	private void CalculateVelocity()
	{
		m_Velocity += m_Acceleration;
	}
	
	// Calculate and apply drag.
	private void ApplyDrag()
	{
		m_Velocity += (-m_DragCoefficient * m_Velocity);
	}
	
	// Rotate the camera on its orbit.
	private void Orbit(float deltaTime)
	{
		m_Theta += m_Velocity * deltaTime;
		
		// Wrap the rotation.
		if(m_Theta > Math.PI * 2)
			m_Theta -= Math.PI * 2;
		
		if(m_Theta < 0)
			m_Theta += Math.PI * 2;
		
		m_Position[0] = (float)((m_OrbitRadius * -Math.sin(m_Theta)));
		m_Position[2] = (float)((m_OrbitRadius *  Math.cos(m_Theta)));
	}
	
	public float[] GetPosition()
	{
		return m_Position;
	}
	
	public float[] GetUp()
	{
		return m_Up;
	}
	
	public float[] GetLookAt()
	{
		return m_LookAt;
	}
}
