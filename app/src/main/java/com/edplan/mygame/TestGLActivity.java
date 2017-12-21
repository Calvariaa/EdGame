package com.edplan.mygame;
import android.app.Activity;
import android.os.Bundle;
import com.edplan.framework.graphics.opengl.BaseGLSurfaceView;

public class TestGLActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(new BaseGLSurfaceView(this));
	}
	
}
