package com.edplan.framework.media.video.tbv.encode;

import com.edplan.framework.media.video.tbv.TBV;
import com.edplan.framework.media.video.tbv.TBVException;

public class TBVEncoder 
{
	private TBVOutputStream out;
	
	private TBV.Header header=new TBV.Header();

	private TBV.FrameHeader frameHeader=new TBV.FrameHeader();

	private TBV.EventHeader eventHeader=new TBV.EventHeader();

	private float currentPlayTime=0;
	
	private double frameDeltaTime=1000/60d;
	
	
	public void endEncode(){
		
	}
	
	public void toNewFrame(double deltaTime) throws TBVException{
		if(deltaTime<=0){
			toNewFrame(frameDeltaTime);
		}
		if(eventHeader.eventType!=TBV.FrameEvent.FRAME_END){
			throw new TBVException("you only can step to a new frame when you ended last frame");
		}
		
	}
	
	

	public void setFrameDeltaTime(double frameDeltaTime) {
		this.frameDeltaTime=frameDeltaTime;
	}

	public double getFrameDeltaTime() {
		return frameDeltaTime;
	}

}
