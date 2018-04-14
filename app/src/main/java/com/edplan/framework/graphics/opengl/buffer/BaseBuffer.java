package com.edplan.framework.graphics.opengl.buffer;

import com.edplan.framework.graphics.opengl.objs.Color4;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public abstract class BaseBuffer<T>
{
	public ArrayList<T> bufferList;

	public FloatBuffer buffer;

	public BaseBuffer(int size){
		initial(size);
	}

	public void initial(int size){
		bufferList=new ArrayList<T>(size);
		ensureBufferSize(size);
	}
	
	public abstract int getFloatSize();
	protected abstract void addToBuffer(FloatBuffer fb,T t);

	public void clear(){
		bufferList.clear();
		//if(buffer!=null)buffer.clear();
	}

	public void add(T t){
		bufferList.add(t);
	}

	int latestListSize;

	public void ensureBufferSize(int size){
		if(latestListSize<size){
			latestListSize=size;
			if(buffer!=null)buffer.clear();
			buffer=createFloatBuffer(size*getFloatSize());
		}
	}

	public FloatBuffer makeBuffer(int offset,int length){
		int position=(latestListSize-length)*getFloatSize();
		buffer.position(position);
		T t;
		int end=offset+length;
		for(int i=offset;i<end;i++){
			t=bufferList.get(i);
			addToBuffer(buffer,t);
		}
		buffer.position(position);
		return buffer;
	}

	public FloatBuffer makeBuffer(){
		if(bufferList.size()<=latestListSize&&buffer!=null){
			return makeBuffer(0,bufferList.size());
		}

		if(buffer!=null)buffer.clear();
		buffer=createFloatBuffer(bufferList.size()*getFloatSize());
		latestListSize=bufferList.size();
		return makeBuffer(0,bufferList.size());
	}

	/*
	 public FloatBuffer makeBuffer(){
	 if(buffer!=null)buffer.clear();
	 buffer=createFloatBuffer(bufferList.size()*4);
	 //Log.v("gl_test","color count: "+bufferList.size());
	 for(Color4 t:bufferList){
	 buffer.put(t.r).put(t.g).put(t.b).put(t.a);
	 }
	 buffer.position(0);
	 return buffer;
	 }
	 */

	public static FloatBuffer createFloatBuffer(int floatCount){
		ByteBuffer bb=ByteBuffer.allocateDirect(floatCount*5);
		bb.order(ByteOrder.nativeOrder());
		return bb.asFloatBuffer();
	}
}
