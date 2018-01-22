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
import android.graphics.Bitmap;
import android.graphics.Color;
import com.edplan.framework.graphics.opengl.batch.Texture3DBatch;
import com.edplan.nso.ruleset.amodel.playing.PlayField;
import com.edplan.framework.ui.looper.UILooper;
import com.edplan.superutils.MTimer;
import com.edplan.framework.test.TestView;

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
	
	private UILooper uiLooper;
	
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
	private GLTexture sliderTex;
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
			uiLooper=new UILooper();
			context.setUiLooper(uiLooper);
			tmer.initial();
			
			context.setContent(new TestView(context));
			
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
				StdSlider sl=(StdSlider) parser.parse(
				//"396,120,5034,6,0,B|452:84|452:84|388:44|320:32|320:32|308:68|316:104|316:104|464:132|440:236|408:296|352:300|280:300|236:248|216:176|288:132|392:124|376:348|376:348|468:304|484:224,1,1049.99996795654,4|0,3:2|0:2,0:2:0:0:");
				"256,273,1724,6,0,B|256:340|256:340|208:340|208:340|208:332|188:320|188:320|205:304|215:280|215:280|240:248|248:212|248:212|252:192|248:156|248:156|236:156|236:156|248:156|248:156|248:116|248:116|244:88|216:72|216:72|236:64|256:60|256:60|260:28|256:25|256:25|252:28|256:60|256:60|276:64|296:72|296:72|268:88|264:116|264:116|264:156|264:156|276:156|276:156|264:156|264:156|260:192|264:212|264:212|272:248|298:280|298:280|312:304|324:320|324:320|312:332|312:340|312:340|256:340|256:340|256:272,1,1049.99996795654,4|0,3:2|0:2,0:2:0:0:");
				//"84,364,348429,6,0,B|41:353|5:288|5:288|50:270|50:270|-1:208|5:127|5:127|67:177|67:177|73:62|144:22|144:22|152:125|152:125|242:21|372:32|372:32|282:119|282:119|402:97|500:184|506:238|506:238|459:224|441:230,1,1395.00005321503,6|0,0:3|3:0,0:0:0:0:");
				//"431,64,72803,2,0,B|409:47|370:43|337:56|317:84|317:84|354:88|376:112,1,191.999994140625,8|8,0:0|0:3,0:0:0:0:");
				sld=new DrawableStdSlider(sl.getPath());
			} catch (NsoException e) {
				e.printStackTrace();
			}
			
			Bitmap bmp=Bitmap.createBitmap(500,1,Bitmap.Config.ARGB_8888);
			for(int x=0;x<bmp.getWidth();x++){
				float v=x/(float)bmp.getWidth();
				float theta=(float)Math.acos(v);
				
				int gr=(int)(80*(1-v));
				if(v<0.9f){
					//int gr=(int)(100*(1-v));
					bmp.setPixel(x,0,Color.argb(255,gr,gr,gr));
				}else{
					bmp.setPixel(x,0,Color4.mix(Color4.gray(1),Color4.argb255(Color.argb(255,gr,gr,gr)),1-FMath.min((v-0.9f)*10*10,1)).toIntBit());
				}
			}
			sliderTex=GLTexture.create(bmp,true);
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
	boolean debugUi=true;
	MTimer tmer=new MTimer();
	@Override
	public void onDrawFrame(GL10 p1) {
		// TODO: Implement this method
		tmer.refresh();
		uiLooper.loop(tmer.getDeltaTime());
		
		a+=0.005;
		GLCanvas2D canvas=new GLCanvas2D(rootLayer);
		canvas.prepare();
		
		if(debugUi){
			if(context.getContent()!=null)context.getContent().draw(canvas);
			canvas.unprepare();
			return;
		}
		
		
		
		
		
		
		float c=Math.abs(a%2-1);
		canvas.drawColor(Color4.gray(0.2f));
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
		
		
		//转换到osu field
		
		canvas.save();
		float osuScale=canvas.getHeight()/PlayField.BASE_Y;
		canvas.translate(canvas.getWidth()/2-PlayField.BASE_X/2*osuScale,0);
		canvas.scale(osuScale);
		canvas.translate(PlayField.PADDING_X,PlayField.PADDING_Y);
		canvas.clip(new Vec2(PlayField.CANVAS_SIZE_X,PlayField.CANVAS_SIZE_Y));
		
		LinePath path=sld.calculatePath();
		float cs=4;
		float sscale=(1.0f-0.3f*(cs-5)/5)/2;
		path.setWidth(64*sscale);
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
		
		final Texture3DBatch batch=new Texture3DBatch();
		batch.setColorMixRate(0);
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
		//dp2.addToBatch(batch);
		
		
		
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
		
		//dp3.addToBatch(batch);
		
		//canvas.drawColorBatch(paint,batch);
		
		
		//canvas.save();
		//canvas.getMProjMatrix().translate(300,100,0).scale(2,2,1);
		paint.setMixColor(Color4.rgb(1,0.5f,0.5f));
		GLWrapped.depthTest.save();
		GLWrapped.depthTest.set(true);
		canvas.drawTexture3DBatch(batch,sliderTex,1,new Color4(1,0.8f,0.8f,1));
		GLWrapped.depthTest.restore();
		//canvas.drawtexture3
		//canvas.restore();
		
		canvas.restore();
		
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
