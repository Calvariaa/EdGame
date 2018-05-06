package com.edplan.framework.graphics.opengl.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class BufferUtil 
{
	public static FloatBuffer createFloatBuffer(int floatCount){
		ByteBuffer bb=ByteBuffer.allocateDirect(floatCount*4);
		bb.order(ByteOrder.nativeOrder());
		return bb.asFloatBuffer();
	}
}
