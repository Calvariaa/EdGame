package com.edplan.framework.graphics.opengl.buffer;
import com.edplan.framework.math.Vec2;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Vec2Buffer
{
	public List<Vec2> bufferList;

	public Vec2Buffer(){
		initial();
	}

	public void initial(){
		bufferList=new ArrayList<Vec2>();
	}

	public Vec2Buffer add(Vec2 t){
		bufferList.add(t);
		return this;
	}
	
	public void clear(){
		bufferList.clear();
	}
	

	public FloatBuffer makeBuffer(){
		FloatBuffer fb=createFloatBuffer(bufferList.size()*2);
		for(Vec2 t:bufferList){
			fb.put(t.x).put(t.y);
		}
		fb.position(0);
		return fb;
	}

	public static FloatBuffer createFloatBuffer(int floatCount){
		ByteBuffer bb=ByteBuffer.allocateDirect(floatCount*4);
		bb.order(ByteOrder.nativeOrder());
		return bb.asFloatBuffer();
	}
}
