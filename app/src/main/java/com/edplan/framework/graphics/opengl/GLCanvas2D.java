package com.edplan.framework.graphics.opengl;
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
import com.edplan.framework.math.IQuad;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.graphics.opengl.batch.BaseBatch;

public class GLCanvas2D extends BaseCanvas // extends AbstractSRable<CanvasData>
{
	private BufferedLayer layer;
	
	public GLCanvas2D(BufferedLayer layer){
		this.layer=layer;
		initial();
		getData().setWidth(layer.getWidth());
		getData().setHeight(layer.getHeight());
	}
	
	public GLCanvas2D(GLTexture texture,MContext context){
		this(new BufferedLayer(context,texture));
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
		d.setCurrentProjMatrix(createDefProjMatrix());
		d.setCurrentMaskMatrix(Mat4.createIdentity());
		d.setHeight(getLayer().getHeight());
		d.setWidth(getLayer().getWidth());
		d.setShaders(ShaderManager.get());
		return d;
	}

	@Override
	public BlendSetting getBlendSetting() {
		// TODO: Implement this method
		return GLWrapped.blend;
	}

	@Override
	public int getDefHeight() {
		// TODO: Implement this method
		return layer.getHeight();
	}

	@Override
	public int getDefWidth() {
		// TODO: Implement this method
		return layer.getWidth();
	}
	
	@Override
	public void checkCanDraw(){
		checkPrepared(
			"canvas hasn't prepared for draw",
			true);	
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
	
	@Override
	public void delete(){
		checkPrepared("you delete a prepared canvas!",false);
		recycle();
	}

	@Override
	public void drawColor(Color4 color){
		GLWrapped.setClearColor(color.r,color.g,color.b,color.a);
		GLWrapped.clearColorBuffer();
	}
	
	public void clearDepthBuffer(){
		GLWrapped.clearDepthBuffer();
	}
	
	@Override
	public void clearBuffer(){
		GLWrapped.clearDepthAndColorBuffer();
	}
	
	public MContext getContext(){
		return getLayer().getContext();
	}

	@Override
	public void recycle(){
		super.recycle();
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO: Implement this method
		super.finalize();
	}
}
