package com.edplan.framework.graphics.opengl;
import com.edplan.framework.graphics.ui.BufferedLayer;
import com.edplan.framework.math.Mat4;
import com.edplan.framework.graphics.opengl.shader.GLShader;
import com.edplan.framework.graphics.opengl.shader.GLProgram;
import com.edplan.framework.graphics.opengl.objs.Color4;

public class GLCanvas
{
	private BufferedLayer layer;
	
	private CanvasData data;
	
	private Mat4 mProjMatrix;
	
	private Mat4 mVMatrix;
	
	private boolean prepared;
	
	public GLCanvas(BufferedLayer layer){
		this.layer=layer;
		mProjMatrix=new Mat4();
		mProjMatrix.setOrtho(0,layer.getWidth(),0,layer.getHeight(),1,100);
		prepared=false;
		mVMatrix=new Mat4();
		mVMatrix.setIden();
	}
	
	public boolean isPrepared(){
		return prepared;
	}
	
	public void checkPrepared(String msg,boolean p){
		if(p!=prepared){
			throw new GLException("prepare err [n,c]=["+p+","+prepared+"] msg: "+msg);
		}
	}
	
	public void prepare(){
		checkPrepared(
			"you can't call prepare when GLCanvas is prepared",
			false);
		prepared=true;
	}
	
	public void unprepare(){
		checkPrepared(
			"you can't call unprepare when GLCanvas isn't prepared",
			true);
		prepared=false;
	}
	
	
	
	private class CanvasData{
		
		private Mat4 currentMatrix;
		
		private GLProgram glProgram;
		
		private Color4 mixColor;
		
		private float colorMixRate;
		
		
	}
	
}
