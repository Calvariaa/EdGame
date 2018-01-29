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
import com.edplan.nso.ruleset.std.objects.drawables.StdSliderPathMaker;
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
	private MContext context;
	
	private DefBufferedLayer rootLayer;

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
		return true;
	}

	private int initialCount=0;
	@Override
	public void onSurfaceCreated(GL10 p1,EGLConfig p2) {
		// TODO: Implement this method
		try {
			context.initial();
			GLWrapped.initial();
			GLWrapped.depthTest.set(false);
			GLWrapped.blend.set(true);
			uiLooper=new UILooper();
			context.setUiLooper(uiLooper);
			tmer.initial();
			
			context.setContent(new TestView(context));
			
			initialCount++;
			Log.v("gl_initial","initial id: "+initialCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void onSurfaceChanged(GL10 p1,int width,int heigth) {
		// TODO: Implement this method
		rootLayer=new DefBufferedLayer(context,width,heigth);
		Log.v("ini-log","ini-scr: "+width+":"+heigth);
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
		GLCanvas2D canvas=new GLCanvas2D(rootLayer);
		canvas.prepare();
		canvas.getMProjMatrix().setOrtho(0,canvas.getWidth(),canvas.getHeight(),0,-100,100);
		if(debugUi){
			if(context.getContent()!=null){
				canvas.drawColor(Color4.gray(0.3f));
				context.getContent().draw(canvas);
			}
		}
		canvas.unprepare();
	}

}
