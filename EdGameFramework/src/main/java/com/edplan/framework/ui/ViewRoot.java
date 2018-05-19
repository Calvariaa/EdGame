package com.edplan.framework.ui;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.BaseCanvas;
import com.edplan.framework.utils.BitUtil;
import com.edplan.framework.ui.layout.EdMeasureSpec;
import android.util.Log;
import com.edplan.framework.test.performance.Tracker;
import com.edplan.framework.ui.layout.EdLayoutParam;
import com.edplan.framework.ui.layout.Gravity;
import com.edplan.framework.ui.layout.Param;
import com.edplan.framework.ui.layout.MeasureCore;
import android.view.MotionEvent;
import com.edplan.framework.ui.inputs.NativeInputQuery;
import java.util.List;
import com.edplan.framework.input.EdMotionEvent;

public class ViewRoot
{
	public static final int FLAG_INVALIDATE_MEASURE=1<<0;
	
	public static final int FLAG_INVALIDATE_LAYOUT=1<<1;
	
	public static final int FLAG_INVALIDATE_DRAW=1<<2;
	
	private MContext context;
	
	private EdView contentView;
	
	private int invalidateFlag;
	
	private int frameInvalidateState;
	
	private boolean alwaysInvalidateMeasure=true;
	
	private boolean alwaysInvalidateLayout=true;
	
	private boolean alwaysInvalidateDraw=true;
	
	private NativeInputQuery nativeInputManager=new NativeInputQuery();
	
	public ViewRoot(MContext c){
		this.context=c;
	}
	
	public void postNativeEvent(MotionEvent event){
		nativeInputManager.postEvent(event);
	}

	
	protected void handlerInputs(){
		List<EdMotionEvent> events=nativeInputManager.getQuery();
	}
	
	public void setContentView(EdView contentView) {
		this.contentView=contentView;
		if(contentView.getLayoutParam()==null){
			EdLayoutParam param=new EdLayoutParam();
			param.gravity=Gravity.None;
			param.width=Param.makeupParam(0,Param.MATCH_PARENT);
			param.height=Param.makeupParam(0,Param.MATCH_PARENT);
			contentView.setLayoutParam(param);
		}
	}

	public EdView getContentView() {
		return contentView;
	}
	
	public void onChange(int w,int h){
		invalidateFlag|=FLAG_INVALIDATE_MEASURE;
		invalidateFlag|=FLAG_INVALIDATE_LAYOUT;
		invalidateFlag|=FLAG_INVALIDATE_DRAW;
	}
	
	protected void postMeasure(){
		long wm=
			EdMeasureSpec.makeupMeasureSpec(
				context.getDisplayWidth(),
				EdMeasureSpec.MODE_AT_MOST);
		long hm=
			EdMeasureSpec.makeupMeasureSpec(
				context.getDisplayHeight(),
				EdMeasureSpec.MODE_AT_MOST);
		MeasureCore.measureChild(contentView,0,0,wm,hm);
	}
	
	protected void postLayout(){
		contentView.layout(0,0,contentView.getMeasuredWidth(),contentView.getMeasuredHeight());
	}
	
	protected void checkInvalidate(){
		Tracker.InvalidateMeasureAndLayout.watch();
		if(alwaysInvalidateMeasure
		   ||BitUtil.match(invalidateFlag,FLAG_INVALIDATE_MEASURE)
		   ){
			postMeasure();
			invalidateFlag&=~FLAG_INVALIDATE_MEASURE;
			frameInvalidateState|=FLAG_INVALIDATE_MEASURE;
		}

		if(alwaysInvalidateLayout
		   ||BitUtil.match(invalidateFlag,FLAG_INVALIDATE_LAYOUT)
		   ){
			postLayout();
			invalidateFlag&=~FLAG_INVALIDATE_LAYOUT;
			frameInvalidateState|=FLAG_INVALIDATE_LAYOUT;
		}
		Tracker.InvalidateMeasureAndLayout.end();
	}
	
	public void onNewFrame(BaseCanvas canvas,double deltaTime){
		frameInvalidateState=0;
		
		checkInvalidate();
		handlerInputs();
		checkInvalidate();
		
		
		if(contentView!=null){
			Tracker.DrawUI.watch();
			contentView.onDraw(canvas);
			Tracker.DrawUI.end();
		}
	}
}
