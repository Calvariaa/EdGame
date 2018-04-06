package com.edplan.nso.storyboard.elements;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.utils.stream.MaxMin;

public class CommandTimeLineGroup
{
	public CommandTimeLine<Vec2> XY=new CommandTimeLine<Vec2>();
	public CommandTimeLine<Vec2> Scale=new CommandTimeLine<Vec2>();
	public CommandTimeLine<Float> Rotation=new CommandTimeLine<Float>();
	public CommandTimeLine<Color4> Colour=new CommandTimeLine<Color4>();
	public CommandTimeLine<Float> Alpha=new CommandTimeLine<Float>();
	public CommandTimeLine<Boolean> BlendingMode=new CommandTimeLine<Boolean>();
	public CommandTimeLine<Boolean> FlipH=new CommandTimeLine<Boolean>();
	public CommandTimeLine<Boolean> FlipV=new CommandTimeLine<Boolean>();
	
	private CommandTimeLine[] timeLines={XY,Scale,Rotation,Colour,Alpha,BlendingMode,FlipH,FlipV};
	
	public boolean hasCommands(){
		for(CommandTimeLine c:timeLines){
			if(c.hasCommands())return true;
		}
		return false;
	}
	
	public double getCommandsStartTime(){
		double startTime=Double.MAX_VALUE;
		for(CommandTimeLine t:timeLines){
			if(t.hasCommands()){
				if(t.getStartTime()<startTime){
					startTime=t.getStartTime();
				}
			}
		}
		return startTime;
	}
	
	public double getCommandsEndTime(){
		double endTime=Double.MIN_VALUE;
		for(CommandTimeLine t:timeLines){
			if(t.hasCommands()){
				if(t.getEndTime()>endTime){
					endTime=t.getEndTime();
				}
			}
		}
		return endTime;
	}
	
	public double getCommandsDuration(){
		return getCommandsEndTime()-getCommandsStartTime();
	}
	
	public double getStartTime(){
		return getCommandsStartTime();
	}
	
	public double getEndTime(){
		return getCommandsEndTime();
	}
}
