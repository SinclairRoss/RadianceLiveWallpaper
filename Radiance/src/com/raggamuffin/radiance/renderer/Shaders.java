// Author - Sinclair Ross.
// Date - 12/04/2014

// I present to you the one and only shader used in this app.

package com.raggamuffin.radiance.renderer;

public class Shaders 
{
	public static final String vertexShader_FADEPOINT =
		    "uniform mat4 u_MVPMatrix;      \n"     // A constant representing the combined model/view/projection matrix.
		  + "uniform float u_Size;			\n"
		  
		  + "attribute vec4 a_Position;     \n"     // Per-vertex position information we will pass in.
		 
		  + "void main()                    \n"     
		  + "{                              \n"
		  + "	gl_PointSize = u_Size;		\n"		// The size of the glPoint.
		  + "   gl_Position = u_MVPMatrix   \n"     // Multiply the vertex info by the MVPMatrix.
		  + "               * a_Position;   \n"     
		  + "}                              \n";    


	public static final String fragmentShader_FADEPOINT =
		    "precision mediump float;       \n"     // Set the default precision to medium. We don't need as high of a
		  + "uniform vec4 u_Color;			\n"     // triangle per fragment.
		
		  + "void main()                    \n"     // The entry point for our fragment shader.
		  + "{                              \n"   
		  +	"	float x = (gl_PointCoord.x * 2.0) - 1.0;	\n"		// Find the position of this fragment relative to the center of the glPoint.
		  +	"	float y = (gl_PointCoord.y * 2.0) - 1.0;	\n"
		  
		  + "	float Dist = sqrt((x * x) + (y * y)); 		\n"		// Find the Distance of this fragment from the center.
		  
		  + " 	gl_FragColor = u_Color;    					\n"  	// Set the fragment colour
		  
		  + "	if(Dist < 0.1)								\n"		// if the distance from the center is less than 0.1 we set the alpha value to 1
		  + "	{                              				\n"		// this creates a solid center for the particle and creates the illusion of glow
		  + "  		gl_FragColor.a = 1.0;					\n"		// without using glow shaders.
		  + "	}                              				\n"
		  + "	else										\n"
		  + "	{                              				\n"
		  + "	 	gl_FragColor.a = (1.0 - Dist) * u_Color.a;		\n"
		  + "	}                              				\n"
		  + "}                              				\n";
}








































