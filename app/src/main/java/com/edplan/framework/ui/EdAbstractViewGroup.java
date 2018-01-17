package com.edplan.framework.ui;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;
import java.util.HashMap;
import com.edplan.framework.MContext;

public class EdAbstractViewGroup extends EdView
{
	protected List<EdView> children;

	protected List<EdLayoutParam> layoutParams;
	
	protected HashMap<String,EdView> viewMap;

	public EdAbstractViewGroup(MContext context){
		super(context);
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
