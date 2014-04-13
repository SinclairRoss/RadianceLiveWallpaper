// Author - Sinclair Ross.
// Date - 12/04/2014

package com.raggamuffin.radiance.logic;

import com.raggamuffin.radiance.behaviours.BehaviourPacket;
import com.raggamuffin.radiance.behaviours.BindingBehaviour;
import com.raggamuffin.radiance.behaviours.MovementBehaviour;

public class Graviton extends GameObject
{
	private float 	m_Pull;

	MovementBehaviour m_MovementBehaviour;
	BindingBehaviour m_BindingBehaviour;

	public Graviton(GravitonPacket packet)
	{
		super();
		
		m_Position[0] = packet.GetPosition()[0];
		m_Position[1] = packet.GetPosition()[1];
		m_Position[2] = packet.GetPosition()[2];
		
		m_Colour[0] = packet.GetColour()[0];
		m_Colour[1] = packet.GetColour()[1];
		m_Colour[2] = packet.GetColour()[2];
		m_Colour[3] = packet.GetColour()[3];

		m_Pull = packet.GetPull();

		m_MovementBehaviour = packet.GetMovementBehaviour();
		m_BindingBehaviour = packet.GetBindingBehaviour();
		
		BehaviourPacket TempBehaviourPacket = new BehaviourPacket();
		TempBehaviourPacket.SetObject(this);
		
		m_MovementBehaviour.Initialise(TempBehaviourPacket);
		m_BindingBehaviour.Initialise(TempBehaviourPacket);
				
		TempBehaviourPacket = null;
	}
	
	@Override
	public void Update(float deltaTime)
	{
		m_MovementBehaviour.Update(deltaTime);	
		m_BindingBehaviour.Update();
	}
	
	public float GetPull()
	{
		return m_Pull;
	}
	
	public float[] GetPosition()
	{
		return m_Position;
	}
}
