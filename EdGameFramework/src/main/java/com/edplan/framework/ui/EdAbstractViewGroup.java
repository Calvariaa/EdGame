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
	private List<ViewNode> children;
	
	private LayoutTransition transition;
	
	public EdAbstractViewGroup(MContext context){
		super(context);
		children=new ArrayList<ViewNode>();
	}

	public EdLayoutParam getDefaultParam(EdView view){
		return new EdLayoutParam();
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

	protected void measureChildren(long widthSpec,long heightSpec){
		for(ViewNode n:children){
			EdView v=n.view;
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
	
	protected ViewNode addToList(EdView view){
		ViewNode n=new ViewNode(view);
		children.add(n);
		return n;
	}
	
	public void addView(EdView view,EdLayoutParam param){
		view.setLayoutParam(param);
		addToList(view);
	}
	
	public void addView(EdView view){
		if(view.getLayoutParam()==null){
			view.setLayoutParam(getDefaultParam(view));
		}
		addToList(view);
	}

	public class ViewNode{
		public EdView view;
		
		public ViewNode(EdView view){
			this.view=view;
		}
	}
	
}
