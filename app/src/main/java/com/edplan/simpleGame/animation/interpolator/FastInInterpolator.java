package com.edplan.simpleGame.animation.interpolator;
import com.edplan.simpleGame.animation.AnimaInterpolator;

public class FastInInterpolator implements AnimaInterpolator
{
	private static FastInInterpolator interpolator=new FastInInterpolator();
	
	public static FastInInterpolator getDefInterpolator(){
		return interpolator;
	}
	
	@Override
	public float getInterpolation(float f){
		// TODO: Implement this method
		return 1-f*f*(3-f)/2;
	}
}
