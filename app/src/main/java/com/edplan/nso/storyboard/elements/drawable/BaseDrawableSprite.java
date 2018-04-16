package com.edplan.nso.storyboard.elements.drawable;
import com.edplan.framework.graphics.opengl.BlendType;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.graphics.opengl.GLWrapped;
import com.edplan.framework.graphics.opengl.objs.AbstractTexture;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.math.Quad;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.ui.Anchor;
import com.edplan.framework.ui.animation.QueryAnimation;
import com.edplan.nso.storyboard.PlayingStoryboard;
import com.edplan.nso.storyboard.elements.StoryboardSprite;
import java.util.ArrayList;
import java.util.List;
import com.edplan.framework.interfaces.InvokeSetter;
import com.edplan.framework.utils.MLog;
import com.edplan.framework.graphics.opengl.GLPaint;
import java.util.HashMap;
import com.edplan.framework.graphics.opengl.GL10Canvas2D;

public class BaseDrawableSprite extends ADrawableStoryboardElement
{
	private AbstractTexture texture;
	
	private Anchor anchor=Anchor.Center;
	
	private Vec2 currentPosition=new Vec2();
	public static InvokeSetter<BaseDrawableSprite,Float> X=new InvokeSetter<BaseDrawableSprite,Float>(){
		@Override
		public void invoke(BaseDrawableSprite target,Float value) {
			// TODO: Implement this method
			target.currentPosition.x=value;
		}
	};
	public static InvokeSetter<BaseDrawableSprite,Float> Y=new InvokeSetter<BaseDrawableSprite,Float>(){
		@Override
		public void invoke(BaseDrawableSprite target,Float value) {
			// TODO: Implement this method
			target.currentPosition.y=value;
		}
	};
	
	private float rotation=0;
	public static InvokeSetter<BaseDrawableSprite,Float> Rotation=new InvokeSetter<BaseDrawableSprite,Float>(){
		@Override
		public void invoke(BaseDrawableSprite target,Float value) {
			// TODO: Implement this method
			target.rotation=value;
		}
	};
	
	private Vec2 scale=new Vec2(1,1);
	public static InvokeSetter<BaseDrawableSprite,Vec2> Scale=new InvokeSetter<BaseDrawableSprite,Vec2>(){
		@Override
		public void invoke(BaseDrawableSprite target,Vec2 value) {
			// TODO: Implement this method
			target.scale.set(value);
		}
	};
	
	private boolean flipH=false;
	public static InvokeSetter<BaseDrawableSprite,Boolean> FlipH=new InvokeSetter<BaseDrawableSprite,Boolean>(){
		@Override
		public void invoke(BaseDrawableSprite target,Boolean value) {
			// TODO: Implement this method
			target.flipH=value;
		}
	};
	
	private boolean flipV=false;
	public static InvokeSetter<BaseDrawableSprite,Boolean> FlipV=new InvokeSetter<BaseDrawableSprite,Boolean>(){
		@Override
		public void invoke(BaseDrawableSprite target,Boolean value) {
			// TODO: Implement this method
			target.flipV=value;
		}
	};
	
	private float alpha=1;
	public static InvokeSetter<BaseDrawableSprite,Float> Alpha=new InvokeSetter<BaseDrawableSprite,Float>(){
		@Override
		public void invoke(BaseDrawableSprite target,Float value) {
			// TODO: Implement this method
			target.alpha=value;
		}
	};
	
	private Color4 varyingColor=Color4.ONE.copyNew();
	public static InvokeSetter<BaseDrawableSprite,Color4> Color=new InvokeSetter<BaseDrawableSprite,Color4>(){
		@Override
		public void invoke(BaseDrawableSprite target,Color4 value) {
			// TODO: Implement this method
			target.varyingColor.set(value);
		}
	};
	
	private BlendType blendType=BlendType.Normal;
	public static InvokeSetter<BaseDrawableSprite,BlendType> Blend=new InvokeSetter<BaseDrawableSprite,BlendType>(){
		@Override
		public void invoke(BaseDrawableSprite target,BlendType value) {
			// TODO: Implement this method
			target.blendType=value;
		}
	};
	
	private double startTime;
	
	private double endTime;
	
	private String path;
	
	private List<QueryAnimation> animations=new ArrayList<QueryAnimation>();
	
	public BaseDrawableSprite(PlayingStoryboard storyboard,StoryboardSprite sprite){
		super(storyboard);
		path=sprite.getPath();
		startTime=sprite.getStartTime();
		endTime=sprite.getEndTime();
		//MLog.test.vOnce("sprite-data",
		//System.out.println("sprite-data: "+startTime+","+endTime);
		anchor=sprite.getAnchor();
		currentPosition.set(sprite.getInitialPosition());
	}
	
	public List<QueryAnimation> getAnimations(){
		return animations;
	}
	
	public void addAnimation(QueryAnimation anim){
		animations.add(anim);
	}

	@Override
	public double getStartTime() {
		// TODO: Implement this method
		return startTime;
	}

	@Override
	public double getEndTime() {
		// TODO: Implement this method
		return endTime;
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

	
	boolean hasAdd=false;
	@Override
	public void onAdd() {
		// TODO: Implement this method
		hasAdd=true;
		for(QueryAnimation anim:animations){
			anim.post(getTimeline());
		}
		animations.clear();
	}

	@Override
	public boolean hasAdd() {
		// TODO: Implement this method
		return hasAdd;
	}

	@Override
	public void onRemove() {
		// TODO: Implement this method
	}

	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		if(alpha<0.002)return;
		
		Quad quad=
			RectF.anchorOWH(
				anchor,
				currentPosition.x,
				currentPosition.y,
				texture.getWidth(),
				texture.getHeight()
			).scale(anchor,scale.x,scale.y)
			.toQuad();
		quad.rotate(anchor,rotation);
		quad.flip(flipH,flipV);
		//默认只按次流程绘制且只绘制StoryboardSprite，这里省去save/restore节省时间
		GLWrapped.blend.setBlendType(blendType);
		canvas.drawTexture(texture,quad,varyingColor,alpha);
	}

	@Override
	public void drawGL10(GL10Canvas2D canvas) {
		// TODO: Implement this method
		if(alpha<0.002)return;

		Quad quad=
			RectF.anchorOWH(
			anchor,
			currentPosition.x,
			currentPosition.y,
			texture.getWidth(),
			texture.getHeight()
		).scale(anchor,scale.x,scale.y)
			.toQuad();
		quad.rotate(anchor,rotation);
		quad.flip(flipH,flipV);
		//默认只按次流程绘制且只绘制StoryboardSprite，这里省去save/restore节省时间
		GLWrapped.blend.setBlendType(blendType);
		canvas.drawTexture(texture,quad,varyingColor,alpha);
	}
	
}
