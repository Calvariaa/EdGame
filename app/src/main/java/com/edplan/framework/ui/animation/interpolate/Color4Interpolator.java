package com.edplan.framework.ui.animation.interpolate;

import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.ui.animation.Easing;

public class Color4Interpolator implements ValueInterpolator<Color4> 
{
	public static Color4Interpolator Instance=new Color4Interpolator();
	
	@Override
	public Color4 applyInterplate(Color4 startValue,Color4 endValue,double time,Easing easing) {
		// TODO: Implement this method
		float inp=(float)EasingManager.apply(easing,time);
		return Color4.mix(startValue,endValue,inp);
	}
}
