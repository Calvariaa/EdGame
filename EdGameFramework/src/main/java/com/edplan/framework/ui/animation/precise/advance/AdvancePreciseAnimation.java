package com.edplan.framework.ui.animation.precise.advance;
import com.edplan.framework.ui.animation.precise.BasePreciseAnimation;
import com.edplan.framework.ui.animation.callback.OnStartListener;
import com.edplan.framework.ui.animation.callback.OnProgressListener;
import com.edplan.framework.ui.animation.callback.OnFinishListener;
import com.edplan.framework.ui.animation.callback.OnEndListener;
import com.edplan.framework.ui.animation.AnimationHelper;

public class AdvancePreciseAnimation extends BasePreciseAnimation
{
	private OnStartListener onStartListener;
	
	private OnProgressListener onProgressListener;
	
	private OnFinishListener onFinishListener;
	
	private OnEndListener onEndListener;

	public void setOnStartListener(OnStartListener onStartListener) {
		this.onStartListener=onStartListener;
	}

	public OnStartListener getOnStartListener() {
		return onStartListener;
	}

	public void setOnProgressListener(OnProgressListener onProgressListener) {
		this.onProgressListener=onProgressListener;
	}

	public OnProgressListener getOnProgressListener() {
		return onProgressListener;
	}

	public void setOnFinishListener(OnFinishListener onFinishListener) {
		this.onFinishListener=onFinishListener;
	}

	public OnFinishListener getOnFinishListener() {
		return onFinishListener;
	}

	public void setOnEndListener(OnEndListener onEndListener) {
		this.onEndListener=onEndListener;
	}

	public OnEndListener getOnEndListener() {
		return onEndListener;
	}

	@Override
	public void onStart() {
		// TODO: Implement this method
		super.onStart();
		if(onStartListener!=null)onStartListener.onStart();
	}

	@Override
	public void onProgress(double p) {
		// TODO: Implement this method
		super.onProgress(p);
		if(onProgressListener!=null)onProgressListener.onProgress(p);
	}
	
	public void setValueProgress(float fp){
		
	}

	@Override
	public void onFinish() {
		// TODO: Implement this method
		super.onFinish();
		if(onFinishListener!=null)onFinishListener.onFinish();
	}

	@Override
	public void onEnd() {
		// TODO: Implement this method
		super.onEnd();
		if(onEndListener!=null)onEndListener.onEnd();
	}
}
