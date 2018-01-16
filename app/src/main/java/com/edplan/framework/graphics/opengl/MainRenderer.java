package com.edplan.framework.graphics.opengl;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.layer.DefBufferedLayer;
import com.edplan.framework.graphics.line.DrawLinePath;
import com.edplan.framework.graphics.line.LinePath;
import com.edplan.framework.graphics.line.PathMeasurer;
import com.edplan.framework.graphics.opengl.batch.BaseColorBatch;
import com.edplan.framework.graphics.opengl.drawui.DrawInfo;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.math.FMath;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.utils.MLog;
import com.edplan.nso.ParsingBeatmap;
import com.edplan.nso.ruleset.amodel.parser.HitObjectParser;
import com.edplan.nso.ruleset.std.objects.drawables.DrawableStdSlider;
import com.edplan.nso.ruleset.std.parser.StdHitObjectParser;
import java.io.IOException;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.edplan.nso.ruleset.std.objects.StdSlider;
import com.edplan.nso.NsoException;

public class MainRenderer implements GLSurfaceView.Renderer,OnTouchListener
{
	private GLLooper looper;
	
	private MContext context;
	
	private DefBufferedLayer rootLayer;
	
	private Vec2 point=new Vec2();
	
	private Vec2 pre=new Vec2();
	
	private int pointer;
	
	private float scaleRate=1.2f;
	
	private DrawableStdSlider sld;
	
	public MainRenderer(Context con){
		context=new MContext(con);
	}

	public DefBufferedLayer getRootLayer() {
		return rootLayer;
	}

	@Override
	public boolean onTouch(View p1,MotionEvent e) {
		// TODO: Implement this method
		switch(e.getActionMasked()){
			case MotionEvent.ACTION_DOWN:
				pre.set(e.getX(),e.getY());
				point.set(pre.x,pre.y);
				break;
			case MotionEvent.ACTION_MOVE:
				Vec2 v=(new Vec2(e.getX(),e.getY())).minus(pre);
				float l=v.length();
				v.toNormal().zoom(l*scaleRate);
				//point.set(pre.x+v.x,pre.y+v.y);
				point.set(e.getX(),e.getY());
				pre.set(e.getX(),e.getY());
				break;
			case MotionEvent.ACTION_UP:
				
				break;
		}
		return true;
	}
	
	
	private GLTexture testPng;
	private GLTexture cardPng;
	private int initialCount=0;
	@Override
	public void onSurfaceCreated(GL10 p1,EGLConfig p2) {
		// TODO: Implement this method
		try {
			context.initial();
			GLWrapped.initial();
			//GLES20.glEnable(GLES20.GL_DEPTH_TEST);
			GLWrapped.depthTest.set(false);
			GLWrapped.enableBlend();
			initialCount++;
			Log.v("gl_initial","initial id: "+initialCount);
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
			StdHitObjectParser parser=new StdHitObjectParser(new ParsingBeatmap());
			try {
				StdSlider sl=(StdSlider) parser.parse("84,364,348429,6,0,B|41:353|5:288|5:288|50:270|50:270|-1:208|5:127|5:127|67:177|67:177|73:62|144:22|144:22|152:125|152:125|242:21|372:32|372:32|282:119|282:119|402:97|500:184|506:238|506:238|459:224|441:230,1,1395.00005321503,6|0,0:3|3:0,0:0:0:0:");
				//"431,64,72803,2,0,B|409:47|370:43|337:56|317:84|317:84|354:88|376:112,1,191.999994140625,8|8,0:0|0:3,0:0:0:0:");
				sld=new DrawableStdSlider(sl.getPath());
			} catch (NsoException e) {
				e.printStackTrace();
			}
			
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
	long lt=0;
	@Override
	public void onDrawFrame(GL10 p1) {
		// TODO: Implement this method
		a+=0.005;
		GLCanvas2D canvas=new GLCanvas2D(rootLayer);
		canvas.prepare();
		float c=Math.abs(a%2-1);
		canvas.drawColor(Color4.Black);
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
		/*
		canvas.drawTexture(
			testPng,
			new RectF(0,0,testPng.getWidth()/2,testPng.getHeight()),
			new RectF(0,0,500,500),
			new Color4(1,1,1,1),
			new Color4(1,1,1,1),
			0.5f,
			1,
			1f);*/
			
		
		
		
		float dh=50;
		GLPaint paint=new GLPaint();
		paint.setColorMixRate(1f);
		paint.setFinalAlpha(1f);
		paint.setMixColor(new Color4(1,1,1,1));
		paint.setPadding(10);
		paint.setVaryingColor(new Color4(1,1,1,1));
		paint.setRoundedRadius(30);
		paint.setGlowFactor
		//(0.1f);
		(0.9f+c*0.3f*0);
		paint.setGlowColor(Color4.rgba(0.3f,0.3f,0.3f,0.9f));
		
		
			
		if(lt!=0){
			canvas.getMaskMatrix().rotate(0,0,0,1);
			paint.setColorMixRate(0);
			paint.setVaryingColor(Color4.White);
			canvas.drawTexture(
				GLTexture.White,
				new RectF(0,0,1,1),
				new RectF(0,rootLayer.getHeight()/2f,rootLayer.getWidth(),20),
				paint);
			paint.setVaryingColor(Color4.rgba(1,0,0,1));
			paint.setColorMixRate(0);
			canvas.drawTexture(
				GLTexture.White,
				new RectF(0,0,1,1),
				new RectF(0,0,20,rootLayer.getHeight()/2f/18f*(System.currentTimeMillis()-lt)),
				paint);
			MLog.test.vOnce("gl bind debug","gl_test","ids: "+testPng.getTextureId()+"|"+GLTexture.White.getTextureId());
			if(GLTexture.White.getTextureId()==testPng.getTextureId()){
				MLog.test.vOnce("gl bind bug","gl_test","err id=id "+testPng.getTextureId());
			}
		}
		lt=System.currentTimeMillis();
		
		
		
		final int lcount=300;
		float xDelta=canvas.getLayer().getWidth()/(float)lcount;
		
		float baseH=300;
		
		float offset=a*2;
		
		float scale=canvas.getLayer().getWidth()/10;
		
		float[] lines=new float[lcount*4];
		
		float h=canvas.getLayer().getHeight()/2;
		
		for(int i=0;i<lcount;i++){
			lines[4*i]=xDelta*i;
			lines[4*i+1]=h;
			lines[4*i+2]=xDelta*i;
			lines[4*i+3]=h-FMath.sin(lines[4*i]/scale+offset)*baseH;
		}
		
		paint.setFinalAlpha(1f);
		paint.setStrokeWidth(10);
		paint.setColorMixRate(1);
		paint.setMixColor(Color4.White);
		canvas.drawLines(lines,paint);
		
		//c=0.3f;
		
		
		LinePath path=sld.calculatePath();
		path.setWidth(40);
		/*
		path.setWidth(90);
		path.add(new Vec2(100,100));
		path.add(new Vec2(200,500));
		path.add(new Vec2(100,900));
		path.add(new Vec2(1000,800));*/
		
		path.measure();
		PathMeasurer m=path.getMeasurer();
		float let=path.getMeasurer().maxLength();
		MLog.test.vOnce("let","path-test",let+"");
		
		
		DrawLinePath dp=new DrawLinePath(path);
		dp.setDrawInfo(new DrawInfo(){
				@Override
				public Vec2 toLayerPosition(Vec2 v) {
					// TODO: Implement this method
					return v;
				}

				@Override
				public Color4 getMaskColor(Vec2 position) {
					// TODO: Implement this method
					return Color4.rgba(1,0,0,1);
				}
			});
		
		final BaseColorBatch batch=new BaseColorBatch();
		dp.addToBatch(batch);
		
		DrawLinePath dp2=new DrawLinePath(path.cutPath(2*c*let/3,(1+2*c)*let/3));
		dp2.setDrawInfo(new DrawInfo(){
				@Override
				public Vec2 toLayerPosition(Vec2 v) {
					// TODO: Implement this method
					return v;
				}

				@Override
				public Color4 getMaskColor(Vec2 position) {
					// TODO: Implement this method
					return Color4.rgba(1,1,1,1);
				}
			});
		dp2.addToBatch(batch);
		
		
		
		LinePath path2=new LinePath();
		path2.setWidth(15);
		path2.add(m.atLength(2*let*c/3));
		path2.add(m.atLength((1+2*c)*let/3));
		DrawLinePath dp3=new DrawLinePath(path2);
		dp3.setDrawInfo(new DrawInfo(){
				@Override
				public Vec2 toLayerPosition(Vec2 v) {
					// TODO: Implement this method
					return v;
				}

				@Override
				public Color4 getMaskColor(Vec2 position) {
					// TODO: Implement this method
					return Color4.rgba(0.5f,0.5f,0.5f,1);
				}
			});
		
		dp3.addToBatch(batch);
		
		canvas.drawColorBatch(paint,batch);
		
		
		paint.setFinalAlpha(0.5f);
		canvas.drawRoundedRectTexture(
			cardPng,
			new RectF(0,0,80,80),
			new RectF(point.x-40,point.y-40,80,80),
			paint);
		
		
		/*canvas.drawLines(new float[]{100,100,500,500,500,500,900,900},paint);
		
		MLog.test.runOnce("path-test", new Runnable(){
				@Override
				public void run() {
					// TODO: Implement this method
					Bitmap bbb=BitmapBatch.drawOnBitmap(batch,1000,1000);
					try {
						bbb.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream("/storage/emulated/0/MyDisk/test.png"));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			});*/
		
		
		/*
		
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
			paint);*/
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
