package com.edplan.nso.storyboard.elements.drawable;
import com.edplan.framework.graphics.opengl.objs.AbstractTexture;
import com.edplan.framework.ui.Anchor;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.ui.drawable.EdDrawable;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.math.Quad;
import com.edplan.framework.math.RectF;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.BlendType;
import com.edplan.framework.graphics.opengl.GLWrapped;

public class BaseDrawableStoryboardSprite extends EdDrawable
{
	private AbstractTexture texture;
	
	private Anchor anchor=Anchor.Center;
	
	private Vec2 currentPosition=new Vec2();
	
	private float rotation=0;
	
	private Vec2 scale=new Vec2(1,1);
	
	private float alpha=1;
	
	private Color4 varyingColor=Color4.ONE.copyNew();
	
	private BlendType blendType=BlendType.Normal;
	
	public BaseDrawableStoryboardSprite(MContext context){
		super(context);
	}

	public void setAlpha(float alpha) {
		this.alpha=alpha;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setBlendType(BlendType blendType) {
		this.blendType=blendType;
	}

	public BlendType getBlendType() {
		return blendType;
	}

	public void setVaryingColor(Color4 varyingColor) {
	this.varyingColor = varyingColor;
	}

	public Color4 getVaryingColor(){
		return varyingColor;
	}

	public void setTexture(AbstractTexture texture) {
		this.texture=texture;
	}

	public AbstractTexture getTexture() {
		return texture;
	}

	public void setAnchor(Anchor anchor) {
		this.anchor=anchor;
	}

	public Anchor getAnchor() {
		return anchor;
	}

	public void setCurrentPosition(Vec2 currentPosition) {
		this.currentPosition.set(currentPosition);
	}

	public Vec2 getCurrentPosition() {
		return currentPosition;
	}

	public void setRotation(float rotation) {
		this.rotation=rotation;
	}

	public float getRotation() {
		return rotation;
	}

	public void setScale(Vec2 scale) {
		this.scale.set(scale);
	}

	public Vec2 getScale() {
		return scale;
	}

	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		Quad quad=
			RectF.anchorOWH(
				anchor,
				currentPosition.x,
				currentPosition.y,
				texture.getWidth(),
				texture.getHeight()
			).toQuad();
		quad.rotate(anchor,rotation);
		
		//默认只按次流程绘制且只绘制StoryboardSprite，这里省去save/restore节省时间
		GLWrapped.blend.setBlendType(blendType);
		canvas.drawTexture(texture,quad,varyingColor,alpha);
	}
}
