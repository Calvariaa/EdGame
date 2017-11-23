package com.edplan.framework.graphics.ui;
import com.edplan.framework.graphics.opengl.bufferObjects.FrameBufferObject;
import com.edplan.framework.graphics.opengl.drawui.GLDrawable;
import com.edplan.framework.graphics.opengl.drawui.DrawInfo;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.GLTexture;

/**
 *通过FBO，完全分离的绘制，最后的结果是一个Texture
 */
public abstract class BufferedLayer extends GLDrawable
{
	private FrameBufferObject frameBuffer;
	
	private boolean hasDepthBuffer;

	private int width;
	
	private int height;
	
	private Color4 clearColor;
	
	public BufferedLayer(int width,int height,boolean hasDepthBuffer){
		this.width=width;
		this.height=height;
		this.hasDepthBuffer=hasDepthBuffer;
		clearColor=new Color4(0,0,0,0);
	}
	
	private void reCreateBuffer(){
		if(frameBuffer!=null)frameBuffer.deleteWithAttachment();
		frameBuffer=FrameBufferObject.create(width,height,hasDepthBuffer);
	}
	
	private void checkChange(){
		if(frameBuffer==null||frameBuffer.getWidth()!=width||frameBuffer.getHeight()!=height||(hasDepthBuffer!=frameBuffer.hasDepthAttachment())){
			reCreateBuffer();
		}
	}
	
	public GLTexture getTexture(){
		return frameBuffer.getColorAttachment();
	}
	
	public abstract void renderThisLayer();
	
	@Override
	public void prepareForDraw() {
		// TODO: Implement this method
		//make sure the width and height are right when drawing.
		checkChange();
	}

	//When rendering, the viewport should be sat by parent layer allready.
	@Override
	public void render(DrawInfo info) {
		// TODO: Implement this method
		frameBuffer.bind();
		renderThisLayer();
		frameBuffer.unBind();
	}
}

