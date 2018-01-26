package com.edplan.nso.ruleset.std.playing.drawable;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.nso.difficulty.DifficultyUtil;
import com.edplan.nso.ruleset.amodel.playing.Judgment;
import com.edplan.nso.ruleset.amodel.playing.PlayingBeatmap;
import com.edplan.nso.ruleset.amodel.playing.drawable.DrawableHitObject;
import com.edplan.nso.ruleset.std.objects.StdHitObject;

public class DrawableStdHitObject extends DrawableHitObject
{
	private float baseSize=64;
	
	private Vec2 origin=new Vec2();
	
	private StdHitObject hitObject;
	
	private int showTime;
	
	private int timePreempt;
	
	private int timeFadein;
	
	private float alpha=1;
	
	private PreciseTimeline timeLine;
	
	private boolean finished=false;
	
	private Color4 accentColor=new Color4(1,1,1,1);

	public DrawableStdHitObject(MContext c,StdHitObject obj){
		super(c);
		hitObject=obj;
		setOrigin(new Vec2(obj.getStartX(),obj.getStartY()));
	}

	public void setAccentColor(Color4 accentColor) {
		this.accentColor.set(accentColor);
	}

	public Color4 getAccentColor() {
		return accentColor;
	}

	public StdHitObject getHitObject() {
		return hitObject;
	}

	public void setTimePreempt(int timePreempt) {
		this.timePreempt=timePreempt;
	}

	public int getTimePreempt() {
		return timePreempt;
	}

	public void setTimeFadein(int timeFadein) {
		this.timeFadein=timeFadein;
	}

	public int getTimeFadein() {
		return timeFadein;
	}

	public void setBaseSize(float baseSize) {
		this.baseSize=baseSize;
	}

	public float getBaseSize() {
		return baseSize;
	}

	public void setOrigin(Vec2 origin) {
		this.origin.set(origin);
	}

	public Vec2 getOrigin() {
		return origin;
	}

	/**
	 *通过HitObject无法确定的属性在这里设置（如和难度相关的）
	 */
	public void applyDefault(PlayingBeatmap beatmap){
		timeLine=beatmap.getTimeLine();
		timePreempt=(int)(DifficultyUtil.stdHitObjectTimePreempt(beatmap.getDifficulty().getApproachRate())*0.4);
		timeFadein=(int)(DifficultyUtil.stdHitObjectTimeFadein(beatmap.getDifficulty().getApproachRate())*0.4);
		baseSize*=DifficultyUtil.stdCircleSizeScale(beatmap.getDifficulty().getCircleSize());
		showTime=hitObject.getStartTime()-timePreempt;
	}
	
	/**
	 *通知PlayField回收这个HitObject
	 */
	public void finish(){
		finished=true;
	}
	
	public void onFinish(){
		
	}
	
	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
	}

	@Override
	public void setAlpha(float a) {
		// TODO: Implement this method
		this.alpha=a;
	}

	@Override
	public float getAlpha() {
		// TODO: Implement this method
		return alpha;
	}

	@Override
	public int getShowTime() {
		// TODO: Implement this method
		return showTime;
	}

	@Override
	public void onShow() {
		// TODO: Implement this method
	}

	@Override
	public void onJudgment(Judgment judgment) {
		// TODO: Implement this method
	}

	@Override
	public PreciseTimeline getTimeLine() {
		// TODO: Implement this method
		return timeLine;
	}
	
	@Override
	public boolean isFinished() {
		// TODO: Implement this method
		return finished;
	}
}
