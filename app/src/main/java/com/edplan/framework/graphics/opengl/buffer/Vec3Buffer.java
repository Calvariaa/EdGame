package com.edplan.framework.graphics.opengl.buffer;
import com.edplan.framework.math.Vec3;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Vec3Buffer
{
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
		if(buffer!=null)buffer.clear();
	}
	
	public FloatBuffer makeBuffer(){
		if(buffer!=null)buffer.clear();
		buffer=createFloatBuffer(bufferList.size()*3);
		for(Vec3 t:bufferList){
			buffer.put(t.x).put(t.y).put(t.z);
		}
		buffer.position(0);
		return buffer;
	}
	
	public static FloatBuffer createFloatBuffer(int floatCount){
		ByteBuffer bb=ByteBuffer.allocateDirect(floatCount*4);
		bb.order(ByteOrder.nativeOrder());
		return bb.asFloatBuffer();
	}
}
