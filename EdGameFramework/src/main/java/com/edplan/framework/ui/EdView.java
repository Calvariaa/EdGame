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

public class EdView implements IRunnableHandler
{
	protected static int CUSTOM_INDEX=0;
	
	private EdAbstractViewGroup parent;
	
	private LayoutParam layoutParam;

	private String name;

	private RectF area;
	
	//private Area2D touchArea;
	
	private Visibility visiblility=Visibility.Visible;
	
	private DrawRequestParam drawRequestParam=DrawRequestParam.NewLayer;
	
	private MContext context;
	
	private EdDrawable background;
	
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

	public void setDrawRequestParam(DrawRequestParam drawRequestParam) {
		this.drawRequestParam=drawRequestParam;
	}

	public DrawRequestParam getDrawRequestParam() {
		return drawRequestParam;
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

	public void measure(long width,long height){

	}
	
	public boolean onMotionEvent(EdMotionEvent e){
		return false;
	}
	
	

	/**
	 *这个涉及到点击事件的分发
	 */
	public boolean inArea(Vec2 v) {
		// TODO: Implement this method
		return area.inArea(v);
	}
	
	
	
	
}
