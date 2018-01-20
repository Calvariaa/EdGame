package com.edplan.nso.ruleset.std.playing.drawable;
import com.edplan.nso.ruleset.std.objects.StdHitCircle;
import com.edplan.nso.ruleset.std.StdBeatmap;
import com.edplan.framework.ui.drawable.interfaces.IScaleable2D;
import com.edplan.nso.ruleset.std.playing.drawable.piece.HitCirclePiece;
import com.edplan.nso.ruleset.amodel.playing.PlayingBeatmap;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.math.Vec2;

public class DrawableStdHitCircle extends DrawableStdHitObject
{
	private HitCirclePiece circlePiece;
	
	public DrawableStdHitCircle(StdHitCircle obj,StdBeatmap beatmap){
		super(obj);
		circlePiece=new HitCirclePiece();
	}

	@Override
	public void applyDefault(PlayingBeatmap beatmap) {
		// TODO: Implement this method
		super.applyDefault(beatmap);
		circlePiece.setOrigin(getOrigin());
		circlePiece.setBaseSize(getBaseSize());
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
	
	
}
