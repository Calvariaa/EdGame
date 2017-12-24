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
	
	public TextureVertex3D get(int idx){
		return vertexs.get(idx);
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

	private Color4Buffer colorBuffer;
	public Color4Buffer makeColorBuffer(){
		if(colorBuffer!=null)colorBuffer.clear();
		colorBuffer=new Color4Buffer();
		for(TextureVertex3D t:vertexs){
			colorBuffer.add(t.getColor());
		}
		return colorBuffer;
	}

	private Vec2Buffer texturePointBuffer;
	public Vec2Buffer makeTexturePositionBuffer(){
		if(texturePointBuffer!=null)texturePointBuffer.clear();
		texturePointBuffer=new Vec2Buffer();
		for(TextureVertex3D t:vertexs){
			texturePointBuffer.add(t.getTexturePoint());
		}
		return texturePointBuffer;
	}

	private Vec3Buffer positionBuffer;
	public Vec3Buffer makePositionBuffer(){
		if(positionBuffer!=null)positionBuffer.clear();
		positionBuffer=new Vec3Buffer();
		for(TextureVertex3D t:vertexs){
			positionBuffer.add(t.getPosition());
		}
		return positionBuffer;
	}

}
