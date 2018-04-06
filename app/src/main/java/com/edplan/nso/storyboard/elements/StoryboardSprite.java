package com.edplan.nso.storyboard.elements;
import java.util.List;
import java.util.ArrayList;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.ui.Anchor;

public class StoryboardSprite implements IStoryboardElements
{
	private List<CommandLoop> loops=new ArrayList<CommandLoop>();
	private List<CommandTrigger> triggers=new ArrayList<CommandTrigger>();
	
	private CommandTimeLineGroup commandTimeLineGroup=new CommandTimeLineGroup();
	
	private CommandTimeLineGroup startTimeObj;
	private CommandTimeLineGroup endTimeObj;
	
	private String path;
	private Vec2 initialPosition;
	private Anchor anchor;

	public StoryboardSprite(String path,Anchor anchor,Vec2 initialPosition){
		this.path=path;
		this.initialPosition=initialPosition;
		this.anchor=anchor;
	}
	
	public boolean hasCommands(){
		if(commandTimeLineGroup.hasCommands()){
			return true;
		}else{
			for(CommandLoop c:loops){
				if(c.hasCommands())return true;
			}
			return false;
		}
	}
	
	private void onAddCommand(CommandTimeLineGroup command){
		//if(startTimeObj==null||startTimeObj.getc
	}
	
	public CommandLoop addLoop(double startTime,int loopCount){
		CommandLoop loop=new CommandLoop(startTime,loopCount);
		loops.add(loop);
		
		return loop;
	}
	
	public CommandTrigger addTrigger(String triggerName,double startTime,double endTime,int groupNumber){
		CommandTrigger c=new CommandTrigger(triggerName,startTime,endTime,groupNumber);
		triggers.add(c);
		return c;
	}

	@Override
	public boolean isDrawable() {
		// TODO: Implement this method
		return true;
	}
	
	public void setPath(String path){
		this.path=path;
	}

	@Override
	public String getPath() {
		// TODO: Implement this method
		return path;
	}

	
}
