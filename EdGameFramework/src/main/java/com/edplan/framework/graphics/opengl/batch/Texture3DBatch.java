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
import com.edplan.framework.graphics.opengl.batch.base.IHasTexturePosition;
import com.edplan.framework.graphics.opengl.batch.interfaces.ITexture3DBatch;

public class Texture3DBatch<T extends TextureVertex3D> extends BaseColorBatch<T> implements ITexture3DBatch<T>
{
	public Texture3DBatch(){
		
	}

	private Vec2Buffer texturePointBuffer=new Vec2Buffer();
	@Override
	public Vec2Buffer makeTexturePositionBuffer(){
		texturePointBuffer.clear();
		for(T t:vertexs){
			texturePointBuffer.add(t.getTexturePoint());
		}
		return texturePointBuffer;
	}
}
