package com.edplan.framework.ui.drawable.sprite;
import com.edplan.framework.ui.drawable.EdDrawable;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.BaseCanvas;


/**
 *一个独立绘制的Sprite，绘制时只使用Canvas的Alpha和Camera，其余属性自定义
 */
public abstract class AbstractSprite extends EdDrawable
{
	public AbstractSprite(MContext c){
		super(c);
	}
	
	protected abstract void startDraw(BaseCanvas canvas);
	protected abstract void prepareShader(BaseCanvas canvas);
	protected abstract void loadVertexs(BaseCanvas canvas);
	protected abstract void postDraw(BaseCanvas canvas);
	protected abstract void endDraw(BaseCanvas canvas);
	
	
	@Override
	public void draw(BaseCanvas canvas) {
		// TODO: Implement this method
		startDraw(canvas);
		prepareShader(canvas);
		loadVertexs(canvas);
		postDraw(canvas);
		endDraw(canvas);
	}
}
