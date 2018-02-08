package com.edplan.framework.graphics.layer;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.bufferObjects.FrameBufferObject;
import com.edplan.framework.graphics.opengl.GLException;
import com.edplan.framework.graphics.opengl.objs.AbstractTexture;

public class DefBufferedLayer extends BufferedLayer
{
	public DefBufferedLayer(MContext con,int width,int height){
		super(con,new FrameBufferObject.SystemFrameBuffer(width,height));
	}
	
	public void prepare(){
		bind();
		unbind();
	}

	@Override
	public void reCreateBuffer() {
		// TODO: Implement this method
		//you can't recreate SystemBuffer
		return;
	}
	
	private void err(){
		throw new GLException("err operation on sys_bufferedLayer");
	}

	@Override
	public AbstractTexture getTexture() {
		// TODO: Implement this method
		return super.getTexture();
	}

	@Override
	public FrameBufferObject getFrameBuffer() {
		// TODO: Implement this method
		return super.getFrameBuffer();
	}
	

	@Override
	public void setHeight(int height) {
		// TODO: Implement this method
		err();
		super.setHeight(height);
	}

	@Override
	public void setFrameBuffer(FrameBufferObject frameBuffer) {
		// TODO: Implement this method
		err();
		super.setFrameBuffer(frameBuffer);
	}

	@Override
	public void setWidth(int width) {
		// TODO: Implement this method
		err();
		super.setWidth(width);
	}

	@Override
	public void recycle() {
		// TODO: Implement this method
		//recycle a SystemBuffer will happen no thing
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO: Implement this method
		super.finalize();
	}
}
