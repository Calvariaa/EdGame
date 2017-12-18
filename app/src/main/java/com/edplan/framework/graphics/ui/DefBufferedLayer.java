package com.edplan.framework.graphics.ui;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.bufferObjects.FrameBufferObject;
import com.edplan.framework.graphics.opengl.GLException;

public class DefBufferedLayer extends BufferedLayer
{
	public DefBufferedLayer(MContext con,int width,int height){
		super(con,new FrameBufferObject.SystemFrameBuffer(width,height));
	}
	
	private void err(){
		throw new GLException("you can't change sys_bufferedLayer");
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
}
