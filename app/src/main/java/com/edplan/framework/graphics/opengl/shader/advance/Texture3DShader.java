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

public class Texture3DShader extends GLProgram
{
	private UniformMat4 uMVPMatrix;
	
	private UniformFloat uColorMixRate;
	
	private UniformFloat uFinalAlpha;
	
	private UniformSample2D uTexture;
	
	private UniformColor4 uMixColor;
	
	private VertexAttrib vPosition;
	
	private VertexAttrib vColor;
	
	private VertexAttrib vTexturePosition;
	
	protected Texture3DShader(GLProgram program){
		super(program.getVertexShader(),program.getFragmentShader(),program.getProgramId());
		uMVPMatrix=UniformMat4.findUniform(this,Unif.MVPMatrix);
		uColorMixRate=UniformFloat.findUniform(this,Unif.ColorMixRate);
		uFinalAlpha=UniformFloat.findUniform(this,Unif.FinalAlpha);
		uTexture=UniformSample2D.findUniform(this,Unif.Texture,0);
		uMixColor=UniformColor4.findUniform(this,Unif.MixColor);
		vPosition=VertexAttrib.findAttrib(this,Attr.Position,VertexAttrib.Type.VEC3);
		vTexturePosition=VertexAttrib.findAttrib(this,Attr.Texturesition,VertexAttrib.Type.VEC2);
		vColor=VertexAttrib.findAttrib(this,Attr.Color,VertexAttrib.Type.VEC4);
	}
	
	public void loadMixColor(Color4 c){
		uMixColor.loadData(c);
	}
	
	public void loadTexture(GLTexture texture){
		uTexture.loadData(texture);
	}
	
	public void loadMVPMatrix(Mat4 mvp){
		uMVPMatrix.loadData(mvp);
	}
	
	public void loadPosition(Vec3Buffer buffer){
		vPosition.loadData(buffer);
	}
	
	public void loadColor(Color4Buffer buffer){
		vColor.loadData(buffer);
	}
	
	public void loadTexturePosition(Vec2Buffer buffer){
		vTexturePosition.loadData(buffer);
	}
	
	public void loadColorMixRate(float f){
		uColorMixRate.loadData(f);
	}
	
	public void loadAlpha(float a){
		uFinalAlpha.loadData(a);
	}
	
	public static Texture3DShader create(String vs,String fs){
		return new Texture3DShader(GLProgram.createProgram(vs,fs));
	}
}
