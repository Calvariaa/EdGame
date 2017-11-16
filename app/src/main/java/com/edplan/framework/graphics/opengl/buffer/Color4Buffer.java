package com.edplan.framework.graphics.opengl.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import com.edplan.framework.graphics.opengl.objs.Color4;

public class Color4Buffer 
{
	public List<Color4> bufferList;

	public Color4Buffer(){
		initial();
	}

	public void initial(){
		bufferList=new ArrayList<Color4>();
	}
	
	public void clear(){
		bufferList.clear();
	}

	public Color4Buffer add(Color4 t){
		bufferList.add(t);
		return this;
	}

	public FloatBuffer makeBuffer(){
		FloatBuffer fb=createFloatBuffer(bufferList.size()*4);
		for(Color4 t:bufferList){
			fb.put(t.r).put(t.g).put(t.b).put(t.a);
		}
		fb.position(0);
		return fb;
	}

	public static FloatBuffer createFloatBuffer(int floatCount){
		ByteBuffer bb=ByteBuffer.allocateDirect(floatCount*3);
		bb.order(ByteOrder.nativeOrder());
		return bb.asFloatBuffer();
	}
}
