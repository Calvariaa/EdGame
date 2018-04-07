package com.edplan.framework.graphics.opengl;
import android.opengl.GLES20;
import com.edplan.framework.MContext;
import com.edplan.framework.graphics.layer.BufferedLayer;
import com.edplan.framework.graphics.opengl.batch.BaseColorBatch;
import com.edplan.framework.graphics.opengl.batch.RectVertexBatch;
import com.edplan.framework.graphics.opengl.batch.Texture3DBatch;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.AbstractTexture;
import com.edplan.framework.graphics.opengl.objs.TextureVertex3D;
import com.edplan.framework.graphics.opengl.objs.Vertex3D;
import com.edplan.framework.graphics.opengl.objs.vertex.RectVertex;
import com.edplan.framework.graphics.opengl.shader.advance.RectTextureShader;
import com.edplan.framework.graphics.opengl.shader.advance.RoundedRectTextureShader;
import com.edplan.framework.graphics.opengl.shader.advance.Texture3DShader;
import com.edplan.framework.math.Mat4;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.math.Vec3;
import com.edplan.framework.math.Vec4;
import com.edplan.framework.utils.AbstractSRable;
import com.edplan.framework.graphics.opengl.shader.advance.ColorShader;
import com.edplan.framework.utils.MLog;

public class GLCanvas2D extends AbstractSRable<CanvasData>
{
	private BufferedLayer layer;

	private float defZ=0;
	
	private Texture3DBatch<TextureVertex3D> tmpBatch=new Texture3DBatch<TextureVertex3D>();
	
	private RectVertexBatch<RectVertex> tmpRectBatch=new RectVertexBatch<RectVertex>();
	
	private BaseColorBatch<Vertex3D> tmpColorBatch=new BaseColorBatch<Vertex3D>();
	
	public GLCanvas2D(BufferedLayer layer){
		this.layer=layer;
		initial();
		getData().setWidth(layer.getWidth());
		getData().setHeight(layer.getHeight());
	}
	
	public GLCanvas2D translate(float tx,float ty){
		getData().translate(tx,ty);
		return this;
	}
	
	public GLCanvas2D scaleContent(float s){
		getData().scaleContent(s);
		return this;
	}
	
	public GLCanvas2D clip(Vec2 wh){
		getData().clip(wh);
		return this;
	}
	
	public float getPixelDensity(){
		return getData().getPixelDensity();
	}
	
	public float getWidth(){
		return getData().getWidth();
	}
	
	public float getHeight(){
		return getData().getHeight();
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
	
	public float getCanvasAlpha(){
		return getData().getCanvasAlpha();
	}

	public void setCanvasAlpha(float a){
		getData().setCanvasAlpha(a);
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
		d.setHeight(getLayer().getHeight());
		d.setWidth(getLayer().getWidth());
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
		return getData().getFinalMatrix();
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
	
	private void injectData(Texture3DBatch batch,AbstractTexture texture,float alpha,Color4 mixColor,Texture3DShader shader){
		shader.useThis();
		shader.loadMixColor(mixColor);
		//shader.loadColorMixRate(batch.getColorMixRate());
		shader.loadAlpha(alpha*getCanvasAlpha());
		shader.loadMVPMatrix(getFinalMatrix());
		shader.loadMaskMatrix(getMaskMatrix());
		shader.loadTexture(texture.getTexture());
		shader.loadPosition(batch.makePositionBuffer());
		shader.loadColor(batch.makeColorBuffer());
		shader.loadTexturePosition(batch.makeTexturePositionBuffer());
	}
	
	public void injectRectData(RectVertexBatch batch,RectF drawingRect,Vec4 padding,RectTextureShader shader){
		shader.useThis();
		shader.loadRectData(drawingRect,padding);
		shader.loadRectPositions(batch.makeRectPositionBuffer());
	}
	
	public void injectRoundedRectData(RectVertexBatch batch,RectF drawingRect,Vec4 padding,float radius,Color4 glowColor,float glowFactor,RoundedRectTextureShader shader){
		shader.useThis();
		shader.loadRectData(drawingRect,padding,radius,glowColor,glowFactor);
		shader.loadRectPositions(batch.makeRectPositionBuffer());
	}
	
	public void drawTexture3DBatch(Texture3DBatch batch,AbstractTexture texture,float alpha,Color4 mixColor){
		checkCanDraw();
		injectData(batch,texture.getTexture(),alpha,mixColor,getData().getTexture3DShader());
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, batch.getVertexCount());
	}
	
	private RectVertex[] createRectVertexs(AbstractTexture texture,RectF res,RectF dst,Color4 color,float z){
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
	
	public TextureVertex3D[] makeupVertex(AbstractTexture texture,Vec2[] resV,Vec2[] dstV,Color4 varyColors){
		TextureVertex3D[] ary=new TextureVertex3D[resV.length];
		Vec2 curRes;
		Vec2 curDst;
		for(int i=0;i<ary.length;i++){
			curRes=resV[i];
			curDst=dstV[i];
			ary[i]=TextureVertex3D.atPosition(new Vec3(curDst.x,curDst.y,defZ))
									.setTexturePoint(texture.toTexturePosition(curRes.x,curRes.y))
									.setColor(varyColors);
		}
		return ary;
	}
	
	/**
	 *@param res:此处为Texture范围，使用实际像素坐标（原点左上）
	 *@param dst:绘制在canvas上的坐标，也是实际像素坐标（原点左下）
	 */
	public void drawTexture(AbstractTexture texture,RectF res,RectF dst,Color4 mixColor,Color4 varyColor,float z,float alpha){
		checkCanDraw();
		tmpBatch.clear();
		//tmpBatch.setColorMixRate(colorMixRate);
		RectVertex[] v=createRectVertexs(texture,res,dst,varyColor,z);
		tmpBatch.add(v[0],v[1],v[2],v[0],v[2],v[3]);
		drawTexture3DBatch(tmpBatch,texture,alpha,mixColor);
	}
	
	public void drawTexture(AbstractTexture texture,Vec2[] resV,Vec2[] dstV,Color4 varyColor,float finalAlpha,Color4 mixColor){
		checkCanDraw();
		tmpBatch.clear();
		//tmpBatch.setColorMixRate(colorMixRate);
		tmpBatch.add(makeupVertex(texture,resV,dstV,varyColor));
		drawTexture3DBatch(tmpBatch,texture,finalAlpha,mixColor);
	}
	
	public void drawTexture(AbstractTexture texture,RectF res,RectF dst,GLPaint paint){
		drawTexture(texture,res,dst,paint.getMixColor(),paint.getVaryingColor(),defZ,paint.getFinalAlpha());
	}
	
	public void drawTexture(AbstractTexture texture,RectF res,RectF dst,Color4 mixColor,Color4 color,float alpha){
		drawTexture(texture,res,dst,mixColor,color,defZ,alpha);
	}
	
	public void drawTexture(AbstractTexture texture,RectF dst,Color4 mixColor,Color4 color,float z,float alpha){
		checkCanDraw();
		tmpBatch.clear();
		//tmpBatch.setColorMixRate(colorMixRate);
		RectVertex[] v=createRectVertexs(texture,new RectF(0,0,texture.getWidth(),texture.getHeight()),dst,color,z);
		tmpBatch.add(v[0],v[1],v[2],v[0],v[2],v[3]);
		drawTexture3DBatch(tmpBatch,texture,alpha,mixColor);
	}
	
	public void drawRectTexture(AbstractTexture texture,RectF res,RectF dst,GLPaint paint){
		checkCanDraw();
		tmpRectBatch.clear();
		//tmpRectBatch.setColorMixRate(paint.getColorMixRate());
		RectVertex[] v=createRectVertexs(texture,res,dst,paint.getVaryingColor(),defZ);
		tmpRectBatch.add(v[0],v[1],v[2],v[0],v[2],v[3]);
		drawRectBatch(tmpRectBatch,texture,dst,paint);
	}
	
	public void drawRoundedRectTexture(AbstractTexture texture,RectF res,RectF dst,GLPaint paint){
		checkCanDraw();
		tmpRectBatch.clear();
		//tmpRectBatch.setColorMixRate(paint.getColorMixRate());
		RectVertex[] v=createRectVertexs(texture,res,dst,paint.getVaryingColor(),defZ);
		tmpRectBatch.add(v[0],v[1],v[2],v[0],v[2],v[3]);
		drawRoundedRectBatch(tmpRectBatch,texture,dst,paint);
	}
	
	public void drawTexture(AbstractTexture texture,RectF dst,GLPaint paint){
		drawTexture(texture,dst,paint.getMixColor(),paint.getVaryingColor(),defZ,paint.getFinalAlpha());
	}
	
	public void drawTexture(AbstractTexture texture,RectF dst,Color4 mixColor,Color4 color,float alpha){
		drawTexture(texture,dst,mixColor,color,defZ,alpha);
	}
	
	public void drawRectBatch(RectVertexBatch batch,AbstractTexture texture,RectF dst,GLPaint paint){
		checkCanDraw();
		RectTextureShader shader=getContext().getShaderManager().getRectShader();
		injectData(batch,texture,paint.getFinalAlpha(),paint.getMixColor(),shader);
		injectRectData(batch,dst,paint.getPadding(),shader);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, batch.getVertexCount());
	}
	
	public void drawRoundedRectBatch(RectVertexBatch batch,AbstractTexture texture,RectF dst,GLPaint paint){
		checkCanDraw();
		RoundedRectTextureShader shader=getContext().getShaderManager().getRoundedRectShader();
		injectData(batch,texture,paint.getFinalAlpha(),paint.getMixColor(),shader);
		injectRoundedRectData(batch,dst,paint.getPadding(),paint.getRoundedRadius(),paint.getGlowColor(),paint.getGlowFactor(),shader);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, batch.getVertexCount());
	}
	
	public Vertex3D[] createLineRectVertex(Vec2 start,Vec2 end,float w,Color4 color){
		Vec2 dirt=end.copy().minus(start);
		dirt.toOrthogonalDirectionNormal().zoom(w);
		Vertex3D v1=Vertex3D
			.atPosition(new Vec3(start.copy().add(dirt),defZ))
			.setColor(color);
		Vertex3D v2=Vertex3D
			.atPosition(new Vec3(start.copy().minus(dirt),defZ))
			.setColor(color);
		Vertex3D v3=Vertex3D
			.atPosition(new Vec3(end.copy().add(dirt),defZ))
			.setColor(color);
		Vertex3D v4=Vertex3D
			.atPosition(new Vec3(end.copy().minus(dirt),defZ))
			.setColor(color);
		return new Vertex3D[]{v1,v2,v3,v4};
	}
	
	public Vertex3D[] rectToTriangles(Vertex3D[] v){
		return new Vertex3D[]{v[0],v[1],v[3],v[0],v[3],v[2]};
	}
	
	public Vertex3D[] createLineRectVertexTriangles(Vec2 start,Vec2 end,float w,Color4 color){
		Vertex3D[] v=createLineRectVertex(start,end,w,color);
		return rectToTriangles(v);
	}
	
	public void clearTmpColorBatch(){
		tmpColorBatch.clear();
	}
	
	public void addToColorBatch(Vertex3D[] v){
		tmpColorBatch.add(v);
	}
	
	public void drawColorBatch(GLPaint paint,BaseColorBatch cbatch){
		ColorShader<BaseColorBatch> shader=getContext().getShaderManager().getColorShader();
		shader.useThis();
		shader.loadPaint(paint,getCanvasAlpha());
		MLog.test.vOnce("loadPaint","gl-test","loadPaint");
		shader.loadMatrix(getFinalMatrix(),getMaskMatrix());
		MLog.test.vOnce("loadMat","gl-test","loadMat");
		shader.loadBatch(cbatch);
		MLog.test.vOnce("loadBatch","gl-test","loadBatch");
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,cbatch.getVertexCount());
		MLog.test.vOnce("postDraw","gl-test","postDraw");
	}
	
	public void postColorBatch(GLPaint paint){
		drawColorBatch(paint,tmpColorBatch);
	}
	
	public void drawLines(float[] lines,GLPaint paint){
		tmpColorBatch.clear();
		for(int i=0;i<lines.length;i+=4){
			addToColorBatch(
				createLineRectVertexTriangles(
					new Vec2(lines[i],lines[i+1]),
					new Vec2(lines[i+2],lines[i+3]),
					paint.getStrokeWidth(),
					paint.getMixColor()
				)
			);
		}
		postColorBatch(paint);
	}

	public void drawLine(float x1,float y1,float x2,float y2,GLPaint paint){
		drawLines(new float[]{x1,y1,x2,y2},paint);
	}

	/**
	 *通过简便的参数以canvas上某一点为中心绘制一张Texture
	 */
	public void drawTextureAnchorCenter(AbstractTexture texture,Vec2 org,Vec2 w,GLPaint paint){
		RectF dst=RectF.ltrb(org.x-w.x,org.y-w.y,org.x+w.x,org.y+w.y);
		drawTexture(texture,new RectF(0,0,texture.getWidth(),texture.getHeight()),dst,paint);
	}
	
	public void drawTextureAnchorCenter(AbstractTexture texture,Vec2 org,Vec4 w,GLPaint paint){
		RectF dst=RectF.ltrb(org.x-w.r,org.y-w.g,org.x+w.b,org.y+w.a);
		drawTexture(texture,new RectF(0,0,texture.getWidth(),texture.getHeight()),dst,paint);
	}
	
	public MContext getContext(){
		return getLayer().getContext();
	}
	
	public Texture3DShader getDefTexture3DShader(){
		return getContext().getShaderManager().getTexture3DShader();
	}
	
	public Mat4 createDefProjMatrix(){
		Mat4 projMatrix=new Mat4();
		projMatrix.setOrtho(0,layer.getWidth(),0,layer.getHeight(),-100,100);
		return projMatrix;
	}
	
	@Override
	public void recycle(){
		tmpBatch.clear();
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO: Implement this method
		super.finalize();
	}
}
