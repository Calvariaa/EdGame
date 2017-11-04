package com.edplan.superutils.interfaces;
import com.edplan.superutils.classes.MLooper;

public interface Loopable
{
	public enum Flag{
		Run,Skip,Stop
	}
	
	public void setLooper(MLooper lp);
	
	public void onRecycle();
	
	public void onLoop(int deltaTime);
	
	public Flag getFlag();
}
