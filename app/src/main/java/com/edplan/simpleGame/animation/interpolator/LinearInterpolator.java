package com.edplan.simpleGame.animation.interpolator;
import com.edplan.simpleGame.animation.AnimaInterpolator;

public class LinearInterpolator implements AnimaInterpolator
{
	private static LinearInterpolator interpolator=new LinearInterpolator();

	public static LinearInterpolator getDefInterpolator(){
		return interpolator;
	}
	
	@Override
	public float getInterpolation(float f){
		// TODO: Implement this method
		return f;
	}
}
