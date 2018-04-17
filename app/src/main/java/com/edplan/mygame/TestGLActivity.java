package com.edplan.mygame;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import com.edplan.framework.graphics.opengl.BaseGLSurfaceView;
import com.edplan.framework.fallback.GLES10SurfaceView;

public class TestGLActivity extends Activity
{

	GLSurfaceView sv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.mains);
		setContentView((sv==null)?(sv=
		new BaseGLSurfaceView(this)
		//new GLES10SurfaceView(this)
		 ):sv);
	}
}
