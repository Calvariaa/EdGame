package com.edplan.framework.ui.animation.precise;
import com.edplan.framework.ui.animation.AnimState;
import com.edplan.framework.timing.PreciseTimeline;

public class BasePreciseAnimation extends AbstractPreciseAnimation
{
	private double startTime;
	
	private double duration;
	
	private double progressTime;
	
	private AnimState state=AnimState.Waiting;
	
	public void post(PreciseTimeline timeline){
		state=AnimState.Running;
		timeline.addAnimation(this);
		setProgressTime(0);
		//timeline.frameTime()-getStartTimeAtTimeline());
	}
	
	public void setStartTime(double startTime){
		this.startTime=startTime;
	}
	
	public void setDuration(double duration){
		this.duration=duration;
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
	}

	/*
	@Override
	public void postProgressTime(double deltaTime) {
		// TODO: Implement this method
		progressTime+=deltaTime;
	}
	*/

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

}
