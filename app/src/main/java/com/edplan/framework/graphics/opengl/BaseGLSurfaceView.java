package com.edplan.framework.graphics.opengl;
import android.opengl.GLSurfaceView;
import android.content.Context;

public class BaseGLSurfaceView extends GLSurfaceView
{
	Renderer mRenderer;
	
	public BaseGLSurfaceView(Context con){
		super(con);
		this.setEGLContextClientVersion(2);
		mRenderer=new MainRenderer();
		this.setRenderer(mRenderer);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}
}
