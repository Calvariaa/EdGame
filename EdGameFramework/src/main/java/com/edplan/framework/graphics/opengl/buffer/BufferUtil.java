package com.edplan.framework.graphics.opengl.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class BufferUtil 
{
	public static FloatBuffer createFloatBuffer(int floatCount){
		ByteBuffer bb=ByteBuffer.allocateDirect(floatCount*Float.BYTES);
		bb.order(ByteOrder.nativeOrder());
		return bb.asFloatBuffer();
	}
	
	public static ShortBuffer createShortBuffer(int shortCount){
		ByteBuffer bb=ByteBuffer.allocateDirect(shortCount*Short.BYTES);
		bb.order(ByteOrder.nativeOrder());
		return bb.asShortBuffer();
	}
	
	public static IntBuffer createIntBuffer(int intCount){
		ByteBuffer bb=ByteBuffer.allocateDirect(intCount*Integer.BYTES);
		bb.order(ByteOrder.nativeOrder());
		return bb.asIntBuffer();
	}
}
