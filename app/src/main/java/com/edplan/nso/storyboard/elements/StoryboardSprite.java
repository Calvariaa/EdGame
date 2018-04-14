package com.edplan.nso.storyboard.elements;
import java.util.List;
import java.util.ArrayList;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.ui.Anchor;
import com.edplan.nso.storyboard.elements.drawable.BaseDrawableSprite;
import com.edplan.nso.storyboard.PlayingStoryboard;
import com.edplan.nso.storyboard.elements.drawable.ADrawableStoryboardElement;
import com.edplan.framework.ui.animation.QueryAnimation;
import com.edplan.framework.ui.animation.interpolate.ValueInterpolator;
import com.edplan.framework.interfaces.InvokeSetter;
import java.util.Collections;
import java.util.Comparator;
import com.edplan.framework.ui.animation.Easing;
import com.edplan.framework.ui.animation.interpolate.FloatInterpolator;
import com.edplan.framework.ui.animation.interpolate.Vec2Interpolator;
import com.edplan.framework.ui.animation.interpolate.Color4Interpolator;
import com.edplan.framework.ui.animation.interpolate.InvalidInterpolator;
import com.edplan.framework.utils.MLog;

public class StoryboardSprite implements IStoryboardElements
{
	private List<CommandLoop> loops=new ArrayList<CommandLoop>();
	private List<CommandTrigger> triggers=new ArrayList<CommandTrigger>();
	
	private CommandTimeLineGroup commandTimeLineGroup=new CommandTimeLineGroup();
	
	private String path;
	private Vec2 initialPosition;
	private Anchor anchor;
	
	public StringBuilder rawData=new StringBuilder();
	
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
				v2=Math.min(v2,l.getStartTime());
			}
		}
		return Math.min(v1,v2);
	}
	
	public double getEndTime(){
		double v1=commandTimeLineGroup.hasCommands()?commandTimeLineGroup.getCommandsEndTime():Double.MIN_VALUE;
		double v2=Double.MIN_VALUE;
		for(CommandLoop l:loops){
			if(l.hasCommands()){
				v2=Math.max(v2,l.getEndTime());
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
	
	private static Comparator<TypedCommand> comparator=new Comparator<TypedCommand>(){
		@Override
		public int compare(TypedCommand p1,TypedCommand p2) {
			// TODO: Implement this method
			return (int)Math.signum(p1.getStartTime()-p2.getStartTime());
		}
	};
	private <T> void applyCommands(BaseDrawableSprite sprite,List<TypedCommand<T>> command,ValueInterpolator<T> interpolator,InvokeSetter<BaseDrawableSprite,T> setter){
		double offset=sprite.getStartTime();
		double obj_offset=getStartTime();
		QueryAnimation<BaseDrawableSprite,T> anim=new QueryAnimation<BaseDrawableSprite,T>(sprite,obj_offset,interpolator,setter,true);
		//Collections.sort(command,comparator);
		if(command.size()==0)return;
		TypedCommand<T> tmp=command.get(0);
		setter.invoke(sprite,tmp.getStartValue());
		anim.transform(tmp.getStartValue(),offset-obj_offset,Easing.None);
		for(TypedCommand<T> c:command){
			anim.transform(c.getStartValue(),c.getStartTime(),0,c.getEasing());
			anim.transform(c.getEndValue(),c.getStartTime(),c.getDuration(),c.getEasing());
		}
		animations.add(anim);
	}
	
	public static int tmpI=0;
	
	public <T> List<TypedCommand<T>> getCommands(CommandTimelineSelecter<T> selecter){
		List<TypedCommand<T>> list=new ArrayList<TypedCommand<T>>();
		list.addAll(commandTimeLineGroup.getCommands(selecter,0));
		for(CommandLoop l:loops){
			list.addAll(l.getCommands(selecter,0));
		}
		return list;
	}
	
	public String getInitialPath(){
		return path;
	}
	
	public void initialTexture(BaseDrawableSprite sprite,PlayingStoryboard storyboard){
		sprite.setTexture(storyboard.getTexturePool().getTexture(getInitialPath()));
	}

	@Override
	public void onApply(ADrawableStoryboardElement ele,PlayingStoryboard storyboard) {
		// TODO: Implement this method
		if(ele==null)return;
		
		BaseDrawableSprite sprite=(BaseDrawableSprite)ele;
		initialTexture(sprite,storyboard);
		applyCommands(sprite,getCommands(Selecters.SX),FloatInterpolator.Instance,BaseDrawableSprite.X);
		applyCommands(sprite,getCommands(Selecters.SY),FloatInterpolator.Instance,BaseDrawableSprite.Y);
		applyCommands(sprite,getCommands(Selecters.SScale),Vec2Interpolator.Instance,BaseDrawableSprite.Scale);
		applyCommands(sprite,getCommands(Selecters.SRotation),FloatInterpolator.Instance,BaseDrawableSprite.Rotation);
		applyCommands(sprite,getCommands(Selecters.SColour),Color4Interpolator.Instance,BaseDrawableSprite.Color);
		applyCommands(sprite,getCommands(Selecters.SAlpha),FloatInterpolator.Instance,BaseDrawableSprite.Alpha);
		applyCommands(sprite,getCommands(Selecters.SBlendType),InvalidInterpolator.ForBlendType,BaseDrawableSprite.Blend);
		applyCommands(sprite,getCommands(Selecters.SFlipH),InvalidInterpolator.ForBoolean,BaseDrawableSprite.FlipH);
		applyCommands(sprite,getCommands(Selecters.SFlipV),InvalidInterpolator.ForBoolean,BaseDrawableSprite.FlipV);
		for(QueryAnimation a:animations){
			sprite.addAnimation(a);
		}
		animations.clear();
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
	public String[] getTexturePaths() {
		// TODO: Implement this method
		return new String[]{getPath()};
	}

	@Override
	public BaseDrawableSprite createDrawable(PlayingStoryboard storyboard) {
		// TODO: Implement this method
		return new BaseDrawableSprite(storyboard,this);
	}
}
