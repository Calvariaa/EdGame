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
import com.edplan.framework.timing.PreciseTimeline;
import com.edplan.nso.storyboard.elements.StoryboardSprite;
import com.edplan.nso.storyboard.Storyboard;
import com.edplan.nso.storyboard.PlayingStoryboard;

public class BaseDrawableStoryboardSprite extends EdDrawable
{
	private AbstractTexture texture;
	
	private Anchor anchor=Anchor.Center;
	
	private Vec2 currentPosition=new Vec2();
	
	private float rotation=0;
	
	private Vec2 scale=new Vec2(1,1);
	
	private boolean flipH=false;
	
	private boolean flipV=false;
	
	private float alpha=1;
	
	private Color4 varyingColor=Color4.ONE.copyNew();
	
	private BlendType blendType=BlendType.Normal;
	
	private PreciseTimeline timeline;
	
	private PlayingStoryboard storyboard;
	
	private double startTime;
	
	private double endTime;
	
	public BaseDrawableStoryboardSprite(MContext context,PlayingStoryboard storyboard,StoryboardSprite sprite,PreciseTimeline timeline){
		super(context);
		this.timeline=timeline;
		this.storyboard=storyboard;
		startTime=sprite.getStartTime();
		endTime=sprite.getEndTime();
	}

	public void setTimeline(PreciseTimeline timeline) {
		this.timeline=timeline;
	}

	public PreciseTimeline getTimeline() {
		return timeline;
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
			).scale(anchor,scale.x*(flipH?-1:1),scale.y*(flipV?-1:1))
			.toQuad();
		quad.rotate(anchor,rotation);
		
		//默认只按次流程绘制且只绘制StoryboardSprite，这里省去save/restore节省时间
		GLWrapped.blend.setBlendType(blendType);
		canvas.drawTexture(texture,quad,varyingColor,alpha);
	}
	
	
}
