package com.edplan.superutils.interfaces;

public interface AbstractLooper
{
	public void loop(int deltaTime);
	
	public void prepare();
	
	public void addLoopable(Loopable l);
}
