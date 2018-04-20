package com.edplan.framework.graphics.opengl;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.math.Mat4;

public class GL10Canvas2D extends BaseCanvas
{
	private int width;
	private int height;

	public GL10Canvas2D(int width,int height){
		this.width=width;
		this.height=height;
		initial();
	}
	
	@Override
	public boolean isPrepared() {
		// TODO: Implement this method
		return true;
	}

	@Override
	public void prepare() {
		// TODO: Implement this method
	}

	@Override
	public void unprepare() {
		// TODO: Implement this method
	}
	
	@Override
	public int getDefWidth() {
		// TODO: Implement this method
		return width;
	}

	@Override
	public int getDefHeight() {
		// TODO: Implement this method
		return height;
	}

	@Override
	public BlendSetting getBlendSetting() {
		// TODO: Implement this method
		return GLWrapped.blend;
	}

	@Override
	protected void checkCanDraw() {
		// TODO: Implement this method
		if(GLWrapped.GL_VERSION!=1){
			throw new GLException("you can only use GL10Canvas2D in GLES10....please use GLCanvas when it's GLES20 or higher");
		}
	}

	@Override
	public CanvasData getDefData() {
		// TODO: Implement this method
		CanvasData d=new CanvasData();
		d.setCurrentProjMatrix(createDefProjMatrix());
		d.setCurrentMaskMatrix(Mat4.createIdentity());
		d.setHeight(getDefHeight());
		d.setWidth(getDefWidth());
		d.setShaders(ShaderManager.getGL10FakerShader());
		return d;
	}

	@Override
	public void clearBuffer() {
		// TODO: Implement this method
		GLWrapped.clearDepthAndColorBuffer();
	}

	@Override
	public void drawColor(Color4 c) {
		// TODO: Implement this method
		GLWrapped.setClearColor(c);
		GLWrapped.clearColorBuffer();
	}

}
