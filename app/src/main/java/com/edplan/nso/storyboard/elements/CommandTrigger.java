package com.edplan.nso.storyboard.elements;
import java.util.List;
import java.util.ArrayList;

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

	@Override
	public double getStartTime() {
		// TODO: Implement this method
		return getTriggerStartTime()+getCommandsStartTime();
	}

	@Override
	public double getEndTime() {
		// TODO: Implement this method
		return getTriggerEndTime();
	}

	@Override
	public <T extends Object> List<TypedCommand<T>> getCommands(CommandTimelineSelecter<T> timeline,double offset) {
		// TODO: Implement this method
		List<TypedCommand<T>> raw=timeline.select(this);
		List<TypedCommand<T>> l=new ArrayList<TypedCommand<T>>(raw.size());
		for(TypedCommand<T> c:raw){
			l.add(new TypedCommand<T>(
				c.getEasing(),
				c.getStartTime()+offset+triggerStartTime,
				c.getEndTime()+offset+triggerStartTime,
				c.getStartValue(),
				c.getEndValue()));
		}
		return l;
	}
	
}
