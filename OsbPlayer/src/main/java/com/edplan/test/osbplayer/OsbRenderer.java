package com.edplan.test.osbplayer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.layer.BufferedLayer;
import com.edplan.framework.graphics.layer.DefBufferedLayer;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.graphics.opengl.GLWrapped;
import com.edplan.framework.graphics.opengl.bufferObjects.FrameBufferObject;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.ui.looper.UILooper;
import com.edplan.framework.utils.MLog;
import com.edplan.superutils.MTimer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import org.json.JSONObject;
import com.edplan.framework.graphics.opengl.MainRenderer;
import com.edplan.framework.ui.EdView;
import com.edplan.framework.ui.widget.AbsoluteLayout;
import com.edplan.framework.ui.layout.EdLayoutParam;
import com.edplan.framework.ui.layout.Param;
import com.edplan.framework.ui.drawable.RectDrawable;
import com.edplan.framework.main.MainApplication;
import com.edplan.framework.ui.widget.TestButton;
import com.edplan.framework.ui.widget.TestScroller;
import com.edplan.framework.ui.widget.LinearLayout;
import com.edplan.framework.ui.layout.Orientation;
import com.edplan.framework.ui.layout.MarginLayoutParam;
import com.edplan.framework.ui.layout.Gravity;

public class OsbRenderer extends MainRenderer
{
	JSONObject initialJson;

	public OsbRenderer(Context con,MainApplication app,JSONObject initialJson){
		super(con,app);
		this.initialJson=initialJson;
	}

	@Override
	public EdView createContentView(MContext c) {
		// TODO: Implement this method
		AbsoluteLayout layout=new AbsoluteLayout(c);
		{
			TestOsbView view=new TestOsbView(c,initialJson);
			view.setName("main");
			EdLayoutParam param=new EdLayoutParam();
			param.width=Param.MODE_MATCH_PARENT;
			param.height=Param.MODE_MATCH_PARENT;
			layout.addView(view,param);
		}
		/*
		{
			RectDrawable drawable=new RectDrawable(c);
			drawable.setColor(Color4.rgba(1,1,1,0.5f));
			EdView view=new EdView(c);
			view.setName("testWidget");
			view.setBackground(drawable);
			EdLayoutParam param=new EdLayoutParam();
			param.width=Param.MODE_MATCH_PARENT;
			param.height=Param.makeupParam(100);
			param.xoffset=500;
			layout.addView(view,param);
		}
		*/
		{
			TestButton button=new TestButton(c);
			button.setName("testButton");
			EdLayoutParam param=new EdLayoutParam();
			param.width=Param.makeupParam(100);
			param.height=Param.makeupParam(61.8f);
			param.xoffset=100;
			param.yoffset=100;
			layout.addView(button,param);
		}
		{
			TestScroller button=new TestScroller(c);
			button.setName("testScroller");
			EdLayoutParam param=new EdLayoutParam();
			param.width=Param.makeupParam(100);
			param.height=Param.MODE_MATCH_PARENT;
			param.xoffset=0;
			param.yoffset=0;
			layout.addView(button,param);
		}
		{
			RectDrawable drawable=new RectDrawable(c);
			drawable.setColor(Color4.rgba(1,1,1,0.5f));
			LinearLayout llayout=new LinearLayout(c);
			llayout.setGravity(Gravity.Center);
			llayout.setBackground(drawable);
			llayout.setOrientation(Orientation.DIRECTION_L2R);
			llayout.setPaddingTop(10);
			
			EdLayoutParam lparam=new EdLayoutParam();
			lparam.width=Param.MODE_WRAP_CONTENT;
			lparam.height=Param.MODE_WRAP_CONTENT;
			lparam.xoffset=400;
			lparam.yoffset=400;
			{
				TestButton button=new TestButton(c);
				button.setName("testButton");
				MarginLayoutParam param=new MarginLayoutParam();
				param.width=Param.makeupParam(100);
				param.height=Param.makeupParam(61.8f);
				param.marginRight=10;
				//button.setVisiblility(EdView.VISIBILITY_HIDDEN);
				llayout.addView(button,param);
			}
			{
				TestButton button=new TestButton(c);
				button.setName("testButton");
				MarginLayoutParam param=new MarginLayoutParam();
				param.width=Param.makeupParam(100);
				param.height=Param.makeupParam(61.8f);
				param.marginRight=10;
				llayout.addView(button,param);
			}
			layout.addView(llayout,lparam);
		}
		return layout;
	}
}
