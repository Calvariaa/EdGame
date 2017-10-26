package com.edplan.nso.Ruleset.std.object;

public enum StdHitObjectType
{
	Circle,Slider,Spinner;
	public static StdHitObjectType parseType(int i){
		if((i&0x00000001)==1){
			return Circle;
		}else if((i&0x00000002)==0x00000002){
			return Slider;
		}else if((i&0x00000008)==0x00000008){
			return Spinner;
		}else{
			return null;
		}
	}
}
