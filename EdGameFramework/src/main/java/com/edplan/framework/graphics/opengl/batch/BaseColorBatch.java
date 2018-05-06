package com.edplan.framework.graphics.opengl.batch;
import com.edplan.framework.graphics.opengl.batch.base.IHasColor;
import com.edplan.framework.graphics.opengl.batch.base.IHasPosition;
import com.edplan.framework.graphics.opengl.buffer.BufferUtil;
import com.edplan.framework.graphics.opengl.buffer.Color4Buffer;
import com.edplan.framework.graphics.opengl.objs.Vertex3D;
import com.edplan.framework.graphics.opengl.objs.VertexList;
import com.edplan.framework.math.Vec3;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import com.edplan.framework.graphics.opengl.objs.Color4;

public class BaseColorBatch<T extends Vertex3D> implements BaseBatch<T>,IHasColor,IHasPosition
{
	protected List<T> vertexs;
	
	public BaseColorBatch(){
		vertexs=new ArrayList<T>();
	}
	
	public T get(int idx){
		return vertexs.get(idx);
	}

	@Override
	public int getVertexCount(){
		return vertexs.size();
	}
	
	
	public void add(VertexList<T> list){
		add(list.listVertex());
	}
	
	@Override
	public void add(T... vs){
		for(T v:vs)
			add(v);
	}

	@Override
	public void add(T v){
		vertexs.add(v);
	}
	
	public void clear(){
		vertexs.clear();
		//colorBuffer.clear();
		//positionBuffer.clear();
	}

	private FloatBuffer colorBuffer;
	@Override
	public FloatBuffer makeColorBuffer(){
		int floatSize=4*getVertexCount();
		if(colorBuffer==null){
			colorBuffer=BufferUtil.createFloatBuffer(floatSize);
		}
		colorBuffer.clear();
		if(colorBuffer.remaining()<floatSize){
			colorBuffer=BufferUtil.createFloatBuffer(floatSize);
			colorBuffer.clear();
		}
		Color4 tmp;
		for(T t:vertexs){
			tmp=t.getColor();
			colorBuffer.put(tmp.r).put(tmp.g).put(tmp.b).put(tmp.a);
		}
		colorBuffer.position(0);
		return colorBuffer;//colorBuffer;
	}

	private FloatBuffer positionBuffer;
	@Override
	public FloatBuffer makePositionBuffer(){
		int floatSize=3*getVertexCount();
		if(positionBuffer==null){
			positionBuffer=BufferUtil.createFloatBuffer(floatSize);
		}
		positionBuffer.clear();
		if(positionBuffer.remaining()<floatSize){
			positionBuffer=BufferUtil.createFloatBuffer(floatSize);
			positionBuffer.clear();
		}
		Vec3 tmp;
		for(T t:vertexs){
			tmp=t.getPosition();
			positionBuffer.put(tmp.x).put(tmp.y).put(tmp.z);
		}
		positionBuffer.position(0);
		return positionBuffer;//positionBuffer;
	}
	
}
