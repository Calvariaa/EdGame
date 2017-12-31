package com.edplan.framework.graphics.opengl;
import android.opengl.GLES20;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.opengl.batch.RectVertexBatch;
import com.edplan.framework.graphics.opengl.batch.Texture3DBatch;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.graphics.opengl.objs.TextureVertex3D;
import com.edplan.framework.graphics.opengl.objs.texture.TextureRegion;
import com.edplan.framework.graphics.opengl.objs.vertex.RectVertex;
import com.edplan.framework.graphics.opengl.shader.advance.RectShader;
import com.edplan.framework.graphics.opengl.shader.advance.RoundedRectShader;
import com.edplan.framework.graphics.opengl.shader.advance.Texture3DShader;
import com.edplan.framework.graphics.ui.BufferedLayer;
import com.edplan.framework.math.Mat4;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.Vec3;
import com.edplan.framework.utils.AbstractSRable;

public class GLCanvas2D extends AbstractSRable<CanvasData>
{
	private BufferedLayer layer;

	private float defZ=0;
	
	private Texture3DBatch<TextureVertex3D> tmpBatch=new Texture3DBatch<TextureVertex3D>();
	
	private RectVertexBatch<RectVertex> tmpRectBatch=new RectVertexBatch<RectVertex>();
	
	public GLCanvas2D(BufferedLayer layer){
		this.layer=layer;
		initial();
	}
	
	public void setMProjMatrix(Mat4 mProjMatrix) {
		this.getData().setCurrentProjMatrix(mProjMatrix);
	}

	public Mat4 getMProjMatrix() {
		return getData().getCurrentProjMatrix();
	}
	
	public Mat4 getMaskMatrix(){
		return getData().getCurrentMaskMatrix();
	}

	@Override
	public void onSave(CanvasData t) {
		// TODO: Implement this method
	}

	@Override
	public void onRestore(CanvasData now,CanvasData pre) {
		// TODO: Implement this method
		pre.recycle();
	}

	@Override
	public CanvasData getDefData() {
		// TODO: Implement this method
		CanvasData d=new CanvasData();
		d.setTexture3DShader(getDefTexture3DShader());
		d.setCurrentProjMatrix(createDefProjMatrix());
		d.setCurrentMaskMatrix(Mat4.createIdentity());
		return d;
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
		recycle();
	}
	
	public Mat4 getFinalMatrix(){
		return getMProjMatrix().copy().pre(getMaskMatrix());
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
	
	private void injectData(Texture3DBatch batch,GLTexture texture,float alpha,Color4 mixColor,Texture3DShader shader){
		shader.useThis();
		shader.loadMixColor(mixColor);
		shader.loadColorMixRate(batch.getColorMixRate());
		shader.loadAlpha(alpha);
		shader.loadMVPMatrix(getFinalMatrix());
		shader.loadMaskMatrix(getMaskMatrix());
		shader.loadTexture(texture);
		shader.loadPosition(batch.makePositionBuffer());
		shader.loadColor(batch.makeColorBuffer());
		shader.loadTexturePosition(batch.makeTexturePositionBuffer());
	}
	
	public void injectRectData(RectVertexBatch batch,RectF drawingRect,float padding,RectShader shader){
		shader.useThis();
		shader.loadRectData(drawingRect,padding);
		shader.loadRectPositions(batch.makeRectPositionBuffer());
	}
	
	public void injectRoundedRectData(RectVertexBatch batch,RectF drawingRect,float padding,float radius,Color4 glowColor,float glowFactor,RoundedRectShader shader){
		shader.useThis();
		shader.loadRectData(drawingRect,padding,radius,glowColor,glowFactor);
		shader.loadRectPositions(batch.makeRectPositionBuffer());
	}
	
	public void drawTexture3DBatch(Texture3DBatch batch,GLTexture texture,float alpha,Color4 mixColor){
		checkCanDraw();
		injectData(batch,texture,alpha,mixColor,getData().getTexture3DShader());
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, batch.getVertexCount());
	}
	
	public void drawTexture3DBatch(Texture3DBatch batch,TextureRegion texture,float alpha,Color4 mixColor){
		checkCanDraw();
		injectData(batch,texture.getTexture(),alpha,mixColor,getData().getTexture3DShader());
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, batch.getVertexCount());
	}
	
	private RectVertex[] createRectVertexs(GLTexture texture,RectF res,RectF dst,Color4 color,float z){
		//  3          2
		//   ┌────┐
		//   └────┘
		//  0          1
		RectVertex v0=RectVertex.atRect(dst,0,1);
		v0.setPosition(new Vec3(dst.getPoint(0,1),z));
		v0.setColor(color);
		v0.setTexturePoint(texture.toTexturePosition(res.getX1(),res.getY2()));
		RectVertex v1=RectVertex.atRect(dst,1,1);
		v1.setPosition(new Vec3(dst.getPoint(1,1),z));
		v1.setColor(color);
		v1.setTexturePoint(texture.toTexturePosition(res.getX2(),res.getY2()));
		RectVertex v2=RectVertex.atRect(dst,1,0);
		v2.setPosition(new Vec3(dst.getPoint(1,0),z));
		v2.setColor(color);
		v2.setTexturePoint(texture.toTexturePosition(res.getX2(),res.getY1()));
		RectVertex v3=RectVertex.atRect(dst,0,0);
		v3.setPosition(new Vec3(dst.getPoint(0,0),z));
		v3.setColor(color);
		v3.setTexturePoint(texture.toTexturePosition(res.getX1(),res.getY1()));
		return new RectVertex[]{v0,v1,v2,v3};
	}
	
	private RectVertex[] createRectVertexs(TextureRegion texture,RectF res,RectF dst,Color4 color,float z){
		//  3          2
		//   ┌────┐
		//   └────┘
		//  0          1
		RectVertex v0=RectVertex.atRect(dst,0,1);
		v0.setPosition(new Vec3(dst.getPoint(0,1),z));
		v0.setColor(color);
		v0.setTexturePoint(texture.toTexturePosition(res.getX1(),res.getY2()));
		RectVertex v1=RectVertex.atRect(dst,1,1);
		v1.setPosition(new Vec3(dst.getPoint(1,1),z));
		v1.setColor(color);
		v1.setTexturePoint(texture.toTexturePosition(res.getX2(),res.getY2()));
		RectVertex v2=RectVertex.atRect(dst,1,0);
		v2.setPosition(new Vec3(dst.getPoint(1,0),z));
		v2.setColor(color);
		v2.setTexturePoint(texture.toTexturePosition(res.getX2(),res.getY1()));
		RectVertex v3=RectVertex.atRect(dst,0,0);
		v3.setPosition(new Vec3(dst.getPoint(0,0),z));
		v3.setColor(color);
		v3.setTexturePoint(texture.toTexturePosition(res.getX1(),res.getY1()));
		return new RectVertex[]{v0,v1,v2,v3};
	}
	
	/**
	 *@param res:此处为Texture范围，使用实际像素坐标（原点左上）
	 *@param dst:绘制在canvas上的坐标，也是实际像素坐标（原点左下）
	 */
	public void drawTexture(GLTexture texture,RectF res,RectF dst,Color4 mixColor,Color4 color,float colorMixRate,float z,float alpha){
		checkCanDraw();
		tmpRectBatch.clear();
		tmpRectBatch.setColorMixRate(colorMixRate);
		RectVertex[] v=createRectVertexs(texture,res,dst,color,z);
		tmpRectBatch.add(v[0],v[1],v[2],v[0],v[2],v[3]);
		drawTexture3DBatch(tmpRectBatch,texture,alpha,mixColor);
	}
	
	public void drawTexture(GLTexture texture,RectF res,RectF dst,GLPaint paint){
		drawTexture(texture,res,dst,paint.getMixColor(),paint.getVaryingColor(),paint.getColorMixRate(),defZ,paint.getFinalAlpha());
	}
	
	public void drawTexture(GLTexture texture,RectF res,RectF dst,Color4 mixColor,Color4 color,float colorMixRate,float alpha){
		drawTexture(texture,res,dst,mixColor,color,colorMixRate,defZ,alpha);
	}
	
	public void drawTexture(TextureRegion texture,RectF res,RectF dst,Color4 mixColor,Color4 color,float colorMixRate,float z,float alpha){
		checkCanDraw();
		tmpRectBatch.clear();
		tmpRectBatch.setColorMixRate(colorMixRate);
		RectVertex[] v=createRectVertexs(texture,res,dst,color,z);
		tmpRectBatch.add(v[0],v[1],v[2],v[0],v[2],v[3]);
		drawTexture3DBatch(tmpRectBatch,texture,alpha,mixColor);
	}
	
	public void drawRectTexture(GLTexture texture,RectF res,RectF dst,GLPaint paint){
		checkCanDraw();
		tmpRectBatch.clear();
		tmpRectBatch.setColorMixRate(paint.getColorMixRate());
		RectVertex[] v=createRectVertexs(texture,res,dst,paint.getVaryingColor(),defZ);
		tmpRectBatch.add(v[0],v[1],v[2],v[0],v[2],v[3]);
		drawRectBatch(tmpRectBatch,texture,dst,paint);
	}
	
	public void drawRoundedRectTexture(GLTexture texture,RectF res,RectF dst,GLPaint paint){
		checkCanDraw();
		tmpRectBatch.clear();
		tmpRectBatch.setColorMixRate(paint.getColorMixRate());
		RectVertex[] v=createRectVertexs(texture,res,dst,paint.getVaryingColor(),defZ);
		tmpRectBatch.add(v[0],v[1],v[2],v[0],v[2],v[3]);
		drawRoundedRectBatch(tmpRectBatch,texture,dst,paint);
	}
	
	public void drawTexture(TextureRegion texture,RectF res,RectF dst,GLPaint paint){
		drawTexture(texture,res,dst,paint.getMixColor(),paint.getVaryingColor(),paint.getColorMixRate(),defZ,paint.getFinalAlpha());
	}
	
	public void drawTexture(TextureRegion texture,RectF res,RectF dst,Color4 mixColor,Color4 color,float colorMixRate,float alpha){
		drawTexture(texture,res,dst,mixColor,color,colorMixRate,defZ,alpha);
	}
	
	public void drawRectBatch(RectVertexBatch batch,GLTexture texture,RectF dst,GLPaint paint){
		checkCanDraw();
		RectShader shader=getContext().getShaderManager().getRectShader();
		injectData(batch,texture,paint.getFinalAlpha(),paint.getMixColor(),shader);
		injectRectData(batch,dst,paint.getPadding(),shader);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, batch.getVertexCount());
	}
	
	public void drawRoundedRectBatch(RectVertexBatch batch,GLTexture texture,RectF dst,GLPaint paint){
		checkCanDraw();
		RoundedRectShader shader=getContext().getShaderManager().getRoundedRectShader();
		injectData(batch,texture,paint.getFinalAlpha(),paint.getMixColor(),shader);
		injectRoundedRectData(batch,dst,paint.getPadding(),paint.getRoundedRadius(),paint.getGlowColor(),paint.getGlowFactor(),shader);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, batch.getVertexCount());
	}
	
	public MContext getContext(){
		return getLayer().getContext();
	}
	
	public Texture3DShader getDefTexture3DShader(){
		return getContext().getShaderManager().getStdTexture3DShader();
	}
	
	public Mat4 createDefProjMatrix(){
		Mat4 projMatrix=new Mat4();
		return projMatrix.setOrtho(0,layer.getWidth(),layer.getHeight(),0,-100,100);
	}
	
	@Override
	public void recycle(){
		tmpBatch.clear();
	}
}
