package com.edplan.framework.ui.animation.interpolate;

import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.ui.animation.Easing;

public class Color4Interpolator implements ValueInterpolator<Color4> 
{
	public static Color4Interpolator Instance=new Color4Interpolator();
	
	private Color4 buffer=Color4.ONE.copyNew();
	@Override
	public Color4 applyInterplate(Color4 s,Color4 e,double time,Easing easing) {
		// TODO: Implement this method
		float inp=(float)EasingManager.apply(easing,time);
		float minp=1-inp;
		float a=s.a*minp+e.a*inp;
		inp*=a;
		minp*=a;
		buffer.set(s.r*minp+e.r*inp,s.g*minp+e.g*inp,s.b*minp+e.b*inp,a,true);
		return buffer;
	}
}
