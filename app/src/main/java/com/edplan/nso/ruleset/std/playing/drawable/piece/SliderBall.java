package com.edplan.nso.ruleset.std.playing.drawable.piece;
import com.edplan.framework.MContext;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.ui.drawable.interfaces.IRotateable2D;
import com.edplan.framework.graphics.opengl.BaseCanvas;

public class SliderBall extends BasePiece implements IRotateable2D
{
	private float rotation=0;
	
	public SliderBall(MContext c,PreciseTimeline l){
		super(c,l);
	}
	
	@Override
	public void setRotation(float ang) {
		// TODO: Implement this method
		rotation=ang;
	}

	@Override
	public float getRotation() {
		// TODO: Implement this method
		return rotation;
	}

	@Override
	public void draw(BaseCanvas canvas) {
		// TODO: Implement this method
		if(isFinished())return;
	}
}
