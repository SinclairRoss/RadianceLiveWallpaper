// Author - Sinclair Ross.
// Date - 12/04/2014
// This class and those that inherit from it define the particles scaling behaviour.

package com.raggamuffin.radiance.behaviours;

public abstract class ScalingBehaviour 
{
	public abstract void Initialise(BehaviourPacket packet);
	public abstract void Update();
}
