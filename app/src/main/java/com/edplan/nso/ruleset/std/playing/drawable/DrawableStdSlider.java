package com.edplan.nso.ruleset.std.playing.drawable;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.line.LinePath;
import com.edplan.framework.math.Vec2;
import com.edplan.nso.ruleset.amodel.playing.PlayingBeatmap;
import com.edplan.nso.ruleset.std.objects.StdSlider;
import com.edplan.nso.ruleset.std.objects.drawables.StdSliderPathMaker;
import com.edplan.nso.ruleset.std.playing.drawable.piece.SliderBody;
import com.edplan.nso.ruleset.std.playing.drawable.piece.HitCirclePiece;
import com.edplan.framework.ui.animation.precise.BasePreciseAnimation;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.ui.animation.AnimationHelper;
import com.edplan.nso.ruleset.std.playing.drawable.interfaces.IHasApproachCircle;
import com.edplan.nso.ruleset.std.playing.drawable.piece.ApproachCircle;

public class DrawableStdSlider extends DrawableStdHitObject implements IHasApproachCircle
{
	private ApproachCircle approachCircle;
	
	private StdSlider slider;
	
	private LinePath path;
	
	private Vec2 endPoint;
	
	private SliderBody body;
	
	private HitCirclePiece startPiece;
	
	public DrawableStdSlider(MContext c,StdSlider slider){
		super(c,slider);
		this.slider=slider;
	}

	@Override
	public ApproachCircle getApproachCircle() {
		// TODO: Implement this method
		return approachCircle;
	}

	public LinePath getPath() {
		return path;
	}

	@Override
	public Vec2 getEndPoint() {
		// TODO: Implement this method
		return endPoint;
	}

	@Override
	public void applyDefault(PlayingBeatmap beatmap) {
		// TODO: Implement this method
		super.applyDefault(beatmap);
		StdSliderPathMaker maker=new StdSliderPathMaker(slider.getPath());
		maker.setBaseSize(getBaseSize());
		path=maker.calculatePath();
		path.measure();
		path.bufferLength((float)slider.getPixelLength());
		endPoint=path.getMeasurer().atLength((float)slider.getPixelLength());
		body=new SliderBody(getContext(),beatmap.getTimeLine(),this);
		applyPiece(body,beatmap);
		startPiece=new HitCirclePiece(getContext(),beatmap.getTimeLine());
		applyPiece(startPiece,beatmap);
		approachCircle=new ApproachCircle(getContext(),getTimeLine());
		applyPiece(approachCircle,beatmap);
	}

	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		super.draw(canvas);
		body.draw(canvas);
		startPiece.draw(canvas);
	}

	@Override
	public void onShow() {
		// TODO: Implement this method
		super.onShow();
		startPiece.fadeIn(this);
		(new ShowSliderAnimation()).post(getTimeLine());
		approachCircle.fadeAndScaleIn(this);
	}
	
	public class ShowSliderAnimation extends BasePreciseAnimation{
		public ShowSliderAnimation(){
			setStartTime(getShowTime());
			setDuration(getTimeFadein());
		}

		@Override
		public void onProgress(int p) {
			// TODO: Implement this method
			super.onProgress(p);
			float fp=AnimationHelper.getFloatProgress(p,getDuration());
			body.setAlpha(fp);
			body.setProgress2(fp);
		}

		@Override
		public void onFinish() {
			// TODO: Implement this method
			super.onFinish();
			(new FadeOutAnimation()).post(getTimeLine());
		}
	}
	
	public class FadeOutAnimation extends BasePreciseAnimation{
		public FadeOutAnimation(){
			setStartTime(getObjStartTime());
			setDuration(400);
		}

		@Override
		public void onProgress(int p) {
			// TODO: Implement this method
			super.onProgress(p);
			float fp=AnimationHelper.getFloatProgress(p,getDuration());
			body.setAlpha(1-fp);
		}

		@Override
		public void onFinish() {
			// TODO: Implement this method
			super.onFinish();
			finish();
		}
	}
	
}
