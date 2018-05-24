package com.edplan.framework.ui.drawable.sprite;
import com.edplan.framework.graphics.opengl.shader.VertexAttrib;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformSample2D;
import com.edplan.framework.graphics.opengl.objs.AbstractTexture;
import java.nio.Buffer;

public class TextureSpriteShader extends SpriteShader
{
	@PointerName
	public UniformSample2D uTexture;
	
	@PointerName
	@AttribType(VertexAttrib.Type.VEC2)
	public VertexAttrib aTextureCoord;
	
	public TextureSpriteShader(String vs,String fs){
		super(vs,fs);
	}
	
	public void loadTexture(AbstractTexture texture){
		uTexture.loadData(texture.getTexture());
	}
	
	public void loadTextureCoord(Buffer b){
		aTextureCoord.loadData(b);
	}
}
