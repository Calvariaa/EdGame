package com.edplan.framework.graphics.opengl;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.graphics.ui.DefBufferedLayer;
import com.edplan.framework.math.Mat4;
import com.edplan.framework.math.RectF;
import com.edplan.framework.utils.MLog;
import java.io.IOException;
import java.util.Arrays;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.edplan.framework.math.FMath;

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
	private GLTexture cardPng;
	@Override
	public void onSurfaceCreated(GL10 p1,EGLConfig p2) {
		// TODO: Implement this method
		try {
			context.initial();
			//GLES20.glEnable(GLES20.GL_DEPTH_TEST);
			GLWrapped.setDepthTest(false);
			GLWrapped.enableBlend();
			testPng=
				GLTexture.decodeStream(
					context
						 .getAssetResource()
						 .getTextureResource()
						 .openInput("ic_launcher.png"));
			cardPng=GLTexture.decodeResource(context.getAssetResource().getTextureResource(),"card.jpg");
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
		a+=0.005;
		GLCanvas2D canvas=new GLCanvas2D(rootLayer);
		canvas.prepare();
		float c=Math.abs(a%2-1);
		canvas.drawColor(new Color4(c,c,c,1.0f));
		canvas.clearDepthBuffer();
		
		canvas.save();
		canvas.getMaskMatrix().translate(500+testPng.getWidth()/2,500+testPng.getHeight()/2,0).rotate(c*90,0,0,1);
		//.post((new Mat4()).translate(10,10,0));
		canvas.drawTexture(
			testPng,
			new RectF(0,0,testPng.getWidth(),testPng.getHeight()),
			new RectF(-testPng.getWidth()/2,-testPng.getWidth()/2,testPng.getWidth(),testPng.getHeight()),
			new Color4(1,1,1,1),
			new Color4(1,1,1,1),
			0f,
			1,
			1f);
		
		canvas.restore();
		canvas.drawTexture(
			testPng,
			new RectF(0,0,testPng.getWidth()/2,testPng.getHeight()),
			new RectF(0,0,500,500),
			new Color4(1,1,1,1),
			new Color4(1,1,1,1),
			1,
			1,
			1f);
		
		GLPaint paint=new GLPaint();
		paint.setColorMixRate(0);
		paint.setFinalAlpha(0.5f);
		paint.setMixColor(new Color4(1,1,1,1));
		paint.setPadding(c*cardPng.getHeight());
		paint.setVaryingColor(new Color4(0,0,0,0));
		
		canvas.drawRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),cardPng.getHeight()),
			new RectF(100,100,cardPng.getWidth()*2,cardPng.getHeight()*2),
			paint);
		/*
		paint.setPadding(10);
		canvas.drawRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),cardPng.getHeight()),
			new RectF(1000,500,cardPng.getWidth()*2,cardPng.getHeight()*2),
			paint);*/
		
		MLog.test.vOnce("jpgwh","jpgwh","w:"+cardPng.getWidth()+" h:"+cardPng.getHeight());
		//Mat4 m=canvas.getFinalMatrix();
		//Log.v("gl_test",Arrays.toString(m.data));
		//Log.v("gl_test","draw once");
		//MLog.test.vOnce("mvp Matrix","gl_test",Arrays.toString(m.data));
		//MLog.test.vOnce("Canvas data","gl_test","testPng: "+testPng.getTextureId());
		
		canvas.unprepare();
	}

}
