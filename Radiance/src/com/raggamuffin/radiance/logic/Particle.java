// Author - Sinclair Ross.
// Date - 12/04/2014

package com.raggamuffin.radiance.logic;

import com.raggamuffin.radiance.behaviours.BehaviourPacket;

public class Particle extends GameObject
{
	private float m_Drag;

	private Graviton m_Graviton;
	
	public Particle(ParticlePacket packet)
	{
		super();
		
		m_Position[0] = packet.GetPosition()[0];
		m_Position[1] = packet.GetPosition()[1];
		m_Position[2] = packet.GetPosition()[2];
		
		m_Colour[0] = packet.GetColour()[0];
		m_Colour[1] = packet.GetColour()[1];
		m_Colour[2] = packet.GetColour()[2];
		m_Colour[3] = packet.GetColour()[3];

		m_Drag = packet.GetDrag();

		m_Graviton = packet.GetGraviton();
		
		m_OriginalScale = packet.GetScale();
		m_Scale = m_OriginalScale;
		
		m_ColourBehaviour = packet.GetColourBehaviour();
		m_MovementBehaviour = packet.GetMovementBehaviour();
		m_BindingBehaviour = packet.GetBindingBehaviour();
		m_ScalingBehaviour = packet.GetScalingBehaviour();
			
		BehaviourPacket TempBehaviourPacket = new BehaviourPacket();
		TempBehaviourPacket.SetObject(this);
		TempBehaviourPacket.SetParticle(this);
		TempBehaviourPacket.SetGraviton(m_Graviton);
		TempBehaviourPacket.SetOrbitalCamera(packet.GetOrbitalCamera());
		
		m_ColourBehaviour.Initialise(TempBehaviourPacket);
		m_MovementBehaviour.Initialise(TempBehaviourPacket);
		m_BindingBehaviour.Initialise(TempBehaviourPacket);
		m_ScalingBehaviour.Initialise(TempBehaviourPacket);
				
		TempBehaviourPacket = null;
	}

	@Override
	public void Update(float deltaTime)
	{
		m_MovementBehaviour.Update(deltaTime);

		m_BindingBehaviour.Update();
		
		m_ColourBehaviour.Update();

		m_ScalingBehaviour.Update();
	}
	
	public float GetDrag()
	{
		return m_Drag;
	}
	
	public Graviton GetGraviton()
	{
		return m_Graviton;
	}
}



























