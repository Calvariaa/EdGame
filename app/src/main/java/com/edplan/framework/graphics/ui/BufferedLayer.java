package com.edplan.framework.graphics.ui;
import com.edplan.framework.graphics.opengl.bufferObjects.FrameBufferObject;
import com.edplan.framework.graphics.opengl.drawui.GLDrawable;
import com.edplan.framework.graphics.opengl.drawui.DrawInfo;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.MContext;

/**
 *通过FBO，完全分离的绘制，最后的结果是一个Texture
 */
public abstract class BufferedLayer
{
	private MContext context;
	
	private FrameBufferObject frameBuffer;
	
	private boolean hasDepthBuffer;

	private int width;
	
	private int height;
	
	public BufferedLayer(MContext contex,int width,int height,boolean hasDepthBuffer){
		this.context=contex;
		this.width=width;
		this.height=height;
		this.hasDepthBuffer=hasDepthBuffer;
	}
	
	public MContext getContext(){
		return context;
	}

	public void setFrameBuffer(FrameBufferObject frameBuffer) {
		this.frameBuffer=frameBuffer;
	}

	public FrameBufferObject getFrameBuffer() {
		return frameBuffer;
	}
	
	public boolean isBind(){
		return getFrameBuffer().isBind();
	}

	public void setWidth(int width) {
		this.width=width;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height=height;
	}

	public int getHeight() {
		return height;
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
	
	public void bind(){
		getFrameBuffer().bind();
	}
	
	public void unbind(){
		getFrameBuffer().unBind();
	}
	
	public abstract void renderThisLayer();
	
	//When rendering, the viewport should be sat by parent layer allready.
	
	public void render() {
		// TODO: Implement this method
		//make sure the width and height are right when drawing.
		checkChange();
		frameBuffer.bind();
		renderThisLayer();
		frameBuffer.unBind();
	}
}

