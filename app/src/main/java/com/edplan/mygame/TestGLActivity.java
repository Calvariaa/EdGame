package com.edplan.mygame;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import com.edplan.framework.graphics.opengl.BaseGLSurfaceView;
import com.edplan.framework.fallback.GLES10SurfaceView;
import com.edplan.framework.GameActivity;
import com.edplan.framework.graphics.opengl.MainRenderer;
import android.content.Context;
import com.edplan.framework.MContext;
import com.edplan.framework.ui.EdView;

public class TestGLActivity extends GameActivity
{

	@Override
	public BaseGLSurfaceView createGameSurface() {
		// TODO: Implement this method
		BaseGLSurfaceView glSurface=new BaseGLSurfaceView(this,new TestRenderer(this));
		return glSurface;
	}

	
	public class TestRenderer extends MainRenderer{
		public TestRenderer(Context con){
			super(con);
		}

		@Override
		public EdView createContentView(MContext c) {
			// TODO: Implement this method
			return new TestView(c);
		}
	}
	
}
