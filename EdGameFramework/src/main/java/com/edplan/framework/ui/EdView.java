package com.edplan.framework.ui;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.input.EdMotionEvent;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.ui.drawable.EdDrawable;
import com.edplan.framework.ui.uiobjs.DrawRequestParam;
import com.edplan.framework.ui.uiobjs.Visibility;
import com.edplan.superutils.classes.advance.IRunnableHandler;
import com.edplan.framework.ui.layout.MeasureCore;
import com.edplan.framework.ui.layout.LayoutParam;
import com.edplan.framework.graphics.opengl.BaseCanvas;
import com.edplan.framework.ui.layout.EdMeasureSpec;

public class EdView implements IRunnableHandler
{
	protected static int CUSTOM_INDEX=0;
	
	private EdAbstractViewGroup parent;
	
	private String name;
	
	private MContext context;

	private EdDrawable background;

	private Visibility visiblility=Visibility.Visible;
	
	private LayoutParam layoutParam;

	
	private float measuredWidth,measuredHeight;
	
	private float minWidth,minHeight;
	
	private float left,right,top,bottom;
	
	public EdView(MContext context){
		this.context=context;
		if(!checkCurrentThread()){
			throw new RuntimeException("you can only create a view in main thread!");
		}
		initialName();
	}

	public void setLayoutParam(LayoutParam layoutParam) {
		this.layoutParam=layoutParam;
	}

	public LayoutParam getLayoutParam() {
		return layoutParam;
	}
	
	public boolean checkCurrentThread(){
		return Thread.currentThread()==getContext().getMainThread();
	}

	public void setParent(EdAbstractViewGroup parent) {
		this.parent=parent;
	}

	public EdAbstractViewGroup getParent() {
		return parent;
	}

	public void setBackground(EdDrawable background) {
		this.background=background;
	}

	public EdDrawable getBackground() {
		return background;
	}
	
	@Override
	public void post(Runnable r) {
		// TODO: Implement this method
		getContext().runOnUIThread(r);
	}

	@Override
	public void post(Runnable r,int delayMS) {
		// TODO: Implement this method
		getContext().runOnUIThread(r,delayMS);
	}
	
	public void setContext(MContext context) {
		this.context=context;
	}

	public MContext getContext() {
		return context;
	}

	private void initialName(){
		name=CUSTOM_INDEX+"";
		CUSTOM_INDEX++;
	}
	
	public void setName(String name) {
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setVisiblility(Visibility visiblility) {
		this.visiblility=visiblility;
	}

	public Visibility getVisiblility() {
		return visiblility;
	}
	
	public void onCreate(){
		
	}
	
	/**
	 *onDraw is called by this view'parent before
	 *draw this view.
	 *发生在measure之后
	 *@param canvas: parent's canvas, where 
	 */
	public void onDraw(BaseCanvas canvas){
		
	}
	
	/**
	 *@param canvas: The canvas to draw this voew's content.
	 *This canvas maybe based on a FBO or parent's canvas.
	 */
	public void draw(BaseCanvas canvas){
		if(background!=null){
			int s=canvas.save();
			//canvas.getMaskMatrix().post(background.getTranslationMatrix());
			background.draw(canvas);
			canvas.restoreToCount(s);
		}
	}

	public final void measure(long widthSpec,long heightSpec){
		onMeasure(widthSpec,heightSpec);
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
	}
	
	public void layout(float left,float top,float right,float bottom){
		
	}
	
	public void onLayout(){
		
	}
	
	protected final void setFrame(float left,float top,float right,float bottom){
		this.left=left;
		this.top=top;
		this.right=right;
		this.bottom=bottom;
	}
	
	public boolean onMotionEvent(EdMotionEvent e){
		return false;
	}
	
	

	/**
	 *这个涉及到点击事件的分发
	 */
	public boolean inArea(Vec2 v) {
		// TODO: Implement this method
		//return area.inArea(v);
		return false;
	}
	
	
	
	
}
