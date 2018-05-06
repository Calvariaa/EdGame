package com.edplan.framework.graphics.opengl.shader.advance;

import com.edplan.framework.graphics.opengl.buffer.Color4Buffer;
import com.edplan.framework.graphics.opengl.buffer.Vec2Buffer;
import com.edplan.framework.graphics.opengl.buffer.Vec3Buffer;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.graphics.opengl.shader.Attr;
import com.edplan.framework.graphics.opengl.shader.GLProgram;
import com.edplan.framework.graphics.opengl.shader.Unif;
import com.edplan.framework.graphics.opengl.shader.VertexAttrib;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformColor4;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformFloat;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformMat4;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformSample2D;
import com.edplan.framework.math.Mat4;
import com.edplan.framework.graphics.opengl.GLPaint;
import com.edplan.framework.graphics.opengl.batch.BaseColorBatch;
import com.edplan.framework.graphics.opengl.GLCanvas2D;
import com.edplan.framework.graphics.opengl.batch.base.IHasColor;
import com.edplan.framework.graphics.opengl.batch.base.IHasPosition;
import com.edplan.framework.graphics.opengl.batch.BaseBatch;
import android.opengl.GLES20;
import com.edplan.framework.graphics.opengl.Camera;
import com.edplan.framework.graphics.opengl.GLWrapped;
import java.nio.FloatBuffer;

public class ColorShader extends GLProgram 
{
	public static ColorShader Invalid=new InvalidColorShader();
	
	private UniformMat4 uMVPMatrix;
	
	private UniformMat4 uMaskMatrix;
	
	//private UniformFloat uColorMixRate;
	
	private UniformFloat uFinalAlpha;
	
	private UniformColor4 uMixColor;
	
	private VertexAttrib vPosition;
	
	private VertexAttrib vColor;
	
	protected ColorShader(GLProgram program){
		super(program.getVertexShader(),program.getFragmentShader(),program.getProgramId());
	}
	
	public void setUp(){
		uMVPMatrix=UniformMat4.findUniform(this,Unif.MVPMatrix);
		uMaskMatrix=UniformMat4.findUniform(this,Unif.MaskMatrix);
		//uColorMixRate=UniformFloat.findUniform(this,Unif.ColorMixRate);
		uFinalAlpha=UniformFloat.findUniform(this,Unif.FinalAlpha);
		uMixColor=UniformColor4.findUniform(this,Unif.MixColor);
		vPosition=VertexAttrib.findAttrib(this,Attr.Position,VertexAttrib.Type.VEC3);
		vColor=VertexAttrib.findAttrib(this,Attr.Color,VertexAttrib.Type.VEC4);
	}
	
	public void loadPaint(GLPaint paint,float alphaAdjust){
		loadMixColor(paint.getMixColor());
		loadAlpha(paint.getFinalAlpha()*alphaAdjust);
	}

	public boolean loadBatch(BaseBatch batch){
		if(batch instanceof IHasColor&&batch instanceof IHasPosition){
			loadColor(((IHasColor)batch).makeColorBuffer());
			loadPosition(((IHasPosition)batch).makePositionBuffer());
			return true;
		}else{
			return false;
		}
	}
	
	public void loadMatrix(Camera c){
		loadMVPMatrix(c.getFinalMatrix());
		loadMaskMatrix(c.getMaskMatrix());
	}
	
	public void loadMixColor(Color4 c){
		uMixColor.loadData(c);
	}

	protected void loadMVPMatrix(Mat4 mvp){
		uMVPMatrix.loadData(mvp);
	}
	
	protected void loadMaskMatrix(Mat4 mpm){
		uMaskMatrix.loadData(mpm);
	}
	
	public void loadPosition(FloatBuffer buffer){
		vPosition.loadData(buffer);
	}
	
	public void loadColor(FloatBuffer buffer){
		vColor.loadData(buffer);
	}

	public void loadAlpha(float a){
		uFinalAlpha.loadData(a);
	}
	
	public void applyToGL(int mode,int offset,int count){
		GLWrapped.drawArrays(mode,offset,count);
	}
	
	public static final ColorShader createCS(String vs,String fs){
		ColorShader s=new ColorShader(GLProgram.createProgram(vs,fs));
		s.setUp();
		return s;
	}
	
	public static class GL10ColorShader extends ColorShader{
		public GL10ColorShader(){
			super(GLProgram.invalidProgram());
		}
	}
	
	public static class InvalidColorShader extends ColorShader{
		public InvalidColorShader(){
			super(GLProgram.invalidProgram());
		}

		@Override
		public void setUp() {
			// TODO: Implement this method
			//super.setUp();
		}

		@Override
		public void loadColor(FloatBuffer buffer) {
			// TODO: Implement this method
			//super.loadColor(buffer);
		}

		@Override
		public void loadPaint(GLPaint paint,float alphaAdjust) {
			// TODO: Implement this method
			//super.loadPaint(paint, alphaAdjust);
		}

		@Override
		protected void loadMVPMatrix(Mat4 mvp) {
			// TODO: Implement this method
			//super.loadMVPMatrix(mvp);
		}

		@Override
		protected void loadMaskMatrix(Mat4 mpm) {
			// TODO: Implement this method
			//super.loadMaskMatrix(mpm);
		}

		@Override
		public boolean loadBatch(BaseBatch batch) {
			// TODO: Implement this method
			return true;//super.loadBatch(batch);
		}

		@Override
		public void loadPosition(FloatBuffer buffer) {
			// TODO: Implement this method
			//super.loadPosition(buffer);
		}

		@Override
		public void loadMixColor(Color4 c) {
			// TODO: Implement this method
			//super.loadMixColor(c);
		}

		@Override
		public void loadMatrix(Camera c) {
			// TODO: Implement this method
			//super.loadMatrix(mvp, mask);
		}

		@Override
		public void loadAlpha(float a) {
			// TODO: Implement this method
			//super.loadAlpha(a);
		}

		@Override
		public void applyToGL(int mode,int offset,int count) {
			// TODO: Implement this method
			//super.applyToGL(mode, offset, count);
		}
	}
}
