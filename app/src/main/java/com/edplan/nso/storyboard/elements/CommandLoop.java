package com.edplan.nso.storyboard.elements;

public class CommandLoop extends CommandTimeLineGroup
{
	private double loopStartTime;
	private int loopCount;
	
	public CommandLoop(double startTime,int loopCount){
		this.loopStartTime=startTime;
		this.loopCount=loopCount;
	}

	@Override
	public double getStartTime() {
		// TODO: Implement this method
		return loopStartTime+getCommandsStartTime();
	}

	@Override
	public double getEndTime() {
		// TODO: Implement this method
		return getStartTime()+getCommandsDuration()*loopCount;
	}
}
