package com.edplan.framework.graphics.opengl;
import android.opengl.GLSurfaceView;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.ui.DefBufferedLayer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import java.io.IOException;
import com.edplan.framework.math.RectF;
import android.util.Log;

public class MainRenderer implements GLSurfaceView.Renderer
{
	private GLLooper looper;
	
	private MContext context;
	
	private DefBufferedLayer rootLayer;
	
	public MainRenderer(Context con){
		context=new MContext(con);
	}

	public DefBufferedLayer getRootLayer() {
		return rootLayer;
	}
	
	
	private GLTexture testPng;
	@Override
	public void onSurfaceCreated(GL10 p1,EGLConfig p2) {
		// TODO: Implement this method
		try {
			context.initial();
			testPng=
				GLTexture.decodeStream(
					context
						 .getAssetResource()
						 .getTextureResource()
						 .openInput("ic_launcher.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onSurfaceChanged(GL10 p1,int width,int heigth) {
		// TODO: Implement this method
		rootLayer=new DefBufferedLayer(context,width,heigth);
	}

	float a=0;
	@Override
	public void onDrawFrame(GL10 p1) {
		// TODO: Implement this method
		a+=0.01;
		GLCanvas canvas=new GLCanvas(rootLayer);
		canvas.prepare();
		float c=Math.abs(a%2-1);
		canvas.drawColor(new Color4(c,c,c,1.0f));
		canvas.clearDepthBuffer();
		canvas.drawTexture(
			testPng,
			new RectF(0,0,testPng.getWidth(),testPng.getHeight()),
			new RectF(0,0,testPng.getWidth(),testPng.getHeight()),
			new Color4(1,1,1,1),
			new Color4(0,0,0,0),
			0,
			0,
			1);
		
		//Log.v("gl_test","draw once");
		canvas.unprepare();
	}

}
