package com.edplan.opengl.es20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GLCanvas;
import com.edplan.framework.graphics.opengl.GLLooper;
import com.edplan.framework.graphics.opengl.batch.Texture3DBatch;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.graphics.ui.DefBufferedLayer;
import com.edplan.framework.math.Mat4;
import com.edplan.framework.math.RectF;
import com.edplan.framework.utils.MLog;
import com.edplan.mygame.R;
import com.edplan.nso.Ruleset.amodel.object.HitObject;
import com.edplan.nso.Ruleset.std.objects.StdPath;
import com.edplan.nso.Ruleset.std.objects.StdSlider;
import com.edplan.nso.Ruleset.std.objects.drawables.DrawableStdSlider;
import com.edplan.nso.filepart.PartHitObjects;
import com.edplan.nso.parser.StdBeatmapParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.edplan.framework.graphics.line.DrawLinePath;
import com.edplan.framework.graphics.opengl.drawui.DrawInfo;
import com.edplan.framework.math.Vec2;
import android.graphics.Color;
import com.edplan.framework.math.FMath;
import com.edplan.nso.NsoException;
import com.edplan.framework.graphics.opengl.batch.BitmapBatch;

public class MyTDView extends GLSurfaceView
{
	final float ANGLE_SPAN = 0.375f;

	RotateThread rthread;
	SceneRenderer mRenderer;
	public MyTDView(Context context,AttributeSet attr)
	{
		super(context,attr);
		
		this.setEGLContextClientVersion(2);
		mRenderer=new SceneRenderer();
		this.setRenderer(mRenderer);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}
	private class SceneRenderer implements GLSurfaceView.Renderer
	{
		private GLLooper looper;

		private MContext context;

		private DefBufferedLayer rootLayer;
		
		Texture3DBatch batch;
		GLTexture sldTx;
		
		
		Es20Slider tle,sld;
		Es20Triangle tr;
		Es20TextureRect txr;
		TextureTriangle txtr;
		boolean hasTest=false;
		float a=0;
		public void onDrawFrame(GL10 gl)
		{
			//清除深度缓冲与颜色缓冲
			GLES20.glClearColor(0.1f,0.1f,0.1f,0.1f);
			
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
			
			GLES20.glEnable(GLES20.GL_BLEND);
			GLES20.glBlendFunc(GLES20.GL_ONE,GLES20.GL_ONE_MINUS_SRC_ALPHA);
			
			//txr.drawSelf();
			
			
			int txW=512;
			int txH=512;
			
			txH=1080;
			txW=txH/9*16;
			
			
			int[] frameBuffer=new int[1];
			int[] depthBuffer=new int[1];
			Es20Texture btexture;
			
			GLES20.glGenFramebuffers(1,frameBuffer,0); 
			GLES20.glGenRenderbuffers(1,depthBuffer,0);
			btexture=Es20Texture.createGPUTexture(txW,txH);
			//btexture.delete();
			
			
			GLES20.glRenderbufferStorage(
				GLES20.GL_RENDERBUFFER,
				GLES20.GL_DEPTH_COMPONENT16,
				txW,txH);
			GLES20.glBindRenderbuffer(
				GLES20.GL_RENDERBUFFER,
				depthBuffer[0]);
			
			GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,frameBuffer[0]);
			GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER,GLES20.GL_COLOR_ATTACHMENT0,GLES20.GL_TEXTURE_2D,btexture.textureId,0);
			GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER,GLES20.GL_DEPTH_ATTACHMENT,GLES20.GL_RENDERBUFFER,depthBuffer[0]);
			
			int status=GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);
			
			if(status==GLES20.GL_FRAMEBUFFER_COMPLETE){
				// GLES20.glViewport(0,0,txW,txH);
				GLES20.glClearColor(1.0f,1.0f,1.0f,1.0f);
				GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
				
				GLES20.glViewport(0,0,txW,txH);
				
				if(!hasTest){
					int times=5000;
					int ttt=times;
					long time=System.currentTimeMillis();
					while(times>0){
						GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT);
						//sld.drawSelf();
						//txr.drawSelf();
						times--;
					}
					long deltaTime=System.currentTimeMillis()-time;
					Log.v("gltest",ttt+" times: "+deltaTime+"ms "+txW+"|"+txH);
					hasTest=true;
				}
				
				
				//绘制三角形对
				//tle.drawSelf();
				//tr.drawSelf();
				//sld.drawSelf();

				//GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,0);
				txr.drawSelf();
				//txtr.drawSelf();
				//Log.v("gles","draw code: "+status);
				
				//GLES20.glViewport(0,0,txW/2,txH/2);
				//GLES20.glClearColor(0.4f,0.4f,0.4f,0.4f);
				//GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
				
			}else{
				Log.e("gles","err code: "+status);
			}
			

			if(!hasComp){
				hasComp=true;
				try {
					btexture.toBitmap().compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream("/storage/emulated/0/MyDisk/WorkBench/data/test test/glBitmap.png"));
				} catch (FileNotFoundException e) {
					Log.e("tag","res",e);
				}
			}
			
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
				new Color4(1,1,1,1),
				0,
				1,
				1);
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);
			canvas.drawTexture3DBatch(batch,sldTx,0.1f,new Color4(1,0.5f,0.5f,1));
			Mat4 m=canvas.getFinalMatrix();
			//Log.v("gl_test",Arrays.toString(m.data));
			//Log.v("gl_test","draw once");
			MLog.test.vOnce("mvp Matrix","gl_test",Arrays.toString(m.data));
			MLog.test.vOnce("Canvas data","gl_test","testPng: "+testPng.getTextureId());
			canvas.unprepare();
			
			
			GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,0);
			GLES20.glViewport(0,0,lw,lh);
			//sld.drawSelf();
			//txr.drawSelf(btexture);
			GLES20.glDisable(GLES20.GL_DEPTH_TEST);
			//txr.drawSelf(new Es20Texture(testPng));
			
			btexture.delete();
			GLES20.glDeleteFramebuffers(1,frameBuffer,0);
			GLES20.glDeleteRenderbuffers(1,depthBuffer,0);
		}
		
		boolean hasComp=false;
		
		public int lw,lh;
		
		public void onSurfaceChanged(GL10 gl, final int width, final int height)
		{
			rootLayer=new DefBufferedLayer(context,width,height);
			//�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
			lw=width;
			lh=height;
        	//����GLSurfaceView�Ŀ�߱�
        	float ratio= (float) width / height;
            //����ƽ��ͶӰ
			
			
        	//MatrixState.setProjectOrtho(-ratio, ratio, -1, 1, 1, 11); 
			MatrixState.setProjectOrtho(-width/2, width/2, -height/2, height/2, -2000, 2000); 
			//MatrixState.setProjectFrustum(-width/2, width/2, -height/2, height/2, 0.01f, 1);
			
			//MatrixState.setProjectOrtho(0,width,0,height,1,100);
			//MatrixState.setProjectOrtho(0,width,0,height,1,10);
            //���ô˷������������9����λ�þ���
			MatrixState.setCamera(
				0, 0, 500f, 
				0, 0, 0f, 
				0.0f, 1.0f, 0.0f
			);
			//MatrixState.setIdenVm();
			
			//MatrixState.setCamera(
			//	width/2, height/2, 3f, 
			//	width/2, height/2, 0f, 
			//	0.0f, 1.0f, 0.0f
			//);
			
			//MatrixState.setIden();
			
			/*
			
			
			GLES20.glViewport(0, 0, width, height); 
			//设置视窗大小及位置 
			
			float ratio= (float) width / height;
            //����ƽ��ͶӰ
        	MatrixState.setProjectOrtho(-ratio, ratio, -1, 1, 1, 10); 

            //���ô˷������������9����λ�þ���
			MatrixState.setCamera(
				0, 0, 3f, 
				0, 0, 0f, 
				0f, 1.0f, 0.0f
			);
			
			*/
			
			/*
			
        	GLES20.glViewport(0, 0, width, height); 
        	//计算GLSurfaceView的宽高比
            float ratio = (float) width / height;
            //调用此方法计算产生透视投影矩阵
			Matrix.orthoM(Es20Triangle.mProjMatrix,
						0,
						0,1,
						0,1,
						-1,-2);*/
						
			
            //Matrix.frustumM(Es20Triangle.mProjMatrix, 0, -ratio, ratio, -1, 1, 1, 10);
            //调用此方法产生摄像机9参数位置矩阵
            //Matrix.setLookAtM(Es20Triangle.mVMatrix, 0, 0,0,3,0f,0f,0f,0f,1.0f,0.0f); 
			
			try {
				int w=50;
				int txw=256;
				Bitmap b=Bitmap.createBitmap(1,txw,Bitmap.Config.ARGB_8888);
				float tmp;
				for(int x=0;x<txw;x++){
					tmp=x/(float)txw;
					if(tmp>0.87f){
						b.setPixel(0,x,Color.argb(255,255,255,255));
					}else{
						int c=(int)(255*(0.7f*(1-tmp)+0.12f));
						b.setPixel(0,x,Color.argb(255,c,c,c));
					}
				}
				sldTx=GLTexture.create(b,true);
				StdBeatmapParser p=new StdBeatmapParser(new File("/storage/emulated/0/MyDisk/WorkBench/data/test beatmaps/std/Halozy - Kikoku Doukoku Jigokuraku (Hollow Wings) [Notch Hell].osu"));
				try {
					p.parse();
				} catch (NsoException e) {} catch (IOException e) {}
				int count=0;
				for(HitObject o:((PartHitObjects)(p.getHitObjectsParser().getPart())).getHitObjectList()){
					if(o instanceof StdSlider){
						StdSlider sld=(StdSlider)o;
						if(sld.getPath().getType()!=StdPath.Type.Bezier)continue;
						count++;
						if(count==244){
							//Log.v("gl_test",sld.getPath().getControlPoints().toString());
							batch=new Texture3DBatch();
							batch.setColorMixRate(0);
							DrawLinePath dlp=new DrawLinePath((new DrawableStdSlider(sld.getPath())).calculatePath());
							dlp.setBatch(batch);
							dlp.setWidth(w);
							dlp.setDrawInfo(new DrawInfo(){
									@Override
									public Vec2 toLayerPosition(Vec2 v) {
										// TODO: Implement this method
										return new Vec2(v.x*2,height-v.y*2);
									}
									Color4 c=new Color4(0,0,0,0);
									@Override
									public Color4 getMaskColor(Vec2 position) {
										// TODO: Implement this method
										return c;
									}
								});
							dlp.updateBuffers();
							//Log.v("gl_test","vcount: "+batch.getVertexCount());
							//Log.v("gl_test",(new Vec2(100,0)).theta()+"|"+(new Vec2(100,100)).theta()/FMath.PiHalf);
							Bitmap db=BitmapBatch.drawOnBitmap(batch,1000,1000);
							db.compress(Bitmap.CompressFormat.PNG,100,new FileOutputStream("/storage/emulated/0/MyDisk/WorkBench/data/ttt.png"));
							break;
						}
					}
				}
			} catch (FileNotFoundException e) {}
			
			
		}
		
		GLTexture testPng;
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			context=new MContext(getContext());
			context.initial();
			MatrixState.setInitStack();
			//设置屏幕背景色RGBA
            GLES20.glClearColor(0,0,0,1.0f);  
            //创建三角形对对象 
            tle=new Es20Slider(MyTDView.this,0,-7);
			sld=new Es20Slider(MyTDView.this,0.5f,0);
			tr=new Es20Triangle(MyTDView.this);
            //打开深度检测
			GLES20.glDisable(GLES20.GL_DEPTH_TEST);
    		
			
			Bitmap rb=BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.ic_launcher));
			//BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
			txr=new Es20TextureRect(Es20Texture.create(rb),MyTDView.this);
			//rb.recycle();
			txtr=new TextureTriangle(Es20Texture.create(rb),MyTDView.this);
			
			
			rthread=new RotateThread();
    		rthread.start();
			
			
			try {
				//context.initial();
				GLES20.glEnable(GLES20.GL_DEPTH_TEST);
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
	}
	public class RotateThread extends Thread
	{
		public boolean flag=true;
		@Override
		public void run()
		{
			while(flag)
			{
				mRenderer.tle.xAngle=mRenderer.tle.xAngle+ANGLE_SPAN;
				mRenderer.txr.rotateY+=ANGLE_SPAN;
				try
				{
					Thread.sleep(20);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
