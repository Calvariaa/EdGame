package com.edplan.framework.ui;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.BaseCanvas;
import com.edplan.framework.graphics.layer.BufferedLayer;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.graphics.opengl.GLPaint;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.graphics.opengl.objs.Color4;


/**
 *单独绘制一个FBO，然后再绘制到父View上，可以附加边缘效果
 */
public abstract class EdContainer extends EdAbstractViewGroup
{
	private BufferedLayer layer;
	
	private BaseCanvas layerCanvas;
	
	private GLPaint postPaint;
	
	public EdContainer(MContext c){
		super(c);
		layer=new BufferedLayer(c);
		layerCanvas=new GLCanvas2D(layer);
		postPaint=new GLPaint();
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
		drawContainer(layerCanvas);
		layerCanvas.unprepare();
		canvas.drawTexture(layer.getTexture(),RectF.xywh(0,0,getWidth(),getHeight()),postPaint);
	}
}
