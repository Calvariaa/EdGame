package com.edplan.nso.ruleset.std.playing;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.nso.ruleset.amodel.playing.PlayField;
import com.edplan.nso.ruleset.amodel.playing.PlayingBeatmap;
import com.edplan.nso.ruleset.std.playing.drawable.DrawableStdHitObject;
import java.util.List;
import com.edplan.nso.ruleset.std.playing.drawable.interfaces.IHasApproachCircle;
import java.util.Iterator;
import com.edplan.nso.ruleset.std.StdBeatmap;

public class StdPlayField extends PlayField
{
	private List<DrawableStdHitObject> drawableHitObjects;
	
	private int savedIndex=0;
	
	private PreciseTimeline timeline;
	
	//private approachCircleLayer;
	
	//private connectionLayer;
	
	private List<DrawableStdHitObject> hitObjectsInField;
	
	public StdPlayField(MContext context,PreciseTimeline timeline){
		super(context);
		this.timeline=timeline;
	}

	@Override
	public void applyBeatmap(PlayingBeatmap res) {
		// TODO: Implement this method
		if(res instanceof StdPlayingBeatmap){
			StdPlayingBeatmap beatmap=(StdPlayingBeatmap)res;
			
		}else{
			throw new IllegalArgumentException("you can only apply a StdPlayingBeatmap to StdPlayField");
		}
	}
	
	private void showHitObject(DrawableStdHitObject obj){
		hitObjectsInField.add(obj);
		obj.onShow();
		if(obj instanceof IHasApproachCircle){
			//添加一个ApproachCircle
		}
	}
	
	private void removeHitObject(DrawableStdHitObject obj){
		obj.onFinish();
	}
	
	protected void pullNewAdded(int curTime){
		DrawableStdHitObject obj;
		for(int i=savedIndex;i<drawableHitObjects.size();i++){
			obj=drawableHitObjects.get(i);
			if(obj.getShowTime()<=curTime){
				showHitObject(obj);
			}else{
				break;
			}
		}
	}
	
	protected void deleteFinished(){
		Iterator<DrawableStdHitObject> iter=hitObjectsInField.iterator();
		DrawableStdHitObject obj;
		while(iter.hasNext()){
			obj=iter.next();
			if(obj.isFinished()){
				removeHitObject(obj);
				iter.remove();
			}
		}
	}
	
	protected void drawContentLayer(GLCanvas2D canvas){
		for(DrawableStdHitObject obj:hitObjectsInField){
			obj.draw(canvas);
		}
	}
	
	private void drawConnectionLayer(GLCanvas2D canvas){
		
	}
	
	private void drawApproachCircleLayer(GLCanvas2D canvas){
		
	}
	
	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		int curTime=timeline.frameTime();
		//添加新的物件
		pullNewAdded(curTime);
		deleteFinished();
		drawConnectionLayer(canvas);
		drawConnectionLayer(canvas);
		drawApproachCircleLayer(canvas);
	}
}
