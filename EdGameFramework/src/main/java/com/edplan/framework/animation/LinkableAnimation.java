package com.edplan.framework.animation;


import com.edplan.framework.interfaces.Setter;

public class LinkableAnimation extends MAnimation 
{
	public MAnimation nextAnimation;
	
	public LinkableAnimation(Setter<Float> _setter){
		super(_setter);
	}
	
	public LinkableAnimation(){
		super();
	}

	public void setNextAnimation(MAnimation nextAnimation){
		this.nextAnimation=nextAnimation;
	}

	public MAnimation getNextAnimation(){
		return nextAnimation;
	}

	@Override
	protected void onFinish(){
		// TODO: Implement this method
		super.onFinish();
		if(nextAnimation!=null){
			getLooper().addLoopable(nextAnimation);
			nextAnimation.start();
		}
	}
}
