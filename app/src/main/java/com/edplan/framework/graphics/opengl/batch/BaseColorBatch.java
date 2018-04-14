package com.edplan.framework.graphics.opengl.batch;
import com.edplan.framework.graphics.opengl.buffer.Color4Buffer;
import com.edplan.framework.graphics.opengl.buffer.Vec3Buffer;
import com.edplan.framework.graphics.opengl.objs.Vertex3D;
import java.util.ArrayList;
import java.util.List;
import com.edplan.framework.graphics.opengl.objs.VertexList;
import com.edplan.framework.graphics.opengl.batch.base.IHasColor;
import com.edplan.framework.graphics.opengl.batch.base.IHasPosition;

public class BaseColorBatch<T extends Vertex3D> extends BaseBatch implements IHasColor,IHasPosition
{
	protected List<T> vertexs;
	
	public BaseColorBatch(){
		vertexs=new ArrayList<T>();
	}
	
	public T get(int idx){
		return vertexs.get(idx);
	}

	public int getVertexCount(){
		return vertexs.size();
	}
	
	public BaseColorBatch add(VertexList<T> list){
		return add(list.listVertex());
	}
	
	public BaseColorBatch add(T... vs){
		for(T v:vs)
			add(v);
		return this;
	}

	public BaseColorBatch add(T v){
		vertexs.add(v);
		return this;
	}
	
	public void clear(){
		vertexs.clear();
	}

	private Color4Buffer colorBuffer;
	@Override
	public Color4Buffer makeColorBuffer(){
		if(colorBuffer!=null)colorBuffer.clear();
		colorBuffer=new Color4Buffer();
		for(T t:vertexs){
			colorBuffer.add(t.getColor());
		}
		return colorBuffer;
	}

	private Vec3Buffer positionBuffer;
	@Override
	public Vec3Buffer makePositionBuffer(){
		if(positionBuffer!=null)positionBuffer.clear();
		positionBuffer=new Vec3Buffer();
		for(T t:vertexs){
			positionBuffer.add(t.getPosition());
		}
		return positionBuffer;
	}
	
}
