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

public class ColorShader<T extends BaseColorBatch> extends GLProgram 
{
	private UniformMat4 uMVPMatrix;
	
	private UniformMat4 uMaskMatrix;
	
	private UniformFloat uColorMixRate;
	
	private UniformFloat uFinalAlpha;
	
	private UniformColor4 uMixColor;
	
	private VertexAttrib vPosition;
	
	private VertexAttrib vColor;
	
	protected ColorShader(GLProgram program){
		super(program.getVertexShader(),program.getFragmentShader(),program.getProgramId());
		uMVPMatrix=UniformMat4.findUniform(this,Unif.MVPMatrix);
		uMaskMatrix=UniformMat4.findUniform(this,Unif.MaskMatrix);
		uColorMixRate=UniformFloat.findUniform(this,Unif.ColorMixRate);
		uFinalAlpha=UniformFloat.findUniform(this,Unif.FinalAlpha);
		uMixColor=UniformColor4.findUniform(this,Unif.MixColor);
		vPosition=VertexAttrib.findAttrib(this,Attr.Position,VertexAttrib.Type.VEC3);
		vColor=VertexAttrib.findAttrib(this,Attr.Color,VertexAttrib.Type.VEC4);
	}
	
	public void loadPaint(GLPaint paint){
		loadMixColor(paint.getMixColor());
		loadAlpha(paint.getFinalAlpha());
		loadColorMixRate(paint.getColorMixRate());
	}
	
	public void loadBatch(T batch){
		loadColor(batch.makeColorBuffer());
		loadPosition(batch.makePositionBuffer());
	}
	
	public void loadMatrix(Mat4 mvp,Mat4 mask){
		loadMVPMatrix(mvp);
		loadMaskMatrix(mask);
	}
	
	public void loadMixColor(Color4 c){
		uMixColor.loadData(c);
	}
	
	public void loadMVPMatrix(Mat4 mvp){
		uMVPMatrix.loadData(mvp);
	}
	
	public void loadMaskMatrix(Mat4 mpm){
		uMaskMatrix.loadData(mpm);
	}
	
	public void loadPosition(Vec3Buffer buffer){
		vPosition.loadData(buffer);
	}
	
	public void loadColor(Color4Buffer buffer){
		vColor.loadData(buffer);
	}
	
	public void loadColorMixRate(float f){
		uColorMixRate.loadData(f);
	}
	
	public void loadAlpha(float a){
		uFinalAlpha.loadData(a);
	}
	
	public static final <T extends BaseColorBatch> ColorShader<T> createCS(String vs,String fs,Class<T> klass){
		return new ColorShader<T>(GLProgram.createProgram(vs,fs));
	}
}
