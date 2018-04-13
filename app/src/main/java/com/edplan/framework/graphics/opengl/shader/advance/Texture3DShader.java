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

public class Texture3DShader extends ColorShader
{	
	private UniformSample2D uTexture;
	
	private VertexAttrib vTexturePosition;
	
	protected Texture3DShader(GLProgram program){
		super(program);
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
	
	public void loadTexture(GLTexture texture){
		uTexture.loadData(texture);
	}
	
	public void loadTexture(TextureRegion texture){
		uTexture.loadData(texture.getTexture());
	}
	
	public void loadTexturePosition(Vec2Buffer buffer){
		vTexturePosition.loadData(buffer);
	}

	
	public static final Texture3DShader createT3S(String vs,String fs){
		return new Texture3DShader(GLProgram.createProgram(vs,fs));
	}
}
