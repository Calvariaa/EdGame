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

public class Texture3DShader<T extends Texture3DBatch> extends ColorShader<T>
{	
	private UniformSample2D uTexture;
	
	private VertexAttrib vTexturePosition;
	
	protected Texture3DShader(GLProgram program){
		super(program);
		uTexture=UniformSample2D.findUniform(this,Unif.Texture,0);
		vTexturePosition=VertexAttrib.findAttrib(this,Attr.Texturesition,VertexAttrib.Type.VEC2);
	}

	@Override
	public void loadBatch(T batch) {
		// TODO: Implement this method
		super.loadBatch(batch);
		loadTexturePosition(batch.makeTexturePositionBuffer());
	}
	
	public void loadTexture(GLTexture texture){
		uTexture.loadData(texture);
	}
	
	public void loadTexture(TextureRegion texture){
		uTexture.loadData(texture.getTexture());
	}
	
	public void loadTexturePosition(Vec2Buffer buffer){
		vTexturePosition.loadData(buffer);
	}

	
	public static final <T extends Texture3DBatch> Texture3DShader<T> createT3S(String vs,String fs,Class<T> klass){
		return new Texture3DShader<T>(GLProgram.createProgram(vs,fs));
	}
}
