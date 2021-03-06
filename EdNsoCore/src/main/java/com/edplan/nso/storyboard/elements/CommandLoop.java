package com.edplan.nso.storyboard.elements;
import java.util.List;
import java.util.ArrayList;

public class CommandLoop extends CommandTimeLineGroup
{
	private double loopStartTime;
	private int loopCount;

	public CommandLoop(double startTime,int loopCount){
		this.loopStartTime=startTime;
		this.loopCount=loopCount;
	}

	@Override
	public <T> List<TypedCommand<T>> getCommands(CommandTimelineSelecter<T> timeline,double offset) {
		// TODO: Implement this method
		//return super.getCommands(timeline, offset);
		List<TypedCommand<T>> res=super.getCommands(timeline,offset);
		double commandDuration=getCommandsDuration();
		List<TypedCommand<T>> list=new ArrayList<TypedCommand<T>>(res.size()*loopCount);
		for(int i=0;i<loopCount;i++){
			double loopOffset=loopStartTime+i*commandDuration;
			for(TypedCommand<T> c:res){
				list.add(
					new TypedCommand<T>(
						c.getEasing(),
						c.getStartTime()+loopOffset,
						c.getEndTime()+loopOffset,
						c.getStartValue(),
						c.getEndValue()));
			}
		}
		return list;
	}

	/*
	 @Override
	 public double getCommandsDuration() {
	 // TODO: Implement this method
	 return super.getCommandsEndTime();
	 }
	 */

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
