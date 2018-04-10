package com.edplan.nso.storyboard.elements;
import com.edplan.nso.storyboard.elements.TypedCommand;
import java.util.ArrayList;
import java.util.List;
import com.edplan.framework.ui.animation.Easing;
import com.edplan.superutils.DefUtil;
import java.util.Collections;
import java.util.Comparator;

public class CommandTimeLine<T> implements ICommandTimeLine
{
	private static final boolean STRICT_SORT=true;
	
	private List<TypedCommand<T>> commands=new ArrayList<TypedCommand<T>>();
	
	private TypedCommand<T> startValueCommand;
	
	private TypedCommand<T> endValueCommand;
	
	public void add(Easing easing,double startTime,double endTime,T startValue,T endValue){
		if(endTime<startTime)return;
		TypedCommand<T> command=new TypedCommand<T>(easing,startTime,endTime,startValue,endValue);
		
		commands.add(command);
		
		if(startValue==null||startValueCommand.getStartTime()>command.getStartTime()){
			startValueCommand=command;
		}
		if(endValue==null||endValueCommand.getEndTime()<command.getEndTime()){
			endValueCommand=command;
		}
	}
	
	public List<TypedCommand<T>> getCommands(){
		if(STRICT_SORT)Collections.sort(commands, new Comparator<TypedCommand<T>>(){
				@Override
				public int compare(TypedCommand<T> p1,TypedCommand<T> p2) {
					// TODO: Implement this method
					return (int)Math.signum(p2.getStartTime()-p1.getStartTime());
				}
			});
		return commands;
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
