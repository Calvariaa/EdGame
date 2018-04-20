package com.edplan.framework;

import android.app.Activity;
import android.os.Bundle;
import com.edplan.framework.graphics.opengl.BaseGLSurfaceView;
import java.util.stream.Stream;

public abstract class GameActivity extends Activity 
{
	public GameActivity(){
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		BaseGLSurfaceView view=createGameSurface();
		setContentView(view);
	}

	public abstract BaseGLSurfaceView createGameSurface();
}
