// Author - Sinclair Ross.
// Date - 12/04/2014
// Binding behaviours and the classes that inherit from this define how a particle is bound to an area.

package com.raggamuffin.radiance.behaviours;

public abstract class BindingBehaviour 
{
	protected BindingBehaviour()
	{
		
	}
	
	public abstract void Initialise(BehaviourPacket packet);
	public abstract void Update();
}
