package com.edplan.framework.ui.drawable;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.ui.drawable.interfaces.IFadeable;
import com.edplan.framework.ui.drawable.interfaces.IScaleable2D;
import com.edplan.framework.graphics.opengl.GLPaint;

/**
 *用来封装将材质绘制到canvas上的Drawable，有类似canvas.drawTexture的方法
 */
public class TextureDrawable extends EdDrawable implements IFadeable,IScaleable2D
{
	
	private Vec2 org=new Vec2();
	
	private Vec2 baseSize=new Vec2();
	
	private GLPaint basePaint=new GLPaint();
	
	private Vec2 scale=new Vec2(1,1);
	
	public TextureDrawable(MContext c){
		super(c);
		initialPaint();
	}
	
	private void initialPaint(){
		
	}

	@Override
	public void setAlpha(float a) {
		// TODO: Implement this method
		basePaint.setFinalAlpha(a);
	}

	@Override
	public float getAlpha() {
		// TODO: Implement this method
		return basePaint.getFinalAlpha();
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
	}
}
