package com.edplan.nso.Ruleset.mania.objects;
import com.edplan.nso.Ruleset.std.objects.StdHitObjectType;

public class ManiaHolder extends ManiaHitObject
{
	private int endTime;
	
	public ManiaHolder(){
		
	}
	
	public void setEndTime(int endTime){
		this.endTime=endTime;
	}
	
	public int getEndTime(){
		return endTime;
	}

	@Override
	public StdHitObjectType getResType(){
		// TODO: Implement this method
		return StdHitObjectType.ManiaHolder;
	}
}
