package com.edplan.framework.graphics.opengl.batch;

import com.edplan.framework.graphics.opengl.buffer.Vec2Buffer;
import com.edplan.framework.graphics.opengl.buffer.Vec3Buffer;
import com.edplan.framework.graphics.opengl.objs.vertex.RectVertex;
import com.edplan.framework.graphics.opengl.batch.base.IHasRectPosition;

public class RectVertexBatch<T extends RectVertex> extends Texture3DBatch<T> implements IHasRectPosition
{
	private Vec2Buffer rectPositionBuffer=new Vec2Buffer();
	@Override
	public Vec2Buffer makeRectPositionBuffer(){
		rectPositionBuffer.clear();
		for(T t:vertexs){
			rectPositionBuffer.add(t.getRectPosition());
		}
		return rectPositionBuffer;
	}
}
