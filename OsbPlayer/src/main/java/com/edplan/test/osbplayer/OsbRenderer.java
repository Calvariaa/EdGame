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

public class OsbRenderer extends MainRenderer
{
	JSONObject initialJson;

	public OsbRenderer(Context con,JSONObject initialJson){
		super(con);
		this.initialJson=initialJson;
	}

	@Override
	public EdView createContentView(MContext c) {
		// TODO: Implement this method
		return new TestOsbView(c,initialJson);
	}
}
