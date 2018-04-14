package com.edplan.framework.graphics.opengl.buffer;
import com.edplan.framework.math.Vec3;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Vec3Buffer
{
	public static final int RAW_SIZE=3;
	public static final int RAW_BYTE_SIZE=4;
	
	public List<Vec3> bufferList;

	public FloatBuffer buffer;
	
	public Vec3Buffer(){
		initial();
	}
	
	public void initial(){
		bufferList=new ArrayList<Vec3>();
	}
	
	public Vec3Buffer add(Vec3 t){
		bufferList.add(t);
		return this;
	}
	
	public void clear(){
		bufferList.clear();
	}
	
	int latestListSize;
	public FloatBuffer makeBuffer(){
		if(bufferList.size()<=latestListSize&&buffer!=null){
			int position=(latestListSize-bufferList.size())*RAW_SIZE;
			buffer.position(position);
			for(Vec3 t:bufferList){
				buffer.put(t.x).put(t.y).put(t.z);
			}
			buffer.position(position);
			return buffer;
		}

		if(buffer!=null)buffer.clear();
		buffer=createFloatBuffer(bufferList.size()*RAW_SIZE);
		for(Vec3 t:bufferList){
			buffer.put(t.x).put(t.y).put(t.z);
		}
		buffer.position(0);
		latestListSize=bufferList.size();
		return buffer;
	}
	
	/*
	public FloatBuffer makeBuffer(){
		if(buffer!=null)buffer.clear();
		buffer=createFloatBuffer(bufferList.size()*3);
		for(Vec3 t:bufferList){
			buffer.put(t.x).put(t.y).put(t.z);
		}
		buffer.position(0);
		return buffer;
	}
	*/
	
	public static FloatBuffer createFloatBuffer(int floatCount){
		ByteBuffer bb=ByteBuffer.allocateDirect(floatCount*RAW_BYTE_SIZE);
		bb.order(ByteOrder.nativeOrder());
		return bb.asFloatBuffer();
	}
}
