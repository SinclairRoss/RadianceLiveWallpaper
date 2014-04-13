// Author - Sinclair Ross.
// Date - 12/04/2014

package com.raggamuffin.radiance.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

public class GLPoint 
{
    public final FloatBuffer vertexBuffer;
    public int PositionHandle;
    
    private int m_Program;
	
	private int m_MVPMatrixHandle; 
	private int m_SizeHandle;
    private int m_ColourHandle;
    private int m_PositionHandle;
    
    private float[] m_Colour;
    private float m_Size;
    
    static final int COORDS_PER_VERTEX = 3;
    static final int VERTEX_STRIDE = COORDS_PER_VERTEX * 4;
    static float VertexCoords[] = 
    { 
        0.0f, 0.0f, 0.0f
    };
    
    public GLPoint() 
    {
    	// create a byte buffer for the vertex coords.
    	ByteBuffer bb = ByteBuffer.allocateDirect(VertexCoords.length * 4);
		bb.order(ByteOrder.nativeOrder());
		vertexBuffer = bb.asFloatBuffer();
		vertexBuffer.put(VertexCoords);
		vertexBuffer.position(0);
		
		m_Colour = new float[4];
		m_Colour[0] = 1.0f;
		m_Colour[1] = 1.0f;
		m_Colour[2] = 1.0f;
		m_Colour[3] = 1.0f;

		m_Program 			= 0;
	    m_MVPMatrixHandle	= 0;  
	    m_SizeHandle		= 0;
	    m_ColourHandle		= 0;
	    m_PositionHandle	= 0;
	    
	    InitShaders();
	    
	    // set the shader program that will render this object.
	    GLES20.glUseProgram(m_Program);
    }
	
	public void draw(float[] mvpMatrix) 
	{
		// Set the shader information.
		GLES20.glUniformMatrix4fv(m_MVPMatrixHandle, 1, false, mvpMatrix, 0);
        GLES20.glUniform4fv(m_ColourHandle, 1, m_Colour, 0);
        GLES20.glUniform1f(m_SizeHandle, m_Size);
        
        GLES20.glEnableVertexAttribArray(m_PositionHandle);
		GLES20.glVertexAttribPointer(m_PositionHandle, GLPoint.COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, GLPoint.VERTEX_STRIDE, vertexBuffer);

		// Draw the object using glPoints.
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);
	}
	
	public void SetSize(float size)
	{
		m_Size = size;
	}
	
	public void SetColour(float[] colour)
	{
		m_Colour[0] = colour[0];
		m_Colour[1] = colour[1];
		m_Colour[2] = colour[2];
		m_Colour[3] = colour[3];
	}
	
	private void InitShaders()
    {
		int vertexShaderHandler = 0;
		int fragmentShaderHandler = 0;

		// prepare shaders and OpenGL program
		vertexShaderHandler 	= GLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,Shaders.vertexShader_FADEPOINT);
		fragmentShaderHandler 	= GLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,Shaders.fragmentShader_FADEPOINT);

		m_Program = GLES20.glCreateProgram();             		// create empty OpenGL Program
        GLES20.glAttachShader(m_Program, vertexShaderHandler);   // add the vertex shader to program
        GLES20.glAttachShader(m_Program, fragmentShaderHandler); // add the fragment shader to program
        GLES20.glLinkProgram(m_Program);                  		// create OpenGL program executables

        m_MVPMatrixHandle 	= GLES20.glGetUniformLocation(m_Program, "u_MVPMatrix");  
        m_ColourHandle 		= GLES20.glGetUniformLocation(m_Program, "u_Color");
        m_SizeHandle 		= GLES20.glGetUniformLocation(m_Program, "u_Size");
        
        m_PositionHandle = GLES20.glGetAttribLocation(m_Program, "a_Position");
    }
}
