package com.edplan.framework.ui.animation;

public class AnimationHelper
{
	public static float getFloatProgress(int progressTime,int duration){
		if(duration==0){
			return 1;
		}else{
			return progressTime/(float)duration;
		}
	}
}
