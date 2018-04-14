package com.edplan.framework.media.video.tbv.encode;

import com.edplan.framework.media.video.tbv.TBV;
import com.edplan.framework.media.video.tbv.TBVException;
import java.io.IOException;

public class TBVEncoder 
{
	private TBVOutputStream out;
	
	private TBVOutputStream bufferedOut;
	
	private BufferedByteOutputStream rawBufferedOut;
	
	private TBV.Header header=new TBV.Header();

	private TBV.FrameHeader frameHeader=new TBV.FrameHeader();

	private TBV.EventHeader eventHeader=new TBV.EventHeader();

	private float currentPlayTime=0;
	
	private double frameDeltaTime=1000/60d;
	
	
	
	public void initial(){
		
	}
	
	
	public void endEncode(){
		
	}
	
	
	boolean frameIsFlushed=false;
	public void flushFrame() throws TBVException, IOException{
		if(eventHeader.eventType!=TBV.FrameEvent.FRAME_END){
			throw new TBVException("you only can flush a frame when you ended this frame");
		}
		if(frameIsFlushed){
			throw new TBVException("this frame is already flushed");
		}
		frameIsFlushed=true;
		frameHeader.blockSize=rawBufferedOut.size();
		TBV.FrameHeader.write(out,frameHeader);
		out.writeBytes(rawBufferedOut.ary,0,rawBufferedOut.size());
		rawBufferedOut.reset();
	}
	
	
	public void toNewFrame(double deltaTime) throws TBVException{
		if(deltaTime<=0){
			toNewFrame(frameDeltaTime);
		}
		if(!frameIsFlushed){
			throw new TBVException("you can't step to new frame when last frame hasn't flushed");
		}
		
	}
	
	

	public void setFrameDeltaTime(double frameDeltaTime) {
		this.frameDeltaTime=frameDeltaTime;
	}

	public double getFrameDeltaTime() {
		return frameDeltaTime;
	}

}
