package com.edplan.framework.timing.events;

public interface ClockEvent
{
	public int getInvokeTime();
	
	public void invoke();
}
