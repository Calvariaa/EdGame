package com.edplan.framework.ui.drawable;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.graphics.layer.BufferedLayer;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.objs.GLTexture;

/**
 *将Canvas上的一块用BufferedLayer包装，再绘制到canvas上去
 */
public class BufferedDrawable extends EdDrawable
{
	private BufferedLayer bufferedLayer;

	private Vec2 area=new Vec2();
	
	private EdDrawable content;
	
	public BufferedDrawable(MContext c,float w,float h){
		super(c);
		area.set(w,h);
	}

	public void setContent(EdDrawable content) {
		this.content=content;
	}

	public EdDrawable getContent() {
		return content;
	}
	
	private Vec2 calBufferSize(GLCanvas2D canvas){
		float x=((area.x==0)?canvas.getWidth():area.x)*canvas.getPixelDensity();
		float y=((area.y==0)?canvas.getHeight():area.y)*canvas.getPixelDensity();
		return new Vec2(x,y);
	}
	
	/**
	 *默认操作只同步pixelDensity
	 */
	protected void transformCanvas(GLCanvas2D nowCanvas,GLCanvas2D parentCanvas){
		nowCanvas.scaleContent(parentCanvas.getPixelDensity());
	}
	
	/**
	 *如何将内容绘制到父Canvas上去
	 */
	protected void postToParent(GLTexture texture,GLCanvas2D canvas){
		
	}
	
	/**
	 *如何绘制内容
	 */
	protected void drawContent(GLCanvas2D canvas){
		getContent().draw(canvas);
	}

	@Override
	public void draw(GLCanvas2D canvas) {
		// TODO: Implement this method
		Vec2 size=calBufferSize(canvas);
		if(bufferedLayer==null){
			bufferedLayer=new BufferedLayer(getContext(),(int)size.x,(int)size.y,true);
		}
		bufferedLayer.setWidth((int)size.x);
		bufferedLayer.setHeight((int)size.y);
		GLCanvas2D ncanvas=new GLCanvas2D(bufferedLayer);
		ncanvas.prepare();
		transformCanvas(ncanvas,canvas);
		drawContent(ncanvas);
		ncanvas.unprepare();
		postToParent(bufferedLayer.getTexture(),canvas);
	}
}
