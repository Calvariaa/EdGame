package com.edplan.simpleGame.inputs;

import android.util.Log;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.List;

public class Pointer{

	public int id;

	public int action;

	public MotionEvent latestRawEvent;

	public int latestEventIndex;
	
	public float transformX=0;
	
	public float transformY=0;

	public float latestX;

	public float latestY;

	public List<Callback> callbacks;
	
	public List<Pointer> clonedPointers;
	
	Pointer(){
		callbacks=new ArrayList<Callback>();
	}

	public Pointer(MotionEvent res,int index){
		acceptEvent(res,index);
		callbacks=new ArrayList<Callback>();
	}

	public boolean acceptEvent(MotionEvent event,int index){
		action=event.getActionMasked();
		latestRawEvent=event;
		latestEventIndex=index;
		latestX=event.getX(index);
		latestY=event.getY(index);
		switch(event.getActionMasked()){
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				id=event.getPointerId(index);
				break;
			case MotionEvent.ACTION_MOVE:
				callbackMove();
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				callbackUp();
				break;
			case MotionEvent.ACTION_CANCEL:
				callbackCancel();
				break;
		}
		toClonedPointers(event,index);
		return true;
	}
	
	public void toClonedPointers(MotionEvent e,int index){
		if(clonedPointers!=null){
			for(Pointer p:clonedPointers){
				p.acceptEvent(e,index);
			}
		}
	}
	
	private void callbackMove(){
		for(Callback c:callbacks){
			c.onMove(this);
		}
	}
	
	private void callbackUp(){
		//Log.v("pointer","up "+getId());
		for(Callback c:callbacks){
			c.onUp(this);
		}
	}
	
	private void callbackCancel(){
		
	}
	
	public Pointer clonePointer(){
		Pointer p=new Pointer();
		p.id=getId();
		p.action=getAction();
		p.latestEventIndex=getLatestEventIndex();
		p.latestRawEvent=getEvent();
		p.latestX=getX();
		p.latestY=getY();
		p.transformX=transformX;
		p.transformY=transformY;
		return p;
	}
	
	public void registClone(Pointer p){
		if(clonedPointers==null)clonedPointers=new ArrayList<Pointer>();
		clonedPointers.add(p);
	}
	
	public void transform(float dx,float dy){
		transformX+=dx;
		transformY+=dy;
	}
	
	public int getId(){
		return id;
	}

	public int getAction(){
		return action;
	}

	public void setLatestRawEvent(MotionEvent latestRawEvent){
		this.latestRawEvent=latestRawEvent;
	}

	public MotionEvent getEvent(){
		return latestRawEvent;
	}

	public void setLatestEventIndex(int latestEventIndex){
		this.latestEventIndex=latestEventIndex;
	}

	public int getLatestEventIndex(){
		return latestEventIndex;
	}

	public float getX(){
		return latestX-transformX;
	}
	
	public float getY(){
		return latestY-transformY;
	}

	public void addCallback(Callback callback){
		this.callbacks.add(callback);
	}
	
	
	public interface Callback{

		public void onMove(Pointer p);

		public void onUp(Pointer p);
		
		public void onCancel(Pointer p);
		
	}
	
	
}
