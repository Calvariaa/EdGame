package com.edplan.nso.ruleset.std.playing.drawable.piece;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.framework.ui.animation.precise.BasePreciseAnimation;
import com.edplan.nso.resource.OsuSkin;
import com.edplan.nso.ruleset.std.playing.drawable.DrawableStdHitObject;
import com.edplan.framework.graphics.opengl.objs.Color4;

public class ApproachCircle extends BasePiece
{
	private GLTexture approachCircle;
	
	public ApproachCircle(MContext c,PreciseTimeline l){
		super(c,l);
	}

	@Override
	public void setSkin(OsuSkin skin) {
		// TODO: Implement this method
		super.setSkin(skin);
		approachCircle=skin.approachCircle.getRes();
	}
	
	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		simpleDrawWithAccentColor(approachCircle,canvas);
	}
	
	public static class PreemptAnimation extends BasePreciseAnimation{

		private ApproachCircle approachCircle;
		
		public PreemptAnimation(DrawableStdHitObject obj,ApproachCircle c){
			approachCircle=c;
			setDuration(obj.getTimePreempt());
			setStartTime(obj.getShowTime());
			setProgressTime(0);
		}

		@Override
		public void setProgressTime(int p) {
			// TODO: Implement this method
			super.setProgressTime(p);
			float fp=p/(float)getDuration();
			float s=3f*(1-fp)+1.1f*fp;
			approachCircle.setScale(s,s);
		}

	}
}
