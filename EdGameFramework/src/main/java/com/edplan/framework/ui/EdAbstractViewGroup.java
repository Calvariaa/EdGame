package com.edplan.framework.ui;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;
import java.util.HashMap;
import com.edplan.framework.MContext;
import java.util.ArrayList;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.ui.layout.EdLayoutParam;
import com.edplan.framework.ui.layout.MeasureCore;
import com.edplan.framework.graphics.opengl.BaseCanvas;
import com.edplan.framework.ui.inputs.EdMotionEvent;
import com.edplan.framework.math.RectF;

public abstract class EdAbstractViewGroup extends EdView
{
	private EdView[] children;
	private int idx;
	
	private LayoutTransition transition;
	
	public EdAbstractViewGroup(MContext context){
		super(context);
		children=new EdView[1];
	}

	private float paddingLeft,paddingTop,paddingRight,paddingBottom;

	public void setPaddingLeft(float paddingLeft){
		this.paddingLeft=paddingLeft;
	}

	@Override
	public float getPaddingLeft(){
		return paddingLeft;
	}

	public void setPaddingTop(float paddingTop){
		this.paddingTop=paddingTop;
	}

	@Override
	public float getPaddingTop(){
		return paddingTop;
	}

	public void setPaddingRight(float paddingRight){
		this.paddingRight=paddingRight;
	}

	@Override
	public float getPaddingRight(){
		return paddingRight;
	}

	public void setPaddingBottom(float paddingBottom){
		this.paddingBottom=paddingBottom;
	}

	@Override
	public float getPaddingBottom(){
		return paddingBottom;
	}
	
	public EdLayoutParam getDefaultParam(EdView view){
		// TODO: Implement this method
		final EdLayoutParam param=view.getLayoutParam();
		if(param!=null){
			return param;
		}
		else{
			return new EdLayoutParam();
		}
	}

	@Override
	public void onCreate(){
		// TODO: Implement this method
		final int count=getChildrenCount();
		for(int i=0;i<count;i++){
			final EdView view=getChildAt(i);
			if(!view.hasCreated())view.onCreate();
		}
	}
	
	@Override
	protected abstract void onLayout(boolean changed, float left, float top, float right, float bottom);

	@Override
	protected abstract void onMeasure(long widthSpec, long heightSpec);
	
	public EdView getChildAt(int i){
		return children[i];
	}
	
	public int getChildrenCount(){
		return idx;
	}
	
	@Override
	public final void layout(float left,float top,float right,float bottom) {
		// TODO: Implement this method
		if(transition==null||!transition.isChangingLayout()){
			if(transition!=null){
				transition.layoutChange(this);
				super.layout(left, top, right, bottom);
			}else{
				super.layout(left, top, right, bottom);
			}
		}
	}
	
	protected void dispatchDraw(BaseCanvas canvas){
		final int count=getChildrenCount();
		for(int i=0;i<count;i++){
			final EdView view=getChildAt(i);
			if(view.getVisiblility()==VISIBILITY_SHOW){
				final int savedcount=canvas.save();
				try{
					canvas.translate(view.getLeft(),view.getTop());
					canvas.clip(view.getWidth(),view.getHeight());
					view.onDraw(canvas);
				}finally{
					canvas.restoreToCount(savedcount);
				}
			}
		}
	}

	@Override
	public void onDraw(BaseCanvas canvas){
		// TODO: Implement this method
		drawBackground(canvas);
		dispatchDraw(canvas);
	}
	
	/*
	private int holdingPointer=-1;
	private EdView viewUsingPointer;
	
	protected void clearPointerInfo(){
		holdingPointer=-1;
		viewUsingPointer=null;
	}
	*/
	
	private PointerHolder holder;
	
	protected boolean dispatchMotionEvent(EdMotionEvent event){
		if(holder==null)holder=new PointerHolder();
		if(holder.ifIgnore(event))return false;
		
		boolean useevent=false;
		/*
		//暂时只支持单点触控
		if(holdingPointer!=-1&&holdingPointer!=event.getPointerId()){
			return false;
		}
		*/
		final float x=event.getX(),y=event.getY();
		final EdView viewUsingPointer=holder.getHoldingView(event);
		if(viewUsingPointer!=null){
			if(viewUsingPointer.getParent()!=this){
				//已经不在这个ViewGroup中了
				holder.clearPointerInfo(event);
				return false;
			}
		}
		event.transform(-getLeft(),-getTop());
		switch(event.getEventType()){
			case Down:
				final int count=getChildrenCount();
				for(int i=count-1;i>=0;i--){
					final EdView view=getChildAt(i);
					if(view.inViewBound(event.getX(),event.getY())){
						if(view.onMotionEvent(event)){
							useevent=true;
							holder.setHoldingView(event,view);
							break;
						}
					}
				}
				break;
			case Move:
				useevent=viewUsingPointer.onMotionEvent(event);
				break;
			case Up:
				useevent=viewUsingPointer.onMotionEvent(event);
				holder.clearPointerInfo(event);
				break;
			case Cancel:
				useevent=viewUsingPointer.onMotionEvent(event);
				holder.clearPointerInfo(event);
				break;
		}
		event.setEventPosition(x,y);
		return useevent;
	}

	@Override
	public boolean onMotionEvent(EdMotionEvent e){
		// TODO: Implement this method
		if(!dispatchMotionEvent(e)){
			return super.onMotionEvent(e);
		}else{
			return true;
		}
	}
	
	protected void layoutChildren(float left,float top,float right,float bottom){
		final int count=getChildrenCount();
		
		final float parentLeft=getPaddingLeft();
		final float parentTop=getPaddingTop();
		
		for(int i=0;i<count;i++){
			final EdView view=getChildAt(i);
			if(view.getVisiblility()!=VISIBILITY_GONE){
				final EdLayoutParam param=view.getLayoutParam();
				final float dx=param.xoffset+parentLeft;
				final float dy=param.yoffset+parentTop;
				view.layout(dx,dy,view.getMeasuredWidth()+dx,view.getMeasuredHeight()+dy);
			}
		}
	}
	
	protected float getDefaultMaxChildrenMeasuredWidthWithMargin(){
		final int count=getChildrenCount();
		float m=0;
		for(int i=0;i<count;i++){
			final EdView v=getChildAt(i);
			m=Math.max(v.getMeasuredWidth()+v.getMarginHorizonIfHas(),m);
		}
		return m;
	}
	
	protected float getDefaultMaxChildrenMeasuredWidth(){
		final int count=getChildrenCount();
		float m=0;
		for(int i=0;i<count;i++){
			final EdView v=getChildAt(i);
			m=Math.max(v.getMeasuredWidth(),m);
		}
		return m;
	}
	
	
	protected float getDefaultMaxChildrenMeasuredHeightWithMargin(){
		final int count=getChildrenCount();
		float m=0;
		for(int i=0;i<count;i++){
			final EdView v=getChildAt(i);
			m=Math.max(v.getMeasuredHeight()+v.getMarginVerticalIfHas(),m);
		}
		return m;
	}
	
	protected float getDefaultMaxChildrenMeasuredHeight(){
		final int count=getChildrenCount();
		float m=0;
		for(int i=0;i<count;i++){
			final EdView v=getChildAt(i);
			m=Math.max(v.getMeasuredHeight(),m);
		}
		return m;
	}
	
	protected float getDefaultMaxChildrenMeasuredWidthWithOffset(){
		final int count=getChildrenCount();
		float m=0;
		for(int i=0;i<count;i++){
			final EdView v=getChildAt(i);
			m=Math.max(v.getMeasuredWidth()+v.getLayoutParam().xoffset,m);
		}
		return m;
	}

	protected float getDefaultMaxChildrenMeasuredHeightWithOffset(){
		final int count=getChildrenCount();
		float m=0;
		for(int i=0;i<count;i++){
			final EdView v=getChildAt(i);
			m=Math.max(v.getMeasuredHeight()+v.getLayoutParam().yoffset,m);
		}
		return m;
	}

	protected void measureChildren(long widthSpec,long heightSpec){
		final int count=getChildrenCount();
		for(int i=0;i<count;i++){
			final EdView v=getChildAt(i);
			if(v.getVisiblility()!=VISIBILITY_GONE){
				measureChild(v,widthSpec,heightSpec);
			}
		}
	}
	
	protected void measureChild(EdView view,long wspec,long hspec){
		MeasureCore.measureChild(
			view,
			getPaddingHorizon(),
			getPaddingVertical(),
			wspec,
			hspec);
	}
	
	protected void measureChildWithMargin(EdView view,long wspec,long hspec,float wused,float hused){
		MeasureCore.measureChildWithMargin(
			view,
			getPaddingHorizon(),
			getPaddingVertical(),
			wspec,
			hspec,
			wused,
			hused);
	}
	
	protected void addToList(EdView v){
		v.setParent(this);
		if(idx>=children.length){
			children=Arrays.copyOf(children,children.length*3/2+1);
		}
		children[idx]=v;
		idx++;
		if(hasCreated()){
			if(!v.hasCreated())v.onCreate();
		}
	}
	
	public EdLayoutParam adjustParam(EdView view,EdLayoutParam param){
		return param;
	}
	
	public void addView(EdView view,EdLayoutParam param){
		view.setLayoutParam(adjustParam(view,param));
		addView(view);
	}
	
	public void addView(EdView view){
		if(view.getLayoutParam()==null){
			view.setLayoutParam(getDefaultParam(view));
		}
		addToList(view);
	}
	
	public class PointerHolder{
		public EdView[] holdingView=new EdView[EdMotionEvent.MAX_POINTER];
		//public boolean[] handlePointer=new boolean[EdMotionEvent.MAX_POINTER];
		
		public PointerHolder(){
			//Arrays.fill(handlePointer,false);
		}
		
		public void setHoldingView(EdMotionEvent e,EdView view){
			holdingView[e.getPointerId()]=view;
		}
		
		public boolean ifIgnore(EdMotionEvent e){
			if(e.getEventType()!=EdMotionEvent.EventType.Down){
				if(holdingView[e.getPointerId()]==null)return true;
				return false;
			}else{
				return false;
			}
		}
		
		public EdView getHoldingView(EdMotionEvent e){
			return holdingView[e.getPointerId()];
		}
		
		public void clearPointerInfo(EdMotionEvent e){
			holdingView[e.getPointerId()]=null;
		}
	}
}
