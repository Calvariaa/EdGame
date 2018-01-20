package com.edplan.nso.ruleset.amodel.playing.drawable;
import com.edplan.framework.ui.drawable.EdDrawable;
import com.edplan.nso.ruleset.amodel.playing.Judgment;
import com.edplan.framework.ui.drawable.interfaces.IFadeable;
import com.edplan.framework.timing.PreciseTimeline;

public abstract class DrawableHitObject extends EdDrawable implements IFadeable
{
	/**
	 *这个物件出现的时间，此处的时间为getTimeLine()中的时间
	 */
	public abstract float getShowTime();
	
	public abstract void onShow();
	
	public abstract void onJudgment(Judgment judgment);
	
	public abstract PreciseTimeline getTimeLine();
}
