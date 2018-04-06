package com.edplan.nso.storyboard.elements;
import com.edplan.nso.storyboard.elements.TypedCommand;
import java.util.ArrayList;
import java.util.List;
import com.edplan.framework.ui.animation.Easing;
import com.edplan.superutils.DefUtil;

public class CommandTimeLine<T> implements ICommandTimeLine
{
	private List<TypedCommand> commands=new ArrayList<TypedCommand>();
	
	private TypedCommand<T> startValueCommand;
	
	private TypedCommand<T> endValueCommand;
	
	public void add(Easing easing,double startTime,double endTime,T startValue,T endValue){
		if(endTime<startTime)return;
		TypedCommand<T> command=new TypedCommand<T>();
		command.setEasing(easing);
		command.setStartTime(startTime);
		command.setEndTime(endTime);
		command.setStartValue(startValue);
		command.setEndValue(endValue);
		
		commands.add(command);
		
		if(startValue==null||startValueCommand.getStartTime()>command.getStartTime()){
			startValueCommand=command;
		}
		if(endValue==null||endValueCommand.getEndTime()<command.getEndTime()){
			endValueCommand=command;
		}
	}
	
	public void iterateCommands(){
		
	}
	
	public T getStartValue(){
		return (startValueCommand!=null)?(startValueCommand.getStartValue()):null;
	}
	
	@Override
	public double getStartTime() {
		// TODO: Implement this method
		return (startValueCommand!=null)?startValueCommand.getStartTime():Double.MIN_VALUE;
	}

	@Override
	public double getEndTime() {
		// TODO: Implement this method
		return (endValueCommand!=null)?endValueCommand.getEndTime():Double.MAX_VALUE;
	}

	@Override
	public boolean hasCommands() {
		// TODO: Implement this method
		return commands.size()>0;
	}

}
