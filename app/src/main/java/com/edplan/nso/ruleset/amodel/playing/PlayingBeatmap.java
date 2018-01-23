package com.edplan.nso.ruleset.amodel.playing;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.nso.filepart.PartDifficulty;

public abstract class PlayingBeatmap
{
	private PreciseTimeline timeLine;

	public PreciseTimeline getTimeLine() {
		return timeLine;
	}
	
	public abstract PartDifficulty getDifficulty();
}
