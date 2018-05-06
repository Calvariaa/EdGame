package com.edplan.framework.graphics.opengl.shader.advance;
import com.edplan.framework.graphics.opengl.shader.GLProgram;
import com.edplan.framework.graphics.opengl.shader.GLShader;
import com.edplan.framework.graphics.opengl.shader.advance.interfaces.ITexturedVertex3DShader;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformMat4;
import com.edplan.framework.graphics.opengl.shader.VertexAttrib;
import com.edplan.framework.graphics.opengl.shader.Unif;
import com.edplan.framework.graphics.opengl.shader.Attr;
import com.edplan.framework.math.Mat4;
import com.edplan.framework.graphics.opengl.buffer.Vec3Buffer;
import com.edplan.framework.graphics.opengl.buffer.Color4Buffer;
import com.edplan.framework.graphics.opengl.buffer.Vec2Buffer;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformFloat;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformSample2D;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformColor4;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.interfaces.Recycleable;
import com.edplan.framework.graphics.opengl.batch.BaseColorBatch;
import com.edplan.framework.graphics.opengl.batch.Texture3DBatch;
import com.edplan.framework.graphics.opengl.objs.texture.TextureRegion;
import com.edplan.framework.graphics.opengl.batch.BaseBatch;
import com.edplan.framework.graphics.opengl.batch.base.IHasTexturePosition;
import com.edplan.framework.graphics.opengl.GLPaint;
import com.edplan.framework.graphics.opengl.objs.AbstractTexture;
import com.edplan.framework.graphics.opengl.Camera;
import java.nio.FloatBuffer;

public class Texture3DShader extends ColorShader
{
	public static Texture3DShader Invalid=new InvalidTexture3DShader();
	
	private UniformSample2D uTexture;
	
	private VertexAttrib vTexturePosition;
	
	protected Texture3DShader(GLProgram program){
		super(program);
	}

	@Override
	public void setUp() {
		// TODO: Implement this method
		super.setUp();
		uTexture=UniformSample2D.findUniform(this,Unif.Texture,0);
		vTexturePosition=VertexAttrib.findAttrib(this,Attr.Texturesition,VertexAttrib.Type.VEC2);
	}

	@Override
	public boolean loadBatch(BaseBatch batch) {
		// TODO: Implement this method
		if(super.loadBatch(batch)){
			if(batch instanceof IHasTexturePosition){
				loadTexturePosition(((IHasTexturePosition)batch).makeTexturePositionBuffer());
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public void loadTexture(AbstractTexture texture){
		uTexture.loadData(texture.getTexture());
	}
	
	/*
	public void loadTexture(TextureRegion texture){
		uTexture.loadData(texture.getTexture());
	}
	*/
	
	public void loadTexturePosition(FloatBuffer buffer){
		vTexturePosition.loadData(buffer);
	}

	public static final Texture3DShader createT3S(String vs,String fs){
		Texture3DShader s=new Texture3DShader(GLProgram.createProgram(vs,fs));
		s.setUp();
		return s;
	}
	
	public static class InvalidTexture3DShader extends Texture3DShader{
		public InvalidTexture3DShader(){
			super(GLProgram.invalidProgram());
		}

		@Override
		public void setUp() {
			// TODO: Implement this method
			//super.setUp();
		}

		@Override
		public void loadPosition(FloatBuffer buffer) {
			// TODO: Implement this method
			//super.loadPosition(buffer);
		}

		@Override
		public void loadTexture(AbstractTexture texture) {
			// TODO: Implement this method
			//super.loadTexture(texture);
		}

		@Override
		public void loadColor(FloatBuffer buffer) {
			// TODO: Implement this method
			//super.loadColor(buffer);
		}

		@Override
		protected void loadMVPMatrix(Mat4 mvp) {
			// TODO: Implement this method
			//super.loadMVPMatrix(mvp);
		}

		@Override
		public void loadPaint(GLPaint paint,float alphaAdjust) {
			// TODO: Implement this method
			//super.loadPaint(paint, alphaAdjust);
		}

		@Override
		public void loadMatrix(Camera c) {
			// TODO: Implement this method
			//super.loadMatrix(mvp, mask);
		}

		@Override
		public void loadTexturePosition(FloatBuffer buffer) {
			// TODO: Implement this method
			//super.loadTexturePosition(buffer);
		}

		@Override
		public boolean loadBatch(BaseBatch batch) {
			// TODO: Implement this method
			return true;//super.loadBatch(batch);
		}

		@Override
		public void loadMixColor(Color4 c) {
			// TODO: Implement this method
			//super.loadMixColor(c);
		}

		@Override
		public void loadAlpha(float a) {
			// TODO: Implement this method
			//super.loadAlpha(a);
		}

		@Override
		protected void loadMaskMatrix(Mat4 mpm) {
			// TODO: Implement this method
			//super.loadMaskMatrix(mpm);
		}

		@Override
		public void applyToGL(int mode,int offset,int count) {
			// TODO: Implement this method
			//super.applyToGL(mode, offset, count);
		}
		
		
	}
}
