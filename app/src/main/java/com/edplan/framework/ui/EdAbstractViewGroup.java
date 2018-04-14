package com.edplan.framework.ui;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;
import java.util.HashMap;
import com.edplan.framework.MContext;
import java.util.ArrayList;
import com.edplan.framework.graphics.opengl.GLCanvas2D;

public abstract class EdAbstractViewGroup extends EdView
{
	protected List<EdView> children=new ArrayList<EdView>();

	protected List<EdLayoutParam> layoutParams=new ArrayList<EdLayoutParam>();
	
	protected HashMap<String,EdView> viewMap;

	public EdAbstractViewGroup(MContext context){
		super(context);
	}
	
	public abstract void dispatchMesure();
	
	public abstract void dispatchTouchEvent();
	
	public abstract void dispatchDraw(GLCanvas2D canvas);
	
	public EdLayoutParam getDefaultLayoutParam(){
		return null;
	}
	
	public void add(final EdView view){
		post(new Runnable(){
				@Override
				public void run() {
					// TODO: Implement this method
					addUnsyn(view);
				}
			});
	}
	
	private void addUnsyn(EdView view){
		children.add(view);
		viewMap.put(view.getName(),view);
	}
	
	public void addAll(final EdView... views){
		post(new Runnable(){
				@Override
				public void run() {
					// TODO: Implement this method
					for(EdView v:views){
						addUnsyn(v);
					}
				}
			});
	}
	
	public void remove(final EdView view){
		post(new Runnable(){
				@Override
				public void run() {
					// TODO: Implement this method
					removeUnsyn(view);
				}
			});
	}
	
	private void removeUnsyn(EdView view){
		children.remove(view);
		viewMap.remove(view.getName());
	}
	
	public void removeAll(final EdView... views){
		post(new Runnable(){
				@Override
				public void run() {
					// TODO: Implement this method
					for(EdView v:views){
						addUnsyn(v);
					}
				}
			});
	}
}
