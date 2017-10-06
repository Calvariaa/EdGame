package com.edplan.simpleGame.animation.interpolator;
import com.edplan.simpleGame.animation.AnimaInterpolator;

public class MaterialInterpolator implements AnimaInterpolator
{
	@Override
	public float getInterpolation(float f){
		// TODO: Implement this method
		return f*f*(3-2*f);
	}
}
