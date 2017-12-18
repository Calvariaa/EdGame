package com.edplan.framework.graphics.ui;
import com.edplan.framework.graphics.opengl.bufferObjects.FrameBufferObject;
import com.edplan.framework.graphics.opengl.drawui.GLDrawable;
import com.edplan.framework.graphics.opengl.drawui.DrawInfo;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.GLException;
import com.edplan.framework.graphics.opengl.GLWrapped;

/**
 *通过FBO，完全分离的绘制，最后的结果是一个Texture
 */
public class BufferedLayer
{
	private MContext context;
	
	private FrameBufferObject frameBuffer;
	
	private boolean hasDepthBuffer;

	private int width;
	
	private int height;
	
	public BufferedLayer(MContext context,int width,int height,boolean hasDepthBuffer){
		this.context=context;
		this.width=width;
		this.height=height;
		this.hasDepthBuffer=hasDepthBuffer;
	}
	
	public BufferedLayer(MContext context,FrameBufferObject fbo){
		this.context=context;
		this.frameBuffer=fbo;
		this.width=fbo.getWidth();
		this.height=fbo.getHeight();
		this.hasDepthBuffer=fbo.hasDepthAttachment();
	}
	
	public MContext getContext(){
		return context;
	}
	
	public void checkBind(String msg,boolean c){
		if(isBind()!=c){
			throw new GLException("err operation:you can't operate layer when it is bind. msg: "+msg);
		}
	}

	public void setFrameBuffer(FrameBufferObject frameBuffer) {
		checkBind("setFrameBuffer",false);
		this.frameBuffer=frameBuffer;
	}

	public FrameBufferObject getFrameBuffer() {
		return frameBuffer;
	}
	
	public boolean isBind(){
		return getFrameBuffer().isBind();
	}

	public void setWidth(int width) {
		checkBind("setWidth",false);
		this.width=width;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		checkBind("setHeight",false);
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
		checkChange();
		getFrameBuffer().bind();
		GLWrapped.setViewport(0,0,getWidth(),getHeight());
	}
	
	public void unbind(){
		getFrameBuffer().unBind();
	}
	
	//When rendering, the viewport should be sat by parent layer allready.
}

