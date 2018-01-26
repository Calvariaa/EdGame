package com.edplan.nso.ruleset.std.playing.drawable;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.math.FMath;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.ui.animation.precise.BasePreciseAnimation;
import com.edplan.nso.ruleset.amodel.playing.Judgment;
import com.edplan.nso.ruleset.amodel.playing.PlayingBeatmap;
import com.edplan.nso.ruleset.std.objects.StdHitCircle;
import com.edplan.nso.ruleset.std.objects.StdHitObject;
import com.edplan.nso.ruleset.std.playing.drawable.interfaces.IHasApproachCircle;
import com.edplan.nso.ruleset.std.playing.drawable.piece.ApproachCircle;
import com.edplan.nso.ruleset.std.playing.drawable.piece.BasePieces;
import com.edplan.nso.ruleset.std.playing.drawable.piece.HitCirclePiece;
import com.edplan.nso.ruleset.std.playing.judgment.StdJudgment;

public class DrawableStdHitCircle extends DrawableStdHitObject implements IHasApproachCircle
{
	private HitCirclePiece circlePiece;
	
	private ApproachCircle approachCircle;
	
	private Color4 accentColor=new Color4(1,1,1,1);
	
	public DrawableStdHitCircle(MContext c,StdHitCircle obj){
		super(c,obj);
	}
	
	/**
	 *测试用的构造方法，之后大概率被弃用
	 */
	public DrawableStdHitCircle(MContext c,StdHitObject obj){
		super(c,obj);
	}

	@Override
	public ApproachCircle getApproachCircle() {
		// TODO: Implement this method
		return approachCircle;
	}

	@Override
	public void applyDefault(PlayingBeatmap beatmap) {
		// TODO: Implement this method
		super.applyDefault(beatmap);
		circlePiece=new HitCirclePiece(getContext(),beatmap.getTimeLine());
		applyPiece(circlePiece,beatmap);
		approachCircle=new ApproachCircle(getContext(),beatmap.getTimeLine());
		applyPiece(approachCircle,beatmap);
	}
	
	private void applyPiece(BasePieces p,PlayingBeatmap beatmap){
		p.setOrigin(getOrigin());
		p.setBaseSize(getBaseSize());
		p.setSkin(beatmap.getSkin());
	}

	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		super.draw(canvas);
		circlePiece.draw(canvas);
	}
	
	public void setAccentColor(Color4 accentColor) {
		this.accentColor=accentColor;
	}

	public Color4 getAccentColor() {
		return accentColor;
	}

	@Override
	public void setAlpha(float a) {
		// TODO: Implement this method
		super.setAlpha(a);
		if(circlePiece!=null)circlePiece.setAlpha(a);
		if(approachCircle!=null)approachCircle.setAlpha(a);
	}

	@Override
	public void setBaseSize(float baseSize) {
		// TODO: Implement this method
		super.setBaseSize(baseSize);
		//circlePiece.setBaseSize(baseSize);
	}

	@Override
	public void setOrigin(Vec2 origin) {
		// TODO: Implement this method
		super.setOrigin(origin);
	}

	@Override
	public void onShow() {
		// TODO: Implement this method
		super.onShow();
		(new FadeInAnimation()).post(getTimeLine());
		(new ApproachCircle.PreemptAnimation(this,approachCircle)).post(getTimeLine());
	}
	
	@Override
	public void onJudgment(Judgment judgment) {
		// TODO: Implement this method
		super.onJudgment(judgment);
		StdJudgment judg=(StdJudgment)judgment;
		switch(judg.getLevel()){
			case S50:
			case S100:
			case S300:
				
				break;
			case Miss:
				
				break;
			case None:
				break;
			default:break;
		}
	}

	public class TestOutAnimation extends BasePreciseAnimation{
		static final float m=5;
		
		public TestOutAnimation(int startTime){
			setStartTime(startTime);
			setDuration(300);
			//(int)(m*(DrawableStdHitCircle.this.getHitObject().getStartTime()-getStartTimeAtTimeline())));
		}

		
		@Override
		public void onProgress(int p) {
			// TODO: Implement this method
			super.onProgress(p);
			float fp=Math.max(0,p/(float)getDuration());
			fp=1-fp;
			float a=FMath.sin(fp*FMath.PiHalf);
			circlePiece.setAlpha(a);
			if(p>0)approachCircle.setAlpha(0);
			float s=1.6f-0.6f*fp;
			circlePiece.setScale(s,s);
		}

		@Override
		public void onEnd() {
			// TODO: Implement this method
			super.onEnd();
			finish();
		}
	}
	
	
	
	public class FadeInAnimation extends BasePreciseAnimation{
		
		public FadeInAnimation(){
			setDuration(getTimeFadein());
			setStartTime(getShowTime());
			setProgressTime(0);
		}

		@Override
		public void setProgressTime(int p) {
			// TODO: Implement this method
			super.setProgressTime(p);
			float fp=p/(float)getDuration();
			setAlpha(fp);
			//Log.v("anim-pro","prog:"+fp);
		}

		@Override
		public void onEnd() {
			// TODO: Implement this method
			super.onEnd();
			//Log.v("test_anim","end:"+getStartTimeAtTimeline());
			(new TestOutAnimation(getHitObject().getStartTime())).post(getTimeLine());
		}
	}
	
	public class FadeOutAnimation extends BasePreciseAnimation{
		public FadeOutAnimation(int startTime){
			setStartTime(startTime);
			//setDuration(gett
		}
	}
}
