package com.edplan.framework.graphics.opengl.bufferObjects;
import android.opengl.GLES20;
import com.edplan.framework.graphics.opengl.GLException;
import com.edplan.framework.graphics.opengl.objs.GLTexture;

public class FrameBufferObject
{
	
	private DepthBufferObject depthAttachment;
	
	private GLTexture colorAttachment;
	
	private int frameBufferId;
	
	private int width;
	
	private int height;
	
	FrameBufferObject(){
		
	}
	
	FrameBufferObject(int width,int height){
		this.width=width;
		this.height=height;
	}

	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	private void setWidth(int w){
		width=w;
	}
	
	private void setHeight(int h){
		height=h;
	}
	
	public void clearDepthBuffer(){
		checkCurrent();
		if(hasDepthAttachment()){
			getDepthAttachment().clear();
		}
	}
	
	private void setDepthAttachment(DepthBufferObject depthAttachment) {
		this.depthAttachment=depthAttachment;
	}

	public DepthBufferObject getDepthAttachment() {
		return depthAttachment;
	}
	
	public boolean hasDepthAttachment(){
		return getDepthAttachment()!=null;
	}

	private void setColorAttachment(GLTexture colorAttachment) {
		this.colorAttachment=colorAttachment;
	}

	public GLTexture getColorAttachment() {
		return colorAttachment;
	}
	
	public boolean hasColorAttachment(){
		return getColorAttachment()!=null;
	}
	
	public int getFBOId(){
		return frameBufferId;
	}
	
	private void linkColorAttachment(GLTexture texture){
		checkCurrent();
		if(colorAttachment!=null){
			throw new GLException("A FrameBufferObject can't attach two Texture...Maybe support future:(");
		}else{
			setColorAttachment(texture);
			GLES20.glFramebufferTexture2D(
				GLES20.GL_FRAMEBUFFER,
				GLES20.GL_COLOR_ATTACHMENT0,
				GLES20.GL_TEXTURE_2D,
				texture.getTextureId(),
				0
			);
		}
	}
	
	private void linkDepthBuffer(DepthBufferObject dbo){
		checkCurrent();
		if(depthAttachment!=null){
			throw new GLException("A FrameBufferObject can't attach two DepthBuffer");
		}else{
			setDepthAttachment(dbo);
			GLES20.glBindRenderbuffer(
				GLES20.GL_RENDERBUFFER,
				depthAttachment.getBufferId());
		}
	}
	
	public void delete(){
		GLES20.glDeleteFramebuffers(1,new int[]{getFBOId()},0);
	}
	
	public void deleteWithAttachment(){
		if(hasColorAttachment()){
			getColorAttachment().delete();
		}
		if(hasDepthAttachment()){
			getDepthAttachment().delete();
		}
		this.delete();
	}
	
	public static FrameBufferObject create(int width,int height,boolean useDepth){
		FrameBufferObject fbo=createFBO();
		fbo.setWidth(width);
		fbo.setHeight(height);
		fbo.bind();
			fbo.linkColorAttachment(GLTexture.createGPUTexture(width,height));
			if(useDepth){
				fbo.linkDepthBuffer(DepthBufferObject.create(width,height));
			}
		fbo.unBind();
		return fbo;
	}
	
	private static FrameBufferObject createFBO(){
		FrameBufferObject fbo=new FrameBufferObject();
		fbo.frameBufferId=genOne();
		return fbo;
	}
	
	
	private static int genOne(){
		int[] i=new int[1];
		GLES20.glGenFramebuffers(1,i,0);
		return i[0];
	}
	
	//-----for-bindable---
	
	private FrameBufferObject previousFBO;
	
	protected boolean binded=false;

	public boolean isBind(){
		return binded;
	}

	private void setBind(boolean b){
		binded=b;
	}
	
	public void bind(){
		if(isBind()){
			throw new GLException("You bind a binded obj!! Maybe you forget to call this.unbind() ");
		}else{
			if(currentFBO()==this){
				throw new GLException("You bind a binded obj!! Maybe you forget to call this.unbind() ");
			}else{
				setBind(true);
				previousFBO=currentFBO();
				setCurrentFBO(this);
				if(hasDepthAttachment()){
					getDepthAttachment().bind();
				}
			}
		}
	}

	public void unBind(){
		if(isBind()){
			setBind(false);
			checkCurrent();
			setCurrentFBO(previousFBO);
			previousFBO=null;
			if(hasDepthAttachment()){
				getDepthAttachment().unBind();
			}
		}else{
			throw new GLException("You unbind a unbinded obj!! Maybe you forget to call this.bind() ");
		}
		
	}
	
	private void checkCurrent(){
		if(currentFBO()!=this){
			throw new GLException(
				"Current FrameBufferObject isn't this!! Please operate FrameBufferObject between bind() and unbind().Or another fbo hasn't call unbind()");
		}
	}
	
	public static void bindInitial(SystemFrameBuffer sysFrameBuffer){
		CURRENT_FRAMEBUFFER=sysFrameBuffer;
	}
	
	public static FrameBufferObject CURRENT_FRAMEBUFFER;
	
	public static FrameBufferObject currentFBO(){
		return CURRENT_FRAMEBUFFER;
	}
	
	private static void setCurrentFBO(FrameBufferObject fbo){
		CURRENT_FRAMEBUFFER=fbo;
		bind(fbo.getFBOId());
	}
	
	private static void bind(int id){
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,id);
	}
	
	public static class SystemFrameBuffer extends FrameBufferObject {
		
		private static final int SYSTEM_FRAMEBUFFER_ID=0;
		
		public SystemFrameBuffer(int width,int height){
			super(width,height);
		}
		
		@Override
		public boolean hasDepthAttachment(){
			// TODO: Implement this method
			return true;
		}
		
		@Override
		public int getFBOId() {
			// TODO: Implement this method
			return SYSTEM_FRAMEBUFFER_ID;
		}
		
		@Override
		public void bind() {
			// TODO: Implement this method
			binded=true;
			bindInitial(this);
			FrameBufferObject.bind(SYSTEM_FRAMEBUFFER_ID);
		}

		@Override
		public void unBind() {
			// TODO: Implement this method
			//解绑根FBO不会进行任何操作
			this.binded=false;
		}

		@Override
		public void delete() {
			// TODO: Implement this method
			//super.delete();
		}
	}
}
