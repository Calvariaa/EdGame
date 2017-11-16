package com.edplan.framework.inputs;
import android.util.Log;
import android.view.MotionEvent;
import com.edplan.framework.inputs.Pointer;
import java.util.Map;
import java.util.TreeMap;
import com.edplan.framework.utils.TouchUtils;

public class TouchEventHelper
{
	Map<Integer,Pointer> pointers;
	Pointer catchedPointer;
	
	public TouchEventHelper(){
		pointers=new TreeMap<Integer,Pointer>();
	}
	
	
	//返回是否捕捉event
	public boolean sendEvent(MotionEvent event){
		switch(event.getActionMasked()){
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				Pointer pk=new Pointer(event,event.getActionIndex());
				catchedPointer=pk;
				return true;
			case MotionEvent.ACTION_MOVE:
				/*if(postEvent(event)!=null){
					return true;
				}else{
					return false;
				}*/
				postEvent(event);
				return true;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				Pointer pi=postEvent(event);
				if(pi!=null){
					removePointer(pi);
					return true;
				}else{
					return false;
				}
			case MotionEvent.ACTION_CANCEL:
				postEvent(event);
				pointers.clear();
				return true;
			default:
				postEvent(event);
				return false;
		}
	}
	
	private Pointer postEvent(MotionEvent e){
		
		switch(e.getActionMasked()){
			case MotionEvent.ACTION_MOVE:
			case MotionEvent.ACTION_CANCEL:
				Pointer pt=null;
				for(Map.Entry<Integer,Pointer> entry:pointers.entrySet()){
					/*if(
						Math.hypot(
							entry.getValue().getX()-e.getX(findIndex(entry.getValue().getId(),e))*e.getXPrecision(),
							entry.getValue().getY()-e.getY(findIndex(entry.getValue().getId(),e))*e.getYPrecision()
						)>100
					){
						continue;
					}*/
					if(entry.getValue().acceptEvent(e)){
						pt=entry.getValue();
					}
					//, TouchUtils.indexOfId(pointers.get(i).getId(),e));
				}
				return pt;
			default:
				Pointer p=pointers.get(getCurrentId(e));
				if(p!=null){
					p.acceptEvent(e,e.getActionIndex());
				}
				return p;
		}
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
	
	public static int findIndex(int id,MotionEvent e){
		for(int i=0;i<e.getPointerCount();i++){
			if(e.getPointerId(i)==id)return i;
		}
		return -1;
	}
}
