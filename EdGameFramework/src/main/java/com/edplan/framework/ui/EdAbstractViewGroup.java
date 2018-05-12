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

public abstract class EdAbstractViewGroup extends EdView
{
	private EdView[] children;
	private int idx;
	
	private LayoutTransition transition;
	
	public EdAbstractViewGroup(MContext context){
		super(context);
		children=new EdView[1];
	}

	public abstract EdLayoutParam getDefaultParam(EdView view);

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
	protected final void layout(float left,float top,float right,float bottom) {
		// TODO: Implement this method
		if(transition==null||!transition.isChangingLayout()){
			if(transition!=null){
				transition.layoutChange(this);
				super.layout(left, top, right, bottom);
			}
		}
	}
	
	protected void layoutChildren(float left,float top,float right,float bottom){
		final int count=getChildrenCount();
		
		final float parentLeft=getPaddingLeft();
		final float parentRight=right-left-getPaddingRight();
		final float parentTop=getPaddingTop();
		final float parentBottom=bottom-top-getPaddingBottom();
		
		for(int i=0;i<count;i++){
			final EdView view=getChildAt(i);
			if(view.getVisiblility()!=VISIBILITY_GONE){
				
			}
		}
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
		if(idx>=children.length){
			children=Arrays.copyOf(children,children.length*3/2+1);
		}
		children[idx]=v;
		idx++;
	}
	
	public void addView(EdView view,EdLayoutParam param){
		view.setLayoutParam(param);
		addView(view);
	}
	
	public void addView(EdView view){
		if(view.getLayoutParam()==null){
			view.setLayoutParam(getDefaultParam(view));
		}
		addToList(view);
	}
	
}
