package com.edplan.framework.graphics.opengl.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import com.edplan.framework.graphics.opengl.objs.Color4;
import android.util.Log;

public class Color4Buffer 
{
	public List<Color4> bufferList;
	
	public FloatBuffer buffer;

	public Color4Buffer(){
		initial();
	}

	public void initial(){
		bufferList=new ArrayList<Color4>();
	}
	
	public void clear(){
		bufferList.clear();
		if(buffer!=null)buffer.clear();
	}

	public Color4Buffer add(Color4 t){
		bufferList.add(t);
		return this;
	}

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

	public static FloatBuffer createFloatBuffer(int floatCount){
		ByteBuffer bb=ByteBuffer.allocateDirect(floatCount*4);
		bb.order(ByteOrder.nativeOrder());
		return bb.asFloatBuffer();
	}
}
