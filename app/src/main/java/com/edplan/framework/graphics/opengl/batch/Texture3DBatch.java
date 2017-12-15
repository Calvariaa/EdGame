package com.edplan.framework.graphics.opengl.batch;
import com.edplan.framework.graphics.opengl.buffer.Color4Buffer;
import com.edplan.framework.graphics.opengl.buffer.Vec2Buffer;
import com.edplan.framework.graphics.opengl.buffer.Vec3Buffer;
import com.edplan.framework.graphics.opengl.objs.TextureVertex3D;
import java.util.ArrayList;
import java.util.List;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.graphics.opengl.shader.GLProgram;
import com.edplan.framework.graphics.opengl.shader.advance.Texture3DShader;

public class Texture3DBatch
{
	private List<TextureVertex3D> vertexs;
	
	private float colorMixRate;
	
	public Texture3DBatch(){
		vertexs=new ArrayList<TextureVertex3D>();
	}
	
	public int getVertexCount(){
		return vertexs.size();
	}

	public void setColorMixRate(float colorMixRate) {
		this.colorMixRate=colorMixRate;
	}

	public float getColorMixRate() {
		return colorMixRate;
	}
	
	public Texture3DBatch add(TextureVertex3D... vs){
		for(TextureVertex3D v:vs)
			add(v);
		return this;
	}

	public Texture3DBatch add(TextureVertex3D v){
		vertexs.add(v);
		return this;
	}

	//public void removeAll(){
	//	vertexs.
	//}
	
	public void clear(){
		vertexs.clear();
	}

	public Color4Buffer makeColorBuffer(){
		Color4Buffer buf=new Color4Buffer();
		for(TextureVertex3D t:vertexs){
			buf.add(t.getColor());
		}
		return buf;
	}

	public Vec2Buffer makeTexturePositionBuffer(){
		Vec2Buffer buf=new Vec2Buffer();
		for(TextureVertex3D t:vertexs){
			buf.add(t.getTexturePoint());
		}
		return buf;
	}

	public Vec3Buffer makePositionBuffer(){
		Vec3Buffer buf=new Vec3Buffer();
		for(TextureVertex3D t:vertexs){
			buf.add(t.getPosition());
		}
		return buf;
	}

}
