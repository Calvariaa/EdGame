package com.edplan.nso.ruleset.std.playing.drawable.piece;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.graphics.opengl.GLPaint;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.framework.ui.drawable.EdDrawable;
import com.edplan.framework.ui.drawable.interfaces.IFadeable;
import com.edplan.framework.ui.drawable.interfaces.IScaleable2D;
import com.edplan.nso.resource.OsuSkin;

public abstract class BasePieces extends EdDrawable implements IScaleable2D,IFadeable
{
	private float baseSize;

	private Vec2 origin=new Vec2();

	private Vec2 scale=new Vec2(1, 1);

	protected GLPaint paint=new GLPaint();

	private PreciseTimeline timeline;

	public BasePieces(MContext c,PreciseTimeline timeline){
		super(c);
		this.timeline=timeline;
	}
	
	public void setSkin(OsuSkin skin){
		
	}

	public void setOrigin(Vec2 origin) {
		this.origin.set(origin);
	}

	public Vec2 getOrigin() {
		return origin;
	}

	public void setBaseSize(float baseSize) {
		this.baseSize=baseSize;
	}

	public float getBaseSize() {
		return baseSize;
	}

	@Override
	public void setAlpha(float a) {
		// TODO: Implement this method
		paint.setFinalAlpha(a);
	}

	@Override
	public float getAlpha() {
		// TODO: Implement this method
		return paint.getFinalAlpha();
	}

	@Override
	public void setScale(float sx,float sy) {
		// TODO: Implement this method
		scale.set(sx,sy);
	}

	@Override
	public Vec2 getScale() {
		// TODO: Implement this method
		return scale;
	}
	
	protected void simpleDraw(GLTexture t,GLCanvas2D c){
		c.drawTextureAnchorCenter(t,getOrigin(),(new Vec2(getBaseSize(),getBaseSize())).multiple(getScale()),paint);
	}
}
