package com.edplan.framework.graphics.opengl;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.layer.BufferedLayer;
import com.edplan.framework.graphics.layer.DefBufferedLayer;
import com.edplan.framework.graphics.opengl.bufferObjects.FrameBufferObject;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.test.TestStaticData;
import com.edplan.framework.ui.looper.UILooper;
import com.edplan.framework.utils.MLog;
import com.edplan.superutils.MTimer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.test.performance.Tracker;
import com.edplan.framework.ui.ViewRoot;
import com.edplan.framework.main.MainApplication;

public abstract class MainRenderer implements GLSurfaceView.Renderer,OnTouchListener
{
	private MContext context;
	
	private MainApplication app;
	
	private DefBufferedLayer rootLayer;

	private UILooper uiLooper;
	
	private ViewRoot viewRoot;
	
	private int glVersion;
	
	public MainRenderer(Context con,MainApplication app){
		context=new MContext(con);
		this.app=app;
		viewRoot=new ViewRoot(context);
	}

	public void setGlVersion(int glVersion) {
		this.glVersion=glVersion;
	}

	public int getGlVersion() {
		return glVersion;
	}
	
	public abstract EdView createContentView(MContext c);

	public DefBufferedLayer getRootLayer() {
		return rootLayer;
	}

	@Override
	public boolean onTouch(View p1,MotionEvent e) {
		// TODO: Implement this method
		//Log.v("thread","touch-thread: "+Thread.currentThread());
		TestStaticData.touchPosition.set(e.getX(),e.getY());
		return true;
	}

	private int initialCount=0;
	@Override
	public void onSurfaceCreated(GL10 p1,EGLConfig p2) {
		// TODO: Implement this method
		try {
			GLWrapped.initial(glVersion);
			context.initial();
			GLWrapped.depthTest.set(false);
			GLWrapped.blend.setEnable(true);
			uiLooper=new UILooper();
			context.setUiLooper(uiLooper);
			tmer.initial();
			initialCount++;
			Log.v("gl_initial","initial id: "+initialCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void onSurfaceChanged(GL10 p1,int width,int heigth) {
		// TODO: Implement this method
		context.setDisplaySize(width,heigth);
		viewRoot.onChange(width,heigth);
		rootLayer=new DefBufferedLayer(context,width,heigth);
		rootLayer.checkChange();
		rootLayer.prepare();
		
		app.onGLCreate();
		
		for(int i=0;i<10;i++){
			BufferedLayer.DEF_FBOPOOL.saveFBO(FrameBufferObject.create(1000,1000,true));
		}
		viewRoot.setContentView(createContentView(context));
		viewRoot.getContentView().onCreate();
		
		Log.v("ini-log","ini-scr: "+width+":"+heigth);
	}

	float a=0;
	long lt=0;
	boolean debugUi=true;
	MTimer tmer=new MTimer();
	@Override
	public void onDrawFrame(GL10 p1) {
		// TODO: Implement this method
		MLog.test.vOnce("thread","thread","draw-thread: "+Thread.currentThread());
		tmer.refresh();
		Tracker.reset();
		GLWrapped.onFrame();
		uiLooper.loop(tmer.getDeltaTime());
		GLCanvas2D canvas=new GLCanvas2D(rootLayer);
		canvas.prepare();
		canvas.getMProjMatrix().setOrtho(0,canvas.getWidth(),canvas.getHeight(),0,-100,100);
		if(debugUi){
			canvas.drawColor(Color4.gray(0.0f));
			viewRoot.onNewFrame(canvas,tmer.getDeltaTime());
		}
		canvas.unprepare();
	}

}
