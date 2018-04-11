package com.edplan.framework.ui.animation;

// http://easings.net/
public enum Easing 
{
	None,
	Out,
	In,
	InQuad,
	OutQuad,
	InOutQuad,
	InCubic,
	OutCubic,
	InOutCubic,
	InQuart,
	OutQuart,
	InOutQuart,
	InQuint,
	OutQuint,
	InOutQuint,
	InSine,
	OutSine,
	InOutSine,
	InExpo,
	OutExpo,
	InOutExpo,
	InCirc,
	OutCirc,
	InOutCirc,
	InElastic,
	OutElastic,
	OutElasticHalf,
	OutElasticQuarter,
	InOutElastic,
	InBack,
	OutBack,
	InOutBack,
	InBounce,
	OutBounce,
	InOutBounce,
	OutPow10,
	Jump;
	public final int id;
	
	Easing(){
		id=$ordinal;
	}
	
	public static Easing getEasing(int ord){
		return $VALUES[ord];
	}
}
