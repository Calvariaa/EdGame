package com.edplan.simpleGame.animation;

public interface AnimaCallback
{
	public void onStart();
	
	public void onProgress(float p);
	
	public void onFinish();
	
	public void onStop(float p);
	
	public void onEnd();
}
