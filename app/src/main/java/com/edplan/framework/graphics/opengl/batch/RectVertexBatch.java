package com.edplan.framework.graphics.opengl.batch;

import com.edplan.framework.graphics.opengl.buffer.Vec2Buffer;
import com.edplan.framework.graphics.opengl.buffer.Vec3Buffer;
import com.edplan.framework.graphics.opengl.objs.vertex.RectVertex;

public class RectVertexBatch<T extends RectVertex> extends Texture3DBatch<T> 
{
	private Vec2Buffer rectPositionBuffer;
	public Vec2Buffer makeRectPositionBuffer(){
		if(rectPositionBuffer!=null)rectPositionBuffer.clear();
		rectPositionBuffer=new Vec2Buffer();
		for(T t:vertexs){
			rectPositionBuffer.add(t.getRectPosition());
		}
		return rectPositionBuffer;
	}
}
