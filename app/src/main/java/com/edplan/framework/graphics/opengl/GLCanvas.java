package com.edplan.framework.graphics.opengl;
import android.opengl.GLES20;
import com.edplan.framework.graphics.opengl.batch.Texture3DBatch;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.graphics.opengl.shader.GLProgram;
import com.edplan.framework.graphics.opengl.shader.advance.Texture3DShader;
import com.edplan.framework.graphics.ui.BufferedLayer;
import com.edplan.framework.math.Mat4;

public class GLCanvas
{
	private BufferedLayer layer;
	
	private CanvasData data;
	
	private Mat4 mProjMatrix;
	
	private Mat4 mVMatrix;
	
	//private boolean prepared;
	
	public GLCanvas(BufferedLayer layer){
		this.layer=layer;
		mProjMatrix=new Mat4();
		mProjMatrix.setOrtho(0,layer.getWidth(),0,layer.getHeight(),1,100);
		//prepared=false;
		mVMatrix=new Mat4();
		mVMatrix.setIden();
	}

	public void setMProjMatrix(Mat4 mProjMatrix) {
		this.mProjMatrix=mProjMatrix;
	}

	public Mat4 getMProjMatrix() {
		return mProjMatrix;
	}

	private void setData(CanvasData data) {
		this.data=data;
	}

	private CanvasData getData() {
		return data;
	}

	public void setLayer(BufferedLayer layer) {
		this.layer=layer;
	}

	public BufferedLayer getLayer() {
		return layer;
	}
	
	public boolean isPrepared(){
		return getLayer().isBind();
	}
	
	public void checkPrepared(String msg,boolean p){
		if(p!=isPrepared()){
			throw new GLException("prepare err [n,c]=["+p+","+isPrepared()+"] msg: "+msg);
		}
	}
	
	public void prepare(){
		checkPrepared(
			"you can't call prepare when GLCanvas is prepared",
			false);
		getLayer().bind();
	}
	
	public void unprepare(){
		checkPrepared(
			"you can't call unprepare when GLCanvas isn't prepared",
			true);
		getLayer().unbind();
	}
	
	private void checkCanDraw(){
		checkPrepared(
			"canvas hasn't prepared for draw",
			true);	
	}
	
	public GLProgram getGLProgram(){
		return getData().getGlProgram();
	}
	
	public Mat4 getFinalMatrix(){
		return getMProjMatrix().copy().post(getData().getCurrentMatrix());
	}
	
	
	
	public void drawTexture3DBatch(Texture3DBatch batch,GLTexture texture){
		checkCanDraw();
		if(getGLProgram() instanceof Texture3DShader){
			Texture3DShader shader=(Texture3DShader)getGLProgram();
			shader.useThis();
			shader.loadColorMixRate(batch.getColorMixRate());
			shader.loadMVPMatrix(getFinalMatrix());
			shader.loadTexture(texture);
			shader.loadPosition(batch.makePositionBuffer());
			shader.loadColor(batch.makeColorBuffer());
			shader.loadTexturePosition(batch.makeTexturePositionBuffer());
			GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, batch.getVertexCount());
		}else{
			throw new GLException("when calling drawTexture3DBatch, getGLProgram() should be a Texture3DShader");
		}
	}
	
	private class CanvasData{
		
		private Mat4 currentMatrix;
		
		private GLProgram glProgram;
		
		public void setCurrentMatrix(Mat4 currentMatrix) {
			this.currentMatrix=currentMatrix;
		}

		public Mat4 getCurrentMatrix() {
			return currentMatrix;
		}

		public void setGlProgram(GLProgram glProgram) {
			this.glProgram=glProgram;
		}

		public GLProgram getGlProgram() {
			return glProgram;
		}

	}
	
}
