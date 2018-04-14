package com.edplan.framework.graphics.opengl.buffer;
import com.edplan.framework.math.Vec2;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Vec2Buffer
{
	public static final int RAW_SIZE=2;
	public static final int RAW_BYTE_SIZE=4;
	
	public List<Vec2> bufferList;
	
	public FloatBuffer buffer;

	public Vec2Buffer(){
		initial(12);
	}
	
	public Vec2Buffer(int size){
		initial(size);
	}

	public void initial(int size){
		bufferList=new ArrayList<Vec2>(size);
	}

	public Vec2Buffer add(Vec2 t){
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
			for(Vec2 t:bufferList){
				buffer.put(t.x).put(t.y);
			}
			buffer.position(position);
			return buffer;
		}
		
		if(buffer!=null)buffer.clear();
		buffer=createFloatBuffer(bufferList.size()*RAW_SIZE);
		for(Vec2 t:bufferList){
			buffer.put(t.x).put(t.y);
		}
		buffer.position(0);
		latestListSize=bufferList.size();
		return buffer;
	}

	public static FloatBuffer createFloatBuffer(int floatCount){
		ByteBuffer bb=ByteBuffer.allocateDirect(floatCount*RAW_BYTE_SIZE);
		bb.order(ByteOrder.nativeOrder());
		return bb.asFloatBuffer();
	}
}
