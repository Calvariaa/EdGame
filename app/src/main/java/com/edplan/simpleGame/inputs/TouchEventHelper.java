package com.edplan.simpleGame.inputs;
import android.view.MotionEvent;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TouchEventHelper
{
	Map<Integer,Pointer> pointers;
	Pointer catchedPointer;
	
	public TouchEventHelper(){
		pointers=new TreeMap<Integer,Pointer>();
	}
	
	
	//返回是否捕捉event
	public boolean sendEvent(MotionEvent event){
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				Pointer p=new Pointer(event,event.getActionIndex());
				catchedPointer=p;
				return true;
			case MotionEvent.ACTION_MOVE:
				if(postEvent(event)!=null){
					return true;
				}else{
					return false;
				}
			case MotionEvent.ACTION_UP:
				Pointer pi=postEvent(event);
				if(pi!=null){
					removePointer(pi);
					return true;
				}else{
					return false;
				}
			default:return false;
		}
	}
	
	private Pointer postEvent(MotionEvent e){
		Pointer p=pointers.get(getCurrentId(e));
		if(p!=null){
			p.acceptEvent(e,e.getActionIndex());
		}
		return p;
	}
	
	
	//返回捕捉到的pointer
	public Pointer getCatchedPointer(){
		Pointer p=catchedPointer;
		catchedPointer=null;
		return p;
	}
	
	public void catchPointer(Pointer p){
		pointers.put(p.getId(),p);
	}
	
	public void removePointer(Pointer p){
		pointers.remove(p.getId());
	}
	
	public static int getCurrentId(MotionEvent e){
		return e.getPointerId(e.getActionIndex());
	}
	
	
}
