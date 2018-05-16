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

public class ViewRoot
{
	public static final int FLAG_INVALIDATE_MEASURE=1<<0;
	
	public static final int FLAG_INVALIDATE_LAYOUT=1<<1;
	
	public static final int FLAG_INVALIDATE_DRAW=1<<2;
	
	
	
	private MContext context;
	
	private EdView contentView;
	
	private int invalidateFlag;
	
	private int frameInvalidateState;
	
	private boolean alwaysInvalidateMeasure=false;
	
	private boolean alwaysInvalidateLayout=false;
	
	private boolean alwaysInvalidateDraw=true;
	
	public ViewRoot(MContext c){
		this.context=c;
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
		Tracker.InvalidateMeasure.watch();
		long wm=
			EdMeasureSpec.makeupMeasureSpec(
				context.getDisplayWidth(),
				EdMeasureSpec.MODE_AT_MOST);
		long hm=
			EdMeasureSpec.makeupMeasureSpec(
				context.getDisplayHeight(),
				EdMeasureSpec.MODE_AT_MOST);
		MeasureCore.measureChild(contentView,0,0,wm,hm);
		Tracker.InvalidateMeasure.end();
		/*
		Log.v("ui-layout",Long.toBinaryString(((long)1)<<32)+" "+Long.toBinaryString(EdMeasureSpec.MODE_MASK)+":"+Long.toBinaryString(EdMeasureSpec.SIZE_MASK));
		Log.v("ui-layout","measureSpec : "+wm+","+hm+" "+EdMeasureSpec.toString(wm)+","+EdMeasureSpec.toString(hm));
		Log.v("ui-layout","measure : "+contentView.getMeasuredWidth()+","+contentView.getMeasuredHeight()+" cost: "+Tracker.InvalidateMeasure.totalTimeMS+"ms");
		*/
	}
	
	protected void postLayout(){
		contentView.layout(0,0,contentView.getMeasuredWidth(),contentView.getMeasuredHeight());
	}
	
	public void onNewFrame(BaseCanvas canvas,double deltaTime){
		frameInvalidateState=0;
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
		
		
		
		if(contentView!=null){
			contentView.onDraw(canvas);
		}
	}
}
