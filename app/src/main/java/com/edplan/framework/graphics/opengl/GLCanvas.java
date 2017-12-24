package com.edplan.framework.graphics.opengl;
import android.opengl.GLES20;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.batch.Texture3DBatch;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.graphics.opengl.objs.TextureVertex3D;
import com.edplan.framework.graphics.opengl.shader.GLProgram;
import com.edplan.framework.graphics.opengl.shader.advance.Texture3DShader;
import com.edplan.framework.graphics.ui.BufferedLayer;
import com.edplan.framework.math.Mat4;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.Vec3;
import java.util.Stack;
import com.edplan.framework.utils.MLog;
import com.edplan.opengl.es20.MatrixState;

public class GLCanvas
{
	private BufferedLayer layer;
	
	protected Stack<CanvasData> savedDatas;
	
	protected int currentId=0;
	
	protected CanvasData data;
	
	private Mat4 mProjMatrix;
	
	private Texture3DBatch tmpBatch=new Texture3DBatch();
	
	public GLCanvas(BufferedLayer layer){
		this.layer=layer;
		mProjMatrix=new Mat4();
		mProjMatrix.setOrtho(0,layer.getWidth(),0,layer.getHeight(),-100,100);
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
	
	/**
	 *@param p:当前应该是的状态
	 */
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
	
	public void delete(){
		checkPrepared("you delete a prepared canvas!",false);
		savedDatas.clear();
	}
	
//	private void checkGLProgram(Class<? extends GLProgram> c){
//		if(!c.isInstance(getGLProgram())){
//			
//		}
//	}
	
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
	
	public void drawColor(Color4 color){
		GLWrapped.setClearColor(color.r,color.g,color.b,color.a);
		GLWrapped.clearColorBuffer();
	}
	
	public void clearDepthBuffer(){
		GLWrapped.clearDepthBuffer();
	}
	
	public void clearBuffer(){
		GLWrapped.clearDepthAndColorBuffer();
	}
	
	public void drawTexture3DBatch(Texture3DBatch batch,GLTexture texture,float alpha,Color4 mixColor){
		checkCanDraw();
		if(getGLProgram() instanceof Texture3DShader){
			Texture3DShader shader=(Texture3DShader)getGLProgram();
			shader.useThis();
			shader.loadMixColor(mixColor);
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
	
	/**
	 *@param res:此处为Texture范围，使用实际像素坐标（原点左上）
	 *@param dst:绘制在canvas上的坐标，也是实际像素坐标（原点左下）
	 */
	public void drawTexture(GLTexture texture,RectF res,RectF dst,Color4 mixColor,Color4 color,float colorMixRate,float z,float alpha){
		checkCanDraw();
		tmpBatch.clear();
		tmpBatch.setColorMixRate(colorMixRate);
		//  3          2
		//   ┌────┐
		//   └────┘
		//  0          1
		TextureVertex3D v0=
			TextureVertex3D
				 .atPosition(new Vec3(dst.getPoint(0,0),z))
				 .setColor(color)
				 .setTexturePoint(texture.toTexturePosition(res.getX1(),res.getY2()));
		TextureVertex3D v1=
			TextureVertex3D
				 .atPosition(new Vec3(dst.getPoint(1,0),z))
				 .setColor(color)
				 .setTexturePoint(texture.toTexturePosition(res.getX2(),res.getY2()));
		TextureVertex3D v2=
			TextureVertex3D
				 .atPosition(new Vec3(dst.getPoint(1,1),z))
				 .setColor(color)
				 .setTexturePoint(texture.toTexturePosition(res.getX2(),res.getY1()));
		TextureVertex3D v3=
			TextureVertex3D
				 .atPosition(new Vec3(dst.getPoint(0,1),z))
				 .setColor(color)
				 .setTexturePoint(texture.toTexturePosition(res.getX1(),res.getY1()));
		tmpBatch.add(v0,v1,v2,v0,v2,v3);
		drawTexture3DBatch(tmpBatch,texture,alpha,mixColor);
		MLog.test.vOnce("vdata1","gl_test","v2: "+(new Vec3(dst.getPoint(1,1),z)).toString()+" t: "+texture.toTexturePosition(res.getX2(),res.getY1()).toString());
		MLog.test.vOnce("vdata2","gl_test","v1: "+(new Vec3(dst.getPoint(1,0),z)).toString()+" t: "+texture.toTexturePosition(res.getX2(),res.getY2()).toString());
	}
	
	public void drawTexture(GLTexture texture,float x,float y){
		
	}
	
	public MContext getContext(){
		return getLayer().getContext();
	}
	
	public GLProgram getDefGLProgram(){
		return getContext().getShaderManager().getStdTexture3DShader();
	}
	
	public void recycle(){
		savedDatas.clear();
		tmpBatch.clear();
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
