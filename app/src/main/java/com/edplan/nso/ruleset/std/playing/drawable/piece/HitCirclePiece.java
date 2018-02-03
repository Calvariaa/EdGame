package com.edplan.nso.ruleset.std.playing.drawable.piece;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.math.FMath;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.framework.ui.animation.precise.BasePreciseAnimation;
import com.edplan.framework.ui.drawable.interfaces.IFadeable;
import com.edplan.framework.ui.drawable.interfaces.IScaleable2D;
import com.edplan.nso.resource.OsuSkin;
import com.edplan.nso.ruleset.std.playing.drawable.DrawableStdHitObject;

public class HitCirclePiece extends BasePiece implements IScaleable2D,IFadeable
{
	private GLTexture hitcircle;
	
	private GLTexture hitcircleOverlay;
	
	public HitCirclePiece(MContext c,PreciseTimeline timeline){
		super(c,timeline);
	}
	
	@Override
	public void setSkin(OsuSkin skin){
		super.setSkin(skin);
		hitcircle=skin.hitcircle.getRes();
		hitcircleOverlay=skin.hitcircleOverlay.getRes();
	}

	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		//将note绘制上去
		if(isFinished())return;
		simpleDrawWithAccentColor(hitcircle,canvas);
		simpleDraw(hitcircleOverlay,canvas);
	}
	
	public void fadeIn(DrawableStdHitObject obj){
		(new FadeInAnimation(obj)).post(getTimeline());
	}
	
	public void explode(int startTime){
		(new ExplodeAnimation(startTime)).post(getTimeline());
	}
	
	public class ExplodeAnimation extends BasePreciseAnimation{
		public ExplodeAnimation(int startTime){
			setStartTime(startTime);
			setDuration(300);
			//(int)(m*(DrawableStdHitCircle.this.getHitObject().getStartTime()-getStartTimeAtTimeline())));
		}

		@Override
		public void setProgressTime(int p) {
			// TODO: Implement this method
			super.onProgress(p);
			float fp=Math.max(0,p/(float)getDuration());
			fp=1-fp;
			float a=fp;
			setAlpha(a);
			float s=1.6f-0.6f*fp;
			setScale(s,s);
		}

		@Override
		public void onEnd() {
			// TODO: Implement this method
			super.onEnd();
			finish();
		}
	}

	public class FadeInAnimation extends BasePreciseAnimation{
		DrawableStdHitObject obj;
		public FadeInAnimation(DrawableStdHitObject obj){
			this.obj=obj;
			setDuration(obj.getTimeFadein());
			setStartTime(obj.getShowTime());
			setProgressTime(0);
		}

		@Override
		public void setProgressTime(int p) {
			// TODO: Implement this method
			super.setProgressTime(p);
			float fp=p/(float)getDuration();
			setAlpha(FMath.sin(fp*FMath.PiHalf));
			//Log.v("anim-pro","prog:"+fp);
		}

		@Override
		public void onEnd() {
			// TODO: Implement this method
			super.onEnd();
			//Log.v("test_anim","end:"+getStartTimeAtTimeline());
			explode(obj.getHitObject().getStartTime());
		}
	}
}
