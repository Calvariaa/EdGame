package com.edplan.nso.storyboard.elements;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.ui.Anchor;
import com.edplan.framework.ui.animation.LoopType;
import com.edplan.nso.storyboard.Storyboard;

public class StoryboardAnimation extends StoryboardSprite
{
	private int frameCount;
	private double frameDelay;
	private Storyboard.AnimationLoopType loopType;
	
	public StoryboardAnimation(String path,Anchor anchor,Vec2 initialPosition,int frameCount,double frameDelay,Storyboard.AnimationLoopType loopType){
		super(path,anchor,initialPosition);
		this.frameCount=frameCount;
		this.frameDelay=frameDelay;
		this.loopType=loopType;
	}

	@Override
	public String getInitialPath() {
		// TODO: Implement this method
		return getPath().substring(0,getPath().lastIndexOf("."))+"0"+getPath().substring(getPath().lastIndexOf("."),getPath().length());
	}
}
