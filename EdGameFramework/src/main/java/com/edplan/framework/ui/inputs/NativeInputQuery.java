package com.edplan.framework.ui.inputs;
import android.view.MotionEvent;
import java.util.List;
import java.util.ArrayList;
import com.edplan.framework.ui.inputs.EdMotionEvent.*;
import com.edplan.framework.MContext;

public class NativeInputQuery
{
	private List<EdMotionEvent> eventQuery1=new ArrayList<EdMotionEvent>(),eventQuery2=new ArrayList<EdMotionEvent>();

	private MContext context;
	
	public NativeInputQuery(MContext c){
		context=c;
	}
	
	public void postEvent(MotionEvent raw){
		synchronized(this){
			eventQuery1.add(load(raw));
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
	
	public EdMotionEvent load(MotionEvent raw){
		EdMotionEvent event=new EdMotionEvent();
		event.eventPosition.set(raw.getX(),raw.getY());
		event.eventType=EdMotionEvent.parseType(raw.getActionMasked());
		event.pointerId=raw.getPointerId(raw.getActionIndex());
		event.rawType=RawType.TouchScreen;
		event.time=context.getUiLooper().calTimeOverThread(raw.getEventTime());
		return event;
	}
}
