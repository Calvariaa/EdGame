package com.edplan.framework.ui;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.BaseCanvas;
import com.edplan.framework.graphics.layer.BufferedLayer;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.graphics.opengl.GLPaint;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.ui.animation.interfaces.IHasAlpha;
import com.edplan.framework.ui.drawable.sprite.RoundedRectSprite;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.ui.drawable.sprite.RoundedShadowSprite;
import com.edplan.framework.ui.drawable.sprite.RoundedShadowShader;
import com.edplan.framework.graphics.opengl.BlendType;


/**
 *单独绘制一个FBO，然后再绘制到父View上，可以附加边缘效果
 */
public abstract class EdContainer extends EdAbstractViewGroup implements IHasAlpha
{
	private BufferedLayer layer;
	
	private BaseCanvas layerCanvas;
	
	private GLPaint postPaint;
	
	private LayerPoster layerPoster;
	
	public EdContainer(MContext c){
		super(c);
		layer=new BufferedLayer(c);
		layerCanvas=new GLCanvas2D(layer);
		postPaint=new GLPaint();
	}

	public void setLayerPoster(LayerPoster layerPoster){
		this.layerPoster=layerPoster;
	}

	public LayerPoster getLayerPoster(){
		return layerPoster;
	}
	
	public void setAlpha(float alpha){
		postPaint.setFinalAlpha(alpha);
	}
	
	public float getAlpha(){
		return postPaint.getFinalAlpha();
	}
	
	public void setAccentColor(Color4 c){
		postPaint.setMixColor(c);
	}
	
	public Color4 getAccentColor(){
		return postPaint.getMixColor();
	}
	
	public Vec2 getBufferSize(){
		return new Vec2(layer.getWidth(),layer.getHeight());
	}
	
	protected void updateLayerSize(BaseCanvas canvas){
		layer.setWidth((int)(getWidth()/canvas.getPixelDensity()));
		layer.setHeight((int)(getHeight()/canvas.getPixelDensity()));
	}
	
	protected void updateCanvas(BaseCanvas canvas){
		layerCanvas.reinitial();
		layerCanvas.scaleContent(canvas.getPixelDensity());
		layerCanvas.clip(getWidth(),getHeight());
	}
	
	protected void drawContainer(BaseCanvas canvas){
		super.dispatchDraw(canvas);
	}

	@Override
	protected void dispatchDraw(BaseCanvas canvas){
		// TODO: Implement this method
		updateLayerSize(canvas);
		updateCanvas(canvas);
		layerCanvas.prepare();
		layerCanvas.drawColor(Color4.Alpha);
		layerCanvas.clearBuffer();
		drawBackground(layerCanvas);
		drawContainer(layerCanvas);
		layerCanvas.unprepare();
		postLayer(canvas,layer,RectF.xywh(0,0,getWidth(),getHeight()),postPaint);
	}
	
	protected void postLayer(BaseCanvas canvas,BufferedLayer layer,RectF area,GLPaint paint){
		if(layerPoster!=null){
			layerPoster.postLayer(canvas,layer,area,paint);
			return;
		}
		canvas.drawTexture(layer.getTexture(),area,paint);
	}
	
	public RoundedLayerPoster setRounded(float radius){
		RoundedLayerPoster p=new RoundedLayerPoster(getContext());
		p.setRoundedRadius(radius);
		layerPoster=p;
		return p;
	}

	@Override
	public void onDraw(BaseCanvas canvas){
		// TODO: Implement this method
		dispatchDraw(canvas);
	}
	
	public interface LayerPoster{
		public void postLayer(BaseCanvas canvas,BufferedLayer layer,RectF area,GLPaint paint);
	}
	
	public static class RoundedLayerPoster implements LayerPoster
	{
		private RoundedRectSprite sprite;
		
		private RoundedShadowSprite shadow;
		
		private MContext context;
		
		public RoundedLayerPoster(MContext c){
			this.context=c;
			sprite=new RoundedRectSprite(c);
		}
		
		public void setRoundedRadius(float r){
			sprite.setRoundedRadius(r);
			if(shadow!=null)shadow.setRoundedRadius(r);
		}
		
		public void setShadow(float width,Color4 start,Color4 end){
			if(shadow==null){
				shadow=new RoundedShadowSprite(context);
				shadow.setRoundedRadius(sprite.getRoundedRadius());
			}
			shadow.setShadowWidth(width);
			shadow.setShadowColor(start,end);
			shadow.setBlendType(BlendType.Additive);
		}

		@Override
		public void postLayer(BaseCanvas canvas,BufferedLayer layer,RectF area,GLPaint paint){
			// TODO: Implement this method
			
			if(shadow!=null){
				shadow.setTexture(GLTexture.White);
				shadow.setAccentColor(paint.getMixColor());
				shadow.setAlpha(paint.getFinalAlpha());
				shadow.setArea(RectF.anchorOWH(Anchor.Center,area.getLeft()+area.getWidth()/2,area.getTop()+area.getHeight()/2,area.getWidth()+shadow.getShadowWidth()*2,area.getHeight()+shadow.getShadowWidth()*2));
				shadow.setRect(area);
				shadow.draw(canvas);
			}
			
			sprite.setTexture(layer.getTexture());
			sprite.setAccentColor(paint.getMixColor());
			sprite.setAlpha(paint.getFinalAlpha());
			sprite.setArea(area);
			sprite.setRect(area);
			sprite.draw(canvas);
		}
	}
}
