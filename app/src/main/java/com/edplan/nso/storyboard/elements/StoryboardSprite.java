package com.edplan.nso.storyboard.elements;
import java.util.List;
import java.util.ArrayList;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.ui.Anchor;
import com.edplan.nso.storyboard.elements.drawable.BaseDrawableSprite;
import com.edplan.nso.storyboard.PlayingStoryboard;
import com.edplan.nso.storyboard.elements.drawable.ADrawableStoryboardElement;
import com.edplan.framework.ui.animation.QueryAnimation;

public class StoryboardSprite implements IStoryboardElements
{
	private List<CommandLoop> loops=new ArrayList<CommandLoop>();
	private List<CommandTrigger> triggers=new ArrayList<CommandTrigger>();
	
	private CommandTimeLineGroup commandTimeLineGroup=new CommandTimeLineGroup();
	
	private String path;
	private Vec2 initialPosition;
	private Anchor anchor;
	
	private List<QueryAnimation> animations=new ArrayList<QueryAnimation>();

	public StoryboardSprite(String path,Anchor anchor,Vec2 initialPosition){
		this.path=path;
		this.initialPosition=initialPosition;
		this.anchor=anchor;
	}

	public void setInitialPosition(Vec2 initialPosition) {
		this.initialPosition=initialPosition;
	}

	public Vec2 getInitialPosition() {
		return initialPosition;
	}

	public void setAnchor(Anchor anchor) {
		this.anchor=anchor;
	}

	public Anchor getAnchor() {
		return anchor;
	}

	public double getStartTime(){
		double v1=commandTimeLineGroup.hasCommands()?commandTimeLineGroup.getCommandsStartTime():Double.MAX_VALUE;
		double v2=Double.MAX_VALUE;
		for(CommandLoop l:loops){
			if(l.hasCommands()){
				v2=Math.min(v2,l.getCommandsStartTime());
			}
		}
		return Math.min(v1,v2);
	}
	
	public double getEndTime(){
		double v1=commandTimeLineGroup.hasCommands()?commandTimeLineGroup.getCommandsEndTime():Double.MIN_VALUE;
		double v2=Double.MIN_VALUE;
		for(CommandLoop l:loops){
			if(l.hasCommands()){
				v2=Math.max(v2,l.getCommandsEndTime());
			}
		}
		return Math.max(v1,v2);
	}

	public double getDuration(){
		return getEndTime()-getStartTime();
	}

	public CommandTimeLineGroup getCommandTimeLineGroup() {
		return commandTimeLineGroup;
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
	public void finalBuild(){
		QueryAnimation<BaseDrawableSprite,Float> x;
	}

	@Override
	public void onApply(ADrawableStoryboardElement ele,PlayingStoryboard storyboard) {
		// TODO: Implement this method
		BaseDrawableSprite sprite=(BaseDrawableSprite)ele;
		for(QueryAnimation a:animations){
			sprite.addAnimation(a);
		}
	}

	@Override
	public boolean isDrawable() {
		// TODO: Implement this method
		return hasCommands();
	}
	
	public void setPath(String path){
		this.path=path;
	}

	@Override
	public String getPath() {
		// TODO: Implement this method
		return path;
	}

	@Override
	public BaseDrawableSprite createDrawable(PlayingStoryboard storyboard) {
		// TODO: Implement this method
		return new BaseDrawableSprite(storyboard,this);
	}
}
