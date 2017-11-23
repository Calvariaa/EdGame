package com.edplan.framework.graphics.opengl;
import android.opengl.GLES20;
import com.edplan.framework.graphics.opengl.objs.Color4;

public class GLWrapped
{
	private static boolean depthTest=false;
	
	public static void initial(){
		depthTest=false;
		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
	}
	
	public static void setDepthTest(boolean f){
		if(f!=depthTest){
			if(f){
				GLES20.glEnable(GLES20.GL_DEPTH_TEST);
			}else{
				GLES20.glDisable(GLES20.GL_DEPTH_TEST);
			}
			depthTest=f;
		}
	}
	
	public static void clearColor(Color4 c){
		GLES20.glClearColor(c.r,c.g,c.b,c.a);
	}
}
