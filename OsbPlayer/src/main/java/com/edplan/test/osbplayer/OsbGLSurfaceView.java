package com.edplan.test.osbplayer;

import android.opengl.GLSurfaceView;
import android.content.Context;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import android.opengl.GLES20;
import android.view.MotionEvent;
import android.util.Log;
import org.json.JSONObject;
import com.edplan.framework.graphics.opengl.BaseGLSurfaceView;
import com.edplan.framework.graphics.opengl.MainRenderer;

public class OsbGLSurfaceView extends BaseGLSurfaceView
{

	public OsbGLSurfaceView(Context con,MainRenderer r){
		super(con,r);
	}
	/*
	 public BaseGLSurfaceView(Context con,MainRenderer r){
	 super(con);
	 this.setEGLContextClientVersion(2);
	 this.setEGLConfigChooser(new MSAAConfig());
	 mRenderer=r;
	 this.setRenderer(mRenderer);
	 this.setOnTouchListener(mRenderer);
	 this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	 }*/
}
