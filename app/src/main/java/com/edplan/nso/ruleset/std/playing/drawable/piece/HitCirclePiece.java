package com.edplan.nso.ruleset.std.playing.drawable.piece;
import com.edplan.framework.ui.drawable.EdDrawable;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.ui.drawable.interfaces.IScaleable2D;
import com.edplan.framework.ui.drawable.interfaces.IFadeable;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.graphics.opengl.GLPaint;
import com.edplan.framework.graphics.opengl.objs.GLTexture;

public class HitCirclePiece extends EdDrawable implements IScaleable2D,IFadeable
{
	private float baseSize;
	
	private Vec2 origin=new Vec2();
	
	private Vec2 scale=new Vec2(1, 1);
	
	private GLPaint paint=new GLPaint();
	
	private GLTexture texture;

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
	
	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		//将note绘制上去
		canvas.drawTextureAnchorCenter(texture,getOrigin(),new Vec2(getBaseSize(),getBaseSize()),paint);
	}
}
