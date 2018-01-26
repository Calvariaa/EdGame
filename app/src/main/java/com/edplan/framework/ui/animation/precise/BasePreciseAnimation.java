package com.edplan.framework.ui.animation.precise;
import com.edplan.framework.ui.animation.AnimState;
import com.edplan.framework.timing.PreciseTimeline;

public class BasePreciseAnimation extends AbstractPreciseAnimation
{
	private int startTime;
	
	private int duration;
	
	private int progressTime;
	
	private AnimState state=AnimState.Waiting;
	
	public void post(PreciseTimeline timeline){
		state=AnimState.Running;
		timeline.addAnimation(this);
		setProgressTime(0);
	}
	
	public void setStartTime(int startTime){
		this.startTime=startTime;
	}
	
	public void setDuration(int duration){
		this.duration=duration;
	}
	
	@Override
	public int getDuration() {
		// TODO: Implement this method
		return duration;
	}

	@Override
	public AnimState getState() {
		// TODO: Implement this method
		return state;
	}

	@Override
	public void setProgressTime(int p) {
		// TODO: Implement this method
		progressTime=p;
	}

	@Override
	public int getProgressTime() {
		// TODO: Implement this method
		return progressTime;
	}

	@Override
	public void onStart() {
		// TODO: Implement this method
	}

	@Override
	public void onProgress(int p) {
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
	public int getStartTimeAtTimeline() {
		// TODO: Implement this method
		return startTime;
	}

}
