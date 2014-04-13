// Author - Sinclair Ross.
// Date - 12/04/2014
// This class and the classes that inherit from it define behaviours for movement.

package com.raggamuffin.radiance.behaviours;

public abstract class MovementBehaviour 
{
	protected MovementBehaviour()
	{
		
	}

	public abstract void Initialise(BehaviourPacket packet);
	public abstract void Update(float deltaTime);
}
