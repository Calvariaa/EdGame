package com.edplan.framework.graphics.opengl;
import android.opengl.GLSurfaceView;
import android.content.Context;

public class BaseGLSurfaceView extends GLSurfaceView
{
	MainRenderer mRenderer;
	
	public BaseGLSurfaceView(Context con){
		super(con);
		this.setEGLContextClientVersion(2);
		mRenderer=new MainRenderer(getContext());
		this.setRenderer(mRenderer);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}
	
	public MainRenderer getMainRenderer(){
		return mRenderer;
	}
}
