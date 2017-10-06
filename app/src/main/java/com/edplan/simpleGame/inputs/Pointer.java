package com.edplan.simpleGame.inputs;

import android.view.MotionEvent;
import java.util.List;
import java.util.ArrayList;

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

	public Pointer(MotionEvent res,int index){
		acceptEvent(res,index);
		callbacks=new ArrayList<Callback>();
	}

	public boolean acceptEvent(MotionEvent event,int index){
		action=event.getAction();
		latestRawEvent=event;
		latestEventIndex=index;
		latestX=event.getX(index)-transformX;
		latestY=event.getY(index)-transformY;
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				id=event.getPointerId(index);
				break;
			case MotionEvent.ACTION_MOVE:
				callbackMove();
				break;
			case MotionEvent.ACTION_UP:
				callbackUp();
				break;
		}
		return true;
	}
	
	private void callbackMove(){
		for(Callback c:callbacks){
			c.onMove(this);
		}
	}
	
	private void callbackUp(){
		for(Callback c:callbacks){
			c.onUp(this);
		}
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
		return latestX;
	}
	
	public float getY(){
		return latestY;
	}

	public void addCallback(Callback callback){
		this.callbacks.add(callback);
	}
	
	public interface Callback{

		public void onMove(Pointer p);

		public void onUp(Pointer p);

	}
	
	
}
