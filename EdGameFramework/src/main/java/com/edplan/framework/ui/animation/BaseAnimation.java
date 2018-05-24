package com.edplan.framework.ui.animation;
import com.edplan.framework.ui.animation.callback.OnFinishListener;
import com.edplan.framework.ui.animation.callback.OnEndListener;

public class BaseAnimation extends AbstractAnimation
{
	private OnFinishListener onFinishListener;
	
	private OnEndListener onEndListener;
	
	private double progress;
	
	private double duration;
	
	private LoopType loopType=LoopType.None;
	
	private AnimState state=AnimState.Waiting;

	public void setOnEndListener(OnEndListener onEndListener){
		this.onEndListener=onEndListener;
	}

	public OnEndListener getOnEndListener(){
		return onEndListener;
	}

	protected void setState(AnimState state){
		this.state=state;
	}

	public void setLoopType(LoopType loopType){
		this.loopType=loopType;
	}

	public void setDuration(double duration){
		this.duration=duration;
	}

	public void setOnFinishListener(OnFinishListener onFinishListener){
		this.onFinishListener=onFinishListener;
	}

	public OnFinishListener getOnFinishListener(){
		return onFinishListener;
	}
	
	public void start(){
		setState(AnimState.Running);
	}
	
	@Override
	public void onStart(){
		// TODO: Implement this method
	}

	@Override
	public void onProgress(double p){
		// TODO: Implement this method
	}

	@Override
	public void onFinish(){
		// TODO: Implement this method
		state=AnimState.Stop;
		if(onFinishListener!=null)onFinishListener.onFinish();
	}

	@Override
	public void onEnd(){
		// TODO: Implement this method
		state=AnimState.Stop;
		if(onEndListener!=null)onEndListener.onEnd();
	}

	@Override
	public double getDuration(){
		// TODO: Implement this method
		return duration;
	}

	@Override
	public LoopType getLoopType(){
		// TODO: Implement this method
		return loopType;
	}

	@Override
	public AnimState getState(){
		// TODO: Implement this method
		return state;
	}

	@Override
	public void setProgressTime(double p){
		// TODO: Implement this method
		progress=p;
	}

	@Override
	public void postProgressTime(double deltaTime){
		// TODO: Implement this method
		progress+=deltaTime;
	}

	@Override
	public double getProgressTime(){
		// TODO: Implement this method
		return progress;
	}

	@Override
	public void dispos(){
		// TODO: Implement this method
	}
}
