package com.edplan.nso.ruleset.std.playing.drawable;
import com.edplan.nso.ruleset.std.objects.StdHitCircle;
import com.edplan.nso.ruleset.std.StdBeatmap;
import com.edplan.framework.ui.drawable.interfaces.IScaleable2D;
import com.edplan.nso.ruleset.std.playing.drawable.piece.HitCirclePiece;
import com.edplan.nso.ruleset.amodel.playing.PlayingBeatmap;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.MContext;
import com.edplan.nso.ruleset.std.objects.StdHitObject;
import com.edplan.nso.ruleset.amodel.playing.Judgment;
import com.edplan.nso.ruleset.std.playing.judgment.StdJudgment;
import com.edplan.framework.ui.animation.precise.BasePreciseAnimation;

public class DrawableStdHitCircle extends DrawableStdHitObject
{
	private HitCirclePiece circlePiece;
	
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
	public void applyDefault(PlayingBeatmap beatmap) {
		// TODO: Implement this method
		super.applyDefault(beatmap);
		circlePiece=new HitCirclePiece(getContext(),beatmap.getTimeLine());
		circlePiece.setOrigin(getOrigin());
		circlePiece.setBaseSize(getBaseSize());
		circlePiece.setTexture(beatmap.getSkin().getHitcircleTexture());
	}

	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		super.draw(canvas);
		circlePiece.draw(canvas);
	}

	@Override
	public void setAlpha(float a) {
		// TODO: Implement this method
		super.setAlpha(a);
		circlePiece.setAlpha(a);
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

	public class FadeInAnimation extends BasePreciseAnimation{
		
		public FadeInAnimation(){
			setDuration(getTimeFadein());
			setStartTime(getShowTime());
		}

		@Override
		public void onProgress(int p) {
			// TODO: Implement this method
			super.onProgress(p);
			float fp=p/(float)getDuration();
			circlePiece.setAlpha(fp);
		}
	}
	
	public class FadeOutAnimation extends BasePreciseAnimation{
		public FadeOutAnimation(int startTime){
			setStartTime(startTime);
			//setDuration(gett
		}
	}
}
