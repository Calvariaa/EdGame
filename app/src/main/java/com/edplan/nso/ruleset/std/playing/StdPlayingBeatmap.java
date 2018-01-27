package com.edplan.nso.ruleset.std.playing;
import com.edplan.framework.MContext;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.nso.filepart.PartDifficulty;
import com.edplan.nso.resource.OsuSkin;
import com.edplan.nso.ruleset.amodel.playing.PlayingBeatmap;
import com.edplan.nso.ruleset.std.StdBeatmap;
import com.edplan.nso.ruleset.std.objects.StdHitObject;
import com.edplan.nso.ruleset.std.playing.drawable.DrawableStdHitCircle;
import com.edplan.nso.ruleset.std.playing.drawable.DrawableStdHitObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.edplan.nso.ruleset.std.playing.drawable.DrawableStdFollowpoint;

public class StdPlayingBeatmap extends PlayingBeatmap
{
	private StdBeatmap beatmap;
	
	private MContext context;
	
	private List<DrawableStdHitObject> drawableHitObjects;
	
	private List<DrawableStdHitObject> connectionObjs;
	
	public StdPlayingBeatmap(MContext context,StdBeatmap beatmap,PreciseTimeline timeline,OsuSkin skin){
		super(skin,timeline);
		this.context=context;
		this.beatmap=beatmap;
		calDrawables();
	}

	public void calDrawables(){
		int objCount=getHitObjects().size();
		drawableHitObjects=new ArrayList<DrawableStdHitObject>(objCount);
		DrawableStdHitObject dobj;
		for(StdHitObject obj:getHitObjects()){
			dobj=createDrawableHitObject(obj);
			dobj.applyDefault(this);
			drawableHitObjects.add(dobj);
		}
		//此时依然保持原顺序，计算followpoints
		connectionObjs=new ArrayList<DrawableStdHitObject>(objCount);
		DrawableStdHitObject pre=(objCount!=0)?drawableHitObjects.get(0):null;
		DrawableStdHitObject now;
		DrawableStdFollowpoint followpoint;
		for(int i=1;i<objCount;i++){
			now=drawableHitObjects.get(i);
			if(!now.getHitObject().isNewCombo()){
				followpoint=new DrawableStdFollowpoint(getContext(),pre,now);
				followpoint.applyDefault(this);
				connectionObjs.add(followpoint);
			}
			pre=now;
		}
		/*
		Collections.sort(connectionObjs, new Comparator<DrawableStdHitObject>(){
				@Override
				public int compare(DrawableStdHitObject p1,DrawableStdHitObject p2) {
					// TODO: Implement this method
					return (int)Math.signum(p1.getShowTime()-p2.getShowTime());
				}
			});
		Collections.sort(drawableHitObjects, new Comparator<DrawableStdHitObject>(){
				@Override
				public int compare(DrawableStdHitObject p1,DrawableStdHitObject p2) {
					// TODO: Implement this method
					return (int)Math.signum(p1.getShowTime()-p2.getShowTime());
				}
			});*/
	}
	
	public StdBeatmap getBeatmap() {
		return beatmap;
	}

	public MContext getContext() {
		return context;
	}
	
	public List<StdHitObject> getHitObjects(){
		return getBeatmap().getHitObjects().getHitObjectList();
	}
	
	public List<DrawableStdHitObject> getDrawableHitObjects(){
		return drawableHitObjects;
	}
	
	public List<DrawableStdHitObject> getDrawableConnections(){
		return connectionObjs;
	}
	
	public DrawableStdHitObject createDrawableHitObject(StdHitObject obj){
		DrawableStdHitObject dobj=new DrawableStdHitCircle(getContext(),obj);
		if(obj.isNewCombo()){
			getSkin().comboColorGenerater.skipColors(obj.getComboColorsSkip());
			dobj.setAccentColor(getSkin().comboColorGenerater.nextColor());
		}else{
			dobj.setAccentColor(getSkin().comboColorGenerater.currentColor());
		}
		return dobj;
	}

	@Override
	public PartDifficulty getDifficulty() {
		// TODO: Implement this method
		return getBeatmap().getDifficulty();
	}
}
