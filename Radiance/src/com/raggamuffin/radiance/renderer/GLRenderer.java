// Author - Sinclair Ross.
// Date - 12/04/2014

package com.raggamuffin.radiance.renderer;

import java.util.Iterator;
import java.util.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.raggamuffin.radiance.logic.Particle;
import com.raggamuffin.radiance.master.RendererPacket;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class GLRenderer implements GLSurfaceView.Renderer
{
	public static final String TAG = "GLRenderer";
	
	private float[] m_MVPMatrix  = new float[16];	// MVP Matrix.
    private float[] m_MMatrix 	 = new float[16];	// Model Matrix.		
    private float[] m_ViewMatrix = new float[16];	// View Matrix.
    private float[] m_ProjMatrix = new float[16];	// Projection Matrix.
    
    private GLCamera 	m_Camera;	// This camera will provide the view and projection matrices.
    private GLPoint 	m_Point;	// This point will be used to draw the particles.
 
    private Vector<Particle> 	m_Particles;

	public GLRenderer(RendererPacket packet)
	{
		Log.e(TAG, "GLRenderer");

		m_Camera = new GLCamera(packet.GetOrbitalCamera());	// Create a new GLCamera using the Orbital camera from the Renderer packet as an anchor.
		m_Particles = packet.GetParticles();				// get the vector of particles from the renderer packet.
	}
	
	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) 
	{
		Log.e(TAG, "onSurfaceCreated");
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);	// Set the colour that will be used to clear the buffer.

		m_Point   = new GLPoint();		// Create a new glpoint.

		GLES20.glEnable(GLES20.GL_CULL_FACE);	// enable face culling.
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);	// Disable depth testing
        GLES20.glEnable(GLES20.GL_BLEND);		// Enable blending.
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);	// set the blend function.
        
        GLES20.glCullFace(GLES20.GL_BACK);		// Cull the back face of drawn objects.
	}

	@Override
	public void onDrawFrame(GL10 unused) 
	{
		// Clear the buffer.
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		
		// Update the camera.
		m_Camera.Update();
		
		// Get the view and projection matrices from the camera.
		m_ViewMatrix = m_Camera.GetViewMatrix();
		m_ProjMatrix = m_Camera.GetProjectionMatrix();
		
		// iterate through the list of particles.
		for(Iterator<Particle> iterator = m_Particles.iterator(); iterator.hasNext();)
        {
			Particle Temp = iterator.next(); // Get the next particle in the vector.
        	
			// Rest the model matrix.
        	Matrix.setIdentityM(m_MMatrix, 0);
        	// Translate the model matrix.
    		Matrix.translateM(m_MMatrix, 0, Temp.GetPosition()[0], Temp.GetPosition()[1], Temp.GetPosition()[2]);
    		
    		// Combine the model, view and projection matrices to create the MVP matrix.
            Matrix.multiplyMM(m_MVPMatrix, 0, m_ViewMatrix, 0, m_MMatrix, 0);
            Matrix.multiplyMM(m_MVPMatrix, 0, m_ProjMatrix, 0, m_MVPMatrix, 0);
            
            // Set the size and colour of the glPoint.
            m_Point.SetSize(Temp.GetScale());
            m_Point.SetColour(Temp.GetColour());
            
            // Draw the glPoint.
            m_Point.draw(m_MVPMatrix);
        }
	}

	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) 
	{
		Log.e(TAG, "onSurfaceChanged");
		m_Camera.ViewPortChanged(width, height);
	}
	
	public static int loadShader(int type, String shaderCode)
    {
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
