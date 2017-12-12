package com.edplan.framework.graphics.opengl;
import android.opengl.GLES20;
import com.edplan.framework.graphics.opengl.batch.Texture3DBatch;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.graphics.opengl.shader.GLProgram;
import com.edplan.framework.graphics.opengl.shader.advance.Texture3DShader;
import com.edplan.framework.graphics.ui.BufferedLayer;
import com.edplan.framework.math.Mat4;
import java.util.Stack;
import com.edplan.framework.MContext;
import com.edplan.framework.math.RectF;

public class GLCanvas
{
	private BufferedLayer layer;
	
	protected Stack<CanvasData> savedDatas;
	
	protected int currentId=0;
	
	protected CanvasData data;
	
	private Mat4 mProjMatrix;
	
	public GLCanvas(BufferedLayer layer){
		this.layer=layer;
		mProjMatrix=new Mat4();
		mProjMatrix.setOrtho(0,layer.getWidth(),0,layer.getHeight(),1,100);
		savedDatas=new Stack<CanvasData>();
		data=new CanvasData();
		data.setDefault();
	}

	public void setMProjMatrix(Mat4 mProjMatrix) {
		this.mProjMatrix=mProjMatrix;
	}

	public Mat4 getMProjMatrix() {
		return mProjMatrix;
	}

	public int save(){
		savedDatas.push(data);
		data=new CanvasData(data);
		currentId++;
		return currentId-1;
	}
	
	public void restore(){
		data=savedDatas.pop();
		currentId--;
	}
	
	public void restoreToCount(int id){
		if(id>0&&id<=currentId){
			while(currentId>id){
				restore();
			}
		}else{
			throw new IllegalArgumentException("id should >0&&<=currentId");
		}
	}

	private CanvasData getData() {
		return data;
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
	
	public void post(){
		checkPrepared("when post canvas, canvas should be prepared",true);
		unprepare();
	}
	
	private void checkCanDraw(){
		checkPrepared(
			"canvas hasn't prepared for draw",
			true);	
	}
	
	public void setGLProgram(GLProgram p){
		checkPrepared("you can only change shader when canvas isn't prepared",false); 
		getData().setGlProgram(p);
	}
	
	public GLProgram getGLProgram(){
		return getData().getGlProgram();
	}
	
	public Mat4 getFinalMatrix(){
		return getMProjMatrix().copy().post(getData().getCurrentMatrix());
	}
	
	public void drawTexture3DBatch(Texture3DBatch batch,GLTexture texture,float alpha){
		checkCanDraw();
		if(getGLProgram() instanceof Texture3DShader){
			Texture3DShader shader=(Texture3DShader)getGLProgram();
			shader.useThis();
			shader.loadColorMixRate(batch.getColorMixRate());
			shader.loadAlpha(alpha);
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
	
	public void drawTexture(GLTexture texture,RectF res,RectF dst,float z){
		
	}
	
	public MContext getContext(){
		return getLayer().getContext();
	}
	
	public GLProgram getDefGLProgram(){
		return getContext().getShaderManager().getStdTexture3DShader();
	}
	
	private class CanvasData{
		
		private Mat4 currentMatrix;
		
		private GLProgram glProgram;
		
		public CanvasData(CanvasData c){
			this.currentMatrix=c.getCurrentMatrix();
			this.glProgram=c.getGlProgram();
		}
		
		public CanvasData(){
			
		}
		
		public void setDefault(){
			glProgram=getDefGLProgram();
			currentMatrix=new Mat4();
			currentMatrix.setIden();
		}
		
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
