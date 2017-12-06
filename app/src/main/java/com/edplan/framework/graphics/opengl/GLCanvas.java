package com.edplan.framework.graphics.opengl;
import com.edplan.framework.graphics.ui.BufferedLayer;
import com.edplan.framework.math.Mat4;

/**
 *This class used to draw objs on a layer
 */
public class GLCanvas
{
	private BufferedLayer layer;
	
	private CanvasData data;
	
	private Mat4 mProjMatrix;
	
	private Mat4 mVMatrix;
	
	public GLCanvas(BufferedLayer layer){
		this.layer=layer;
		mProjMatrix=new Mat4();
		mProjMatrix.setOrtho(0,layer.getWidth(),0,layer.getHeight(),1,100);
		mVMatrix=new Mat4();
		mVMatrix.setIden();
	}
	
	
	private class CanvasData{
		
	}
	
}
