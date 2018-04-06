package com.edplan.nso.storyboard.elements;

public class CommandTrigger extends CommandTimeLineGroup
{
	private String triggerName;
	private double triggerStartTime;
	private double triggerEndTime;
	private int groupNumber;
	
	public CommandTrigger(String triggerName,double startTime,double endTime,int groupNumber){
		this.triggerName=triggerName;
		this.triggerStartTime=startTime;
		this.triggerEndTime=endTime;
		this.groupNumber=groupNumber;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public double getTriggerStartTime() {
		return triggerStartTime;
	}

	public double getTriggerEndTime() {
		return triggerEndTime;
	}

	public int getGroupNumber() {
		return groupNumber;
	}
	
}
