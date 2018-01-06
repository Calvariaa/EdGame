package com.edplan.mygame;
import android.app.Activity;
import android.os.Bundle;
import com.edplan.framework.graphics.opengl.BaseGLSurfaceView;

public class TestGLActivity extends Activity
{

	BaseGLSurfaceView sv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.mains);
		setContentView((sv==null)?(sv=new BaseGLSurfaceView(this)):sv);
	}
}
