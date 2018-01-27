package com.edplan.nso.ruleset.std.playing.drawable.piece;
import com.edplan.framework.ui.drawable.EdDrawable;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.ui.drawable.interfaces.IScaleable2D;
import com.edplan.framework.ui.drawable.interfaces.IFadeable;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.graphics.opengl.GLPaint;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.MContext;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.nso.resource.OsuSkin;
import com.edplan.framework.graphics.opengl.objs.Color4;

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
		simpleDrawWithAccentColor(hitcircle,canvas);
		simpleDraw(hitcircleOverlay,canvas);
	}
}
