package com.edplan.framework.ui;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.BaseCanvas;
import com.edplan.framework.ui.drawable.EdDrawable;
import com.edplan.framework.ui.inputs.EdMotionEvent;
import com.edplan.framework.ui.inputs.HoverEvent;
import com.edplan.framework.ui.inputs.ScrollEvent;
import com.edplan.framework.ui.layout.EdLayoutParam;
import com.edplan.framework.ui.layout.EdMeasureSpec;
import com.edplan.framework.ui.layout.LayoutException;
import com.edplan.superutils.classes.advance.IRunnableHandler;

public class EdView implements IRunnableHandler
{
	protected static int CUSTOM_INDEX=0;

	public static final int VISIBILITY_SHOW=1;

	public static final int VISIBILITY_HIDDEN=2;

	public static final int VISIBILITY_GONE=3;
	
	private EdAbstractViewGroup parent;

	private String name;

	private MContext context;

	private EdDrawable background;

	private int visiblility=VISIBILITY_SHOW;

	private EdLayoutParam layoutParam;

	private float measuredWidth,measuredHeight;

	private float minWidth,minHeight;

	private float leftToParent,rightToParent,topToParent,bottomToParent;

	private boolean hasCreated=false;
	
	private boolean pressed=false;
	
	/**
	 *当前view是否是焦点view，焦点view会在处理滚动，点击等事件时有较高的优先级
	 */
	private boolean focus=false;
	
	/**
	 *设置是否处理点击事件（事件开始结束均在view范围内，且事件时间不是太久)
	 */
	private boolean clickable=false;
	
	/**
	 *设置是否处理长摁事件（事件开始结束均在view范围内，且事件时间比较久)
	 */
	private boolean longclickable=false;
	
	/**
	 *是否接受滚动，包含了两个方向相关的信息。
	 *具体的取值在ScrollEvent.java
	 */
	private int scrollableFlag;
	
	public EdView(MContext context){
		this.context=context;
		if(!checkCurrentThread()){
			throw new RuntimeException("you can only create a view in main thread!");
		}
		initialName();
	}
	
	/**
	 *如果当前view在滚动，停止滚动
	 */
	public void stopScrolling(){
		
	}

	public void setPressed(boolean pressed){
		this.pressed=pressed;
	}

	public boolean isPressed(){
		return pressed;
	}

	public ViewRoot getViewRoot(){
		return getContext().getViewRoot();
	}

	public void setScrollableFlag(int scrollableFlag){
		this.scrollableFlag=scrollableFlag;
	}

	public int getScrollableFlag(){
		return scrollableFlag;
	}

	public void setLongclickable(boolean longclickable){
		this.longclickable=longclickable;
	}

	public boolean isLongclickable(){
		return longclickable;
	}

	public void setClickable(boolean clickable){
		this.clickable=clickable;
	}

	public boolean isClickable(){
		return clickable;
	}
	
	public boolean hasCreated(){
		return hasCreated;
	}
	
	public float getTop(){
		return topToParent;
	}
	
	public float getBottom(){
		return bottomToParent;
	}
	
	public float getLeft(){
		return leftToParent;
	}
	
	public float getRight(){
		return rightToParent;
	}
	
	public float getWidth(){
		return getRight()-getLeft();
	}

	public float getHeight(){
		return getBottom()-getTop();
	}
	
	public float getPaddingLeft(){
		return 0;
	}

	public float getPaddingTop(){
		return 0;
	}

	public float getPaddingRight(){
		return 0;
	}

	public float getPaddingBottom(){
		return 0;
	}

	public float getPaddingHorizon(){
		return getPaddingLeft()+getPaddingRight();
	}

	public float getPaddingVertical(){
		return getPaddingTop()+getPaddingBottom();
	}

	public float getMeasuredWidth(){
		return measuredWidth;
	}

	public float getMeasuredHeight(){
		return measuredHeight;
	}

	public void setLayoutParam(EdLayoutParam layoutParam){
		this.layoutParam=layoutParam;
	}

	public EdLayoutParam getLayoutParam(){
		return layoutParam;
	}

	public boolean checkCurrentThread(){
		return Thread.currentThread()==getContext().getMainThread();
	}

	public void setParent(EdAbstractViewGroup parent){
		this.parent=parent;
	}

	public EdAbstractViewGroup getParent(){
		return parent;
	}

	public void setBackground(EdDrawable background){
		this.background=background;
	}

	public EdDrawable getBackground(){
		return background;
	}

	@Override
	public void post(Runnable r){
		// TODO: Implement this method
		getContext().runOnUIThread(r);
	}

	@Override
	public void post(Runnable r,int delayMS){
		// TODO: Implement this method
		getContext().runOnUIThread(r,delayMS);
	}

	public void setContext(MContext context){
		this.context=context;
	}

	public MContext getContext(){
		return context;
	}

	private void initialName(){
		name="@index/"+CUSTOM_INDEX;
		CUSTOM_INDEX++;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setVisiblility(int visiblility){
		this.visiblility=visiblility;
	}

	public int getVisiblility(){
		return visiblility;
	}

	public void onCreate(){
		hasCreated=true;
	}

	public void onDraw(BaseCanvas canvas){
		defaultDraw(canvas);
	}

	public void defaultDraw(BaseCanvas canvas){
		drawBackground(canvas);
	}
	
	protected void drawBackground(BaseCanvas canvas){
		if(background!=null){
			int s=canvas.save();
			//canvas.getMaskMatrix().post(background.getTranslationMatrix());
			background.draw(canvas);
			canvas.restoreToCount(s);
		}
	}


	private boolean hasSetMeasureDimension=false;
	public final void measure(long widthSpec,long heightSpec){
		hasSetMeasureDimension=false;
		onMeasure(widthSpec,heightSpec);
		if(!hasSetMeasureDimension){
			throw new LayoutException("you must call setMeasureDimension in onMeasure Method, pleas check class : "+getClass().toString());
		}
	}

	protected void onMeasure(long widthSpec,long heightSpec){
		setMeasuredDimensition(
			getDefaultSize(getSuggestedMinWidth(),widthSpec),
			getDefaultSize(getSuggestedMinHeight(),heightSpec));
	}

	public float getSuggestedMinWidth(){
		return (background==null)?minWidth:Math.max(minWidth,background.getMinWidth());
	}

	public float getSuggestedMinHeight(){
		return (background==null)?minHeight:Math.max(minHeight,background.getMinHeight());
	}

	public static float getDefaultSize(float size,long spec){
		float r=size;
		int mode=EdMeasureSpec.getMode(spec);
		switch(mode){
			case EdMeasureSpec.MODE_NONE:
				r=size;
				break;
			case EdMeasureSpec.MODE_DEFINEDED:
			case EdMeasureSpec.MODE_AT_MOST:
				r=EdMeasureSpec.getSize(spec);
				break;
		}
		return r;
	}

	protected final void setMeasuredDimensition(float w,float h){
		measuredWidth=w;
		measuredHeight=h;
		hasSetMeasureDimension=true;
		//Log.v("setMeasureDimension","view:"+getName()+" "+w+","+h);
	}

	/**
	 *四个参数分别为子view到父view坐标系的距离
	 */
	protected void layout(float left,float top,float right,float bottom){
		boolean hasChanged=setFrame(left,top,right,bottom);
		if(hasChanged){
			onLayout(hasChanged,left,top,right,bottom);
		}
	}

	protected void onLayout(boolean changed,float left,float top,float right,float bottom){

	}

	protected final boolean setFrame(float left,float top,float right,float bottom){
		boolean hasChanged=false;
		if(leftToParent!=left||topToParent!=top||rightToParent!=right||bottomToParent!=bottom){
			hasChanged=true;
			this.leftToParent=left;
			this.topToParent=top;
			this.rightToParent=right;
			this.bottomToParent=bottom;
		}
		return hasChanged;
	}
	
	/**
	 *处理原始点击事件
	 */
	public boolean onMotionEvent(EdMotionEvent e){
		
		return false;
	}
	
	/**
	 *处理悬浮事件
	 */
	public boolean onHoverEvent(HoverEvent event){
		return false;
	}
	
	
	/**
	 *下面这些都必须获取焦点后再被应用
	 */
	
	/**
	 *点击事件开始时被调用，只有被设置为clickable=true才会被调用
	 */
	public boolean onStartClick(){
		return false;
	}
	
	/**
	 *对应点击事件被触发
	 */
	public boolean onClickEvent(){
		return false;
	}

	/**
	 *对应长摁事件被触发
	 */
	public boolean onLongClickEvent(){
		return false;
	}
	
	public boolean onClickEventCancel(){
		return false;
	}

	/**
	 *处理滚动事件
	 */
	public boolean onScroll(ScrollEvent event){
		return false;
	}
}
