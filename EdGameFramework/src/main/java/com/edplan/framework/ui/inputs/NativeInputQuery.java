package com.edplan.framework.ui.inputs;
import android.view.MotionEvent;
import com.edplan.framework.input.EdMotionEvent;
import java.util.List;
import java.util.ArrayList;

public class NativeInputQuery
{
	private List<EdMotionEvent> eventQuery1=new ArrayList<EdMotionEvent>(),eventQuery2=new ArrayList<EdMotionEvent>();

	public void postEvent(MotionEvent raw){
		synchronized(this){
			eventQuery1.add(translateToFrameworkMotionEvent(raw));
		}
	}
	
	public List<EdMotionEvent> getQuery(){
		synchronized(this){
			swapQuery();
			List<EdMotionEvent> tmp=new ArrayList<EdMotionEvent>(eventQuery2);
			eventQuery2.clear();
			return tmp;
		}
	}
	
	private void swapQuery(){
		final List<EdMotionEvent> q=eventQuery1;
		eventQuery1=eventQuery2;
		eventQuery2=q;
	}
	
	public static EdMotionEvent translateToFrameworkMotionEvent(MotionEvent event){
		return EdMotionEvent.load(event);
	}
}
