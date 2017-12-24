package com.edplan.opengl;
import android.opengl.GLSurfaceView;
import android.content.Context;
import android.util.AttributeSet;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView
{
	private final float TOUCH_SCALE_FACTOR=180.0f/320;
	private SceneRenderer myRenderer;
	private float preX,preY;
	
	public MyGLSurfaceView(Context context,AttributeSet attr){
		super(context,attr);
		myRenderer=new SceneRenderer();
		this.setRenderer(myRenderer);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		// TODO: Implement this method
		float x=event.getX(),y=event.getY();
		switch(event.getAction()){
			case MotionEvent.ACTION_MOVE:
				float dy=y-preY;
				float dx=x-preX;
				myRenderer.tr.yAngle+=dx*TOUCH_SCALE_FACTOR;
				myRenderer.tr.zAngle+=dy*TOUCH_SCALE_FACTOR;
				requestRender();
		}
		preX=x;
		preY=y;
		return true;
	}
	
	private class SceneRenderer implements GLSurfaceView.Renderer{
		Triangle tr=new Triangle();
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config){
			// TODO: Implement this method
			gl.glDisable(GL10.GL_DITHER);
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
			gl.glClearColor(0,0,0,0);
			gl.glEnable(GL10.GL_DEPTH_TEST);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height){
			// TODO: Implement this method
			gl.glViewport(0,0,width,height);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			float ratio=(float)width/height;
			gl.glFrustumf(-ratio,ratio,-1,1,1,10);
		}

		@Override
		public void onDrawFrame(GL10 gl){
			// TODO: Implement this method
			//gl.glEnable(GL10.GL_CULL_FACE);
			gl.glShadeModel(GL10.GL_SMOOTH);
			/*
			gl.glEnable(GL10.GL_LIGHTING);
			
			gl.glEnable(GL10.GL_LIGHT0);
			float[] ambientParams={0.2f,0.1f,0.1f,0.7f};
			gl.glLightfv(GL10.GL_LIGHT0,GL10.GL_AMBIENT,ambientParams,0);
			float[] diffuseParams={1.0f,1.0f,1.0f,1.0f};
			gl.glLightfv(GL10.GL_LIGHT0,GL10.GL_DIFFUSE,diffuseParams,0);
			float[] specularParams={1.0f,1.0f,1.0f,1.0f};
			gl.glLightfv(GL10.GL_LIGHT0,GL10.GL_SPECULAR,specularParams,0);
			
			float[] positionParamsGreen={2,1,0,1};
			gl.glLightfv(GL10.GL_LIGHT0,GL10.GL_POSITION,positionParamsGreen,0);
			*/
			//gl.glFrontFace(GL10.GL_CCW);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glTranslatef(0,0,-1.8f);
			tr.draw(gl);
			
			
		}
		
	}
}
