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
import com.edplan.framework.graphics.opengl.batch.interfaces.ITexture3DBatch;
import com.edplan.framework.media.video.tbv.element.DataDrawBaseTexture;

public class GLCanvas2D extends BaseCanvas // extends AbstractSRable<CanvasData>
{
	private BufferedLayer layer;
	
	public GLCanvas2D(BufferedLayer layer){
		this.layer=layer;
		initial();
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
		d.setShaders(ShaderManager.getNewDefault());
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
	
	@Override
	public boolean isPrepared(){
		return getLayer().isBind();
	}
	
	@Override
	public void prepare(){
		checkPrepared(
			"you can't call prepare when GLCanvas is prepared",
			false);
		getLayer().bind();
	}
	
	@Override
	public void unprepare(){
		checkPrepared(
			"you can't call unprepare when GLCanvas isn't prepared",
			true);
		getLayer().unbind();
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
