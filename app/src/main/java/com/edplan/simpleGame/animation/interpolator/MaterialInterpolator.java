package com.edplan.simpleGame.animation.interpolator;
import com.edplan.simpleGame.animation.AnimaInterpolator;

public class MaterialInterpolator implements AnimaInterpolator
{
	private static MaterialInterpolator interpolator=new MaterialInterpolator();

	public static MaterialInterpolator getDefInterpolator(){
		return interpolator;
	}
	
	@Override
	public float getInterpolation(float f){
		// TODO: Implement this method
		return f*f*(3-2*f);
	}
}
