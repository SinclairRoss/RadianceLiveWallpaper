// Author - Sinclair Ross.
// Date - 12/04/2014

// This application was designed so that an intirely new wallpaper could be made simply by changing the logic class and a handful of other small 
// changes to the code most noteably the renderer and renderer packet.

package com.raggamuffin.radiance.logic;

import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.raggamuffin.radiance.behaviours.BindingBehaviour;
import com.raggamuffin.radiance.behaviours.BindingCircle;
import com.raggamuffin.radiance.behaviours.ColourBehaviour;
import com.raggamuffin.radiance.behaviours.GravitonAttraction;
import com.raggamuffin.radiance.behaviours.InterpolateColour;
import com.raggamuffin.radiance.behaviours.MovementBehaviour;
import com.raggamuffin.radiance.behaviours.ScaleFromCamera;
import com.raggamuffin.radiance.behaviours.ScalingBehaviour;
import com.raggamuffin.radiance.behaviours.WanderingMovement;
import com.raggamuffin.radiance.master.PaperSettings;
import com.raggamuffin.radiance.master.RendererPacket;

public class LogicMaster
{
	private OrbitalCamera m_Camera;
	
	private Vector<Particle> m_Particles;
	private Graviton m_Graviton;
	
	private int m_ParticleCount;
	
	private final Random m_Rand;
	private static SharedPreferences m_Preferences;

	public LogicMaster(RendererPacket packet, Context context)
	{
		m_Camera = new OrbitalCamera();
		m_Particles = new Vector<Particle>();
		m_Graviton = null;
		
		m_ParticleCount = 0;
		
		m_Rand = new Random();
		m_Preferences = PreferenceManager.getDefaultSharedPreferences(context);

		packet.SetParticles(m_Particles);
		packet.SetOrbitalCamera(m_Camera);	
		
		SpawnGameObjects();
	}
	
	public void SpawnGameObjects()
	{
		SpawnCamera();
		SpawnGraviton();
		SpawnParticles();
	}

	public void Update(Long DT, Bundle inputBundle)
	{
		float DeltaTime = DT * 0.0001f;

		m_Camera.Update(DeltaTime,inputBundle);
		m_Graviton.Update(DeltaTime);
        
		for(int p = 0; p < m_ParticleCount; p++)
		{
			m_Particles.elementAt(p).Update(DeltaTime);
        }
	}
	
	private void SpawnCamera()
	{
		// Create a new Camera Packet.
		CameraPacket Packet = new CameraPacket();
		
		// Set the various attributes of the camera class with values from the shared preferences.
		Packet.SetDrag(m_Preferences.getInt(PaperSettings.CAMERA_DRAG, 5) * PaperSettings.CAMERA_DRAG_MULTIPLIER);
		Packet.SetMass(m_Preferences.getInt(PaperSettings.CAMERA_MASS, 5) * PaperSettings.CAMERA_MASS_MULTIPLIER);
		Packet.SetOrbitRadius(m_Preferences.getInt(PaperSettings.CAMERA_ORBIT_RADIUS, 80) * PaperSettings.CAMERA_RADIUS_MULTIPLIER);
		
		// Initialise the camera using the camera packet.
		m_Camera.Initialise(Packet);
		
		// Allow the camera packet to be picked up by the garbage collector.
		Packet = null;
	}
	
	private void SpawnGraviton()
	{
		// Clear up the graviton and allow it to be garbage collected.
		// It is simpler just to create a new graviton than change the values of the current one
		// And as this only happens when the user makes a change the cost of doing this will be next to nothing.
		m_Graviton = null;
		
		// Create a new graviton packet.
		GravitonPacket Packet = new GravitonPacket();
		
		float[] Pos = new float [3];
		Pos[0] = 0.0f;
		Pos[1] = 0.0f;
		Pos[2] = 0.0f;

		Packet.SetPosition(Pos);

		// Set various attributes of the graviton with values from the shared preferences.
		Packet.Setcolour(PaperSettings.UnpackColour(m_Preferences.getInt(PaperSettings.GRAVITON_COLOUR, -13281)));
		Packet.SetPull((float)m_Preferences.getInt(PaperSettings.GRAVITON_PULL, 200) * PaperSettings.GRAVITON_PULL_MULTIPLIER);

		// Set the wandering and binding behaviour for the graviton using values from the shared preferences.
		MovementBehaviour MovementBehaviour = new WanderingMovement((float)m_Preferences.getInt(PaperSettings.GRAVITON_SPEED, 400) * PaperSettings.GRAVITON_SPEED_MULTIPLIER);
		BindingBehaviour BindingBehaviour 	= new BindingCircle((float)m_Preferences.getInt(PaperSettings.GRAVITON_BINDING, 70) * PaperSettings.OBJECT_BINDING_MULTIPLIER);

		// Set the behaviours in the packet.
		Packet.SetMovementBehaviour(MovementBehaviour);
		Packet.SetBindingBehaviour(BindingBehaviour);
		
		// Create a new graviton.
		m_Graviton = new Graviton(Packet);
		
		// Allow the graviton packet to be picked up by the garbage collector.
		Packet = null;
	}
	
	private void SpawnParticles()
	{
		// Clear up the particles and allow them to be garbage collected.
		// It is simpler just to create a new particles than change the values of the current particles
		// And as this only happens when the user makes a change the cost of doing this will be next to nothing.
		m_Particles.clear();
		
		// Create a new particle packet.
		ParticlePacket Packet = new ParticlePacket();
		float[] Pos = new float[3];
		float[] Colour = new float[4];
		
		// Get the number of particles from the shared preferences.
		m_ParticleCount = m_Preferences.getInt(PaperSettings.PARTICLE_COUNT, 400);
		
		for(int p = 0; p < m_ParticleCount; p++)
		{
			// Get a random position for the particle.
			Pos[0] = (m_Rand.nextFloat() * 200.0f) - 100.0f;
			Pos[1] = (m_Rand.nextFloat() * 200.0f) - 100.0f;
			Pos[2] = (m_Rand.nextFloat() * 200.0f) - 100.0f;

			Packet.SetPosition(Pos);
			
			// Get the colour of the particle from the shared preferences.
			Colour = PaperSettings.UnpackColour(m_Preferences.getInt(PaperSettings.PARTICLE_COLOUR, 659881794));	
			
			Packet.Setcolour(Colour);
			
			// Set the various attributes of the particle from the shared preferences.
			Packet.SetDrag(m_Preferences.getInt(PaperSettings.PARTICLE_DRAG, 8) * PaperSettings.PARTICLE_DRAG_MULTIPLIER);
			Packet.SetScale(m_Preferences.getInt(PaperSettings.PARTICLE_SCALE, 90));
			Packet.SetGraviton(m_Graviton);
			Packet.SetOrbitalCamera(m_Camera);
			
			// Get the behaviours of the particle using the shared preferences.
			ColourBehaviour 	PartColourBehaviour 	= new InterpolateColour((float)m_Preferences.getInt(PaperSettings.GRAVITON_COLOUR_RADIUS, 70) * PaperSettings.GRAVITON_COLOUR_RADIUS_MULTIPLER);
			MovementBehaviour 	PartMovementBehaviour 	= new GravitonAttraction();
			BindingBehaviour 	PartBindingBehaviour 	= new BindingCircle((float)m_Preferences.getInt(PaperSettings.PARTICLE_BINDING, 120) * PaperSettings.OBJECT_BINDING_MULTIPLIER);
			ScalingBehaviour 	PartScalingBehaviour 	= new ScaleFromCamera();
			
			// Set the various behaviours of the particle.
			Packet.SetColourBehaviour(PartColourBehaviour);
			Packet.SetMovementBehaviour(PartMovementBehaviour);
			Packet.SetBindingBehaviour(PartBindingBehaviour);
			Packet.SetScalingBehaviour(PartScalingBehaviour);
			
			// Add the new particle to the vector of particles.
			m_Particles.add(new Particle(Packet));
		}
		
		// Set the packet to null and allow it to be picked up by the garbage collector.
		Packet = null;
	}
}
