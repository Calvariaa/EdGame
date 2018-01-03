package com.edplan.framework.graphics.opengl;
import android.content.Context;
import android.opengl.GLSurfaceView;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.layer.DefBufferedLayer;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.math.RectF;
import com.edplan.framework.utils.MLog;
import java.io.IOException;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

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
			GLWrapped.depthTest.set(false);
			GLWrapped.enableBlend();
			testPng=
				GLTexture.decodeStream(
					context
						 .getAssetResource()
						 .getTextureResource()
						 .openInput
						 ("ic_launcher.png"));
			cardPng=GLTexture.decodeResource(context.getAssetResource().getTextureResource(),
			"card.jpg");
			//"default-bg.png");
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
		a+=0.02;
		GLCanvas2D canvas=new GLCanvas2D(rootLayer);
		canvas.prepare();
		float c=Math.abs(a%2-1);
		canvas.drawColor(Color4.White);
		//new Color4(c,c,c,1.0f));
		canvas.clearDepthBuffer();
		
		//canvas.getMProjMatrix().post(Mat4.createIdentity().setOrtho(0,2,2,2,-100,100));
		
		canvas.save();
		canvas.getMaskMatrix().translate(500+testPng.getWidth()/2,500+testPng.getHeight()/2,0).rotate(c*90,0,0,1);
		//.post((new Mat4()).translate(10,10,0));
		canvas.drawTexture(
			testPng,
			new RectF(0,0,testPng.getWidth(),testPng.getHeight()),
			new RectF(-testPng.getWidth()/2,-testPng.getHeight()/2,testPng.getWidth(),testPng.getHeight()),
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
			0.5f,
			1,
			1f);
		
		float dh=50;
		GLPaint paint=new GLPaint();
		paint.setColorMixRate(0f);
		paint.setFinalAlpha(1f);
		paint.setMixColor(new Color4(1,1,1,1));
		paint.setPadding(10);
		paint.setVaryingColor(new Color4(1,1,1,1));
		paint.setRoundedRadius(30);
		paint.setGlowFactor
		//(0.1f);
		(0.9f+c*0.3f*0);
		paint.setGlowColor(Color4.rgba(0,0,0,0.7f));
		
		/*canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),cardPng.getHeight()),
			new RectF(500,100,cardPng.getWidth()*1.5f,cardPng.getHeight()*1.5f),
			paint);*/
		canvas.getMaskMatrix().rotate(3,0,0,1);
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100,cardPng.getWidth()*3f,dh*3f),
			paint);
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100+dh*3f,cardPng.getWidth()*3f,dh*3f),
			paint);
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100+dh*3f*2,cardPng.getWidth()*3f,dh*3f),
			paint);
			
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100,cardPng.getWidth()*3f,dh*3f),
			paint);
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100+dh*3f,cardPng.getWidth()*3f,dh*3f),
			paint);
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100+dh*3f*2,cardPng.getWidth()*3f,dh*3f),
			paint);
		
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100,cardPng.getWidth()*3f,dh*3f),
			paint);
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100+dh*3f,cardPng.getWidth()*3f,dh*3f),
			paint);
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100+dh*3f*2,cardPng.getWidth()*3f,dh*3f),
			paint);
			
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100,cardPng.getWidth()*3f,dh*3f),
			paint);
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100+dh*3f,cardPng.getWidth()*3f,dh*3f),
			paint);
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100+dh*3f*2,cardPng.getWidth()*3f,dh*3f),
			paint);
			
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100,cardPng.getWidth()*3f,dh*3f),
			paint);
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100+dh*3f,cardPng.getWidth()*3f,dh*3f),
			paint);
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100+dh*3f*2,cardPng.getWidth()*3f,dh*3f),
			paint);
			
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100,cardPng.getWidth()*3f,dh*3f),
			paint);
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100+dh*3f,cardPng.getWidth()*3f,dh*3f),
			paint);
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,cardPng.getWidth(),dh),
			new RectF(500,100+dh*3f*2,cardPng.getWidth()*3f,dh*3f),
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
