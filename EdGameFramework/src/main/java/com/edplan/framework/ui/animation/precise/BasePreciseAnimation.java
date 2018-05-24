package com.edplan.framework.ui.animation.precise;
import com.edplan.framework.ui.animation.AnimState;
import com.edplan.framework.timing.PreciseTimeline;

public class BasePreciseAnimation extends AbstractPreciseAnimation
{
	private double startTime;
	
	private double duration;
	
	private double progressTime;
	
	private AnimState state=AnimState.Waiting;
	
	public void start(){
		state=AnimState.Running;
		setProgressTime(0);
	}
	
	public void post(PreciseTimeline timeline){
		/*
		double endTime=getEndTime();
		if(endTime<=timeline.frameTime()){
			//如果当前时间已经晚于frameTime了，直接模拟一次生命周期
			onStart();
			setProgressTime(getDuration());
			onFinish();
			onEnd();
		}else{
			*/
			
		timeline.addAnimation(this);
		start();
		//}
		//timeline.frameTime()-getStartTimeAtTimeline());
	}
	
	public double currentTime(){
		return getStartTimeAtTimeline()+getProgressTime();
	}
	
	public void setStartTime(double startTime){
		this.startTime=startTime;
	}
	
	public void setDuration(double duration){
		this.duration=duration;
	}
	
	protected void postTime(double deltaTime,double progressTime){
		seekToTime(progressTime);
	}
	
	protected void seekToTime(double progressTime){
		
	}
	
	@Override
	public double getDuration() {
		// TODO: Implement this method
		return duration;
	}

	@Override
	public AnimState getState() {
		// TODO: Implement this method
		return state;
	}

	@Override
	public void setProgressTime(double p) {
		// TODO: Implement this method
		progressTime=p;
		seekToTime(p);
	}

	@Override
	public void postProgressTime(double deltaTime) {
		// TODO: Implement this method
		progressTime+=deltaTime;
		postTime(deltaTime,progressTime);
	}

	@Override
	public double getProgressTime() {
		// TODO: Implement this method
		return progressTime;
	}

	@Override
	public void onStart() {
		// TODO: Implement this method
	}

	@Override
	public void onProgress(double p) {
		// TODO: Implement this method
	}

	@Override
	public void onFinish() {
		// TODO: Implement this method
	}

	@Override
	public void onEnd() {
		// TODO: Implement this method
	}

	@Override
	public double getStartTimeAtTimeline() {
		// TODO: Implement this method
		return startTime;
	}

	@Override
	public void dispos() {
		// TODO: Implement this method
	}
}
