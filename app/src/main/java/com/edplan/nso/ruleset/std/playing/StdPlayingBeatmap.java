package com.edplan.nso.ruleset.std.playing;
import com.edplan.nso.ruleset.amodel.playing.PlayingBeatmap;
import com.edplan.nso.filepart.PartDifficulty;
import com.edplan.framework.MContext;
import com.edplan.nso.ruleset.std.StdBeatmap;
import com.edplan.nso.ruleset.std.playing.drawable.DrawableStdHitObject;
import com.edplan.nso.ruleset.std.objects.StdHitObject;
import com.edplan.nso.ruleset.std.playing.drawable.DrawableStdHitCircle;
import java.util.List;

public class StdPlayingBeatmap extends PlayingBeatmap
{
	private StdBeatmap beatmap;
	
	private MContext context;
	
	public StdPlayingBeatmap(MContext context,StdBeatmap beatmap){
		this.context=context;
		this.beatmap=beatmap;
	}

	public StdBeatmap getBeatmap() {
		return beatmap;
	}

	public MContext getContext() {
		return context;
	}
	
	public List<StdHitObject> getHitObjects(){
		return null;
		//getBeatmap().getHitObjects();
	}
	
	public DrawableStdHitObject createDrawableHitObject(StdHitObject obj){
		return new DrawableStdHitCircle(getContext(),obj);
	}

	@Override
	public PartDifficulty getDifficulty() {
		// TODO: Implement this method
		return getBeatmap().getDifficulty();
	}
}
