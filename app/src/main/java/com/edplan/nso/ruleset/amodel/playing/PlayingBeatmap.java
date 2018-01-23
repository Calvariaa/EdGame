package com.edplan.nso.ruleset.amodel.playing;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.nso.filepart.PartDifficulty;
import com.edplan.nso.resource.OsuSkin;

public abstract class PlayingBeatmap
{
	private PreciseTimeline timeLine;

	private OsuSkin skin;
	
	public PlayingBeatmap(OsuSkin skin){
		this.skin=skin;
	}

	public void setSkin(OsuSkin skin) {
		this.skin=skin;
	}

	public OsuSkin getSkin() {
		return skin;
	}
	
	public PreciseTimeline getTimeLine() {
		return timeLine;
	}
	
	public abstract PartDifficulty getDifficulty();
}
