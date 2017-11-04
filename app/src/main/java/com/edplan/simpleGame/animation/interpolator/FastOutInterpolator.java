package com.edplan.simpleGame.animation.interpolator;
import com.edplan.simpleGame.animation.AnimaInterpolator;

public class FastOutInterpolator implements AnimaInterpolator
{
	private static FastOutInterpolator interpolator=new FastOutInterpolator();

	public static FastOutInterpolator getDefInterpolator(){
		return interpolator;
	}
	
	@Override
	public float getInterpolation(float f){
		// TODO: Implement this method
		return f*f*(3-f)/2;
	}
}
