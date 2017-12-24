package com.edplan.opengl;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.microedition.khronos.opengles.GL10;

public class Triangle {
	private IntBuffer myVertexBuffer;
	private IntBuffer myColorBuffer;
	private ByteBuffer myIndexBuffer;
	int vCount;
	int iCount;
	float yAngle;
	float zAngle;
	
	public Triangle(){
		final int UNIT_SIZE=10000/2;
		int[] vertices={
			-4*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
			-4*UNIT_SIZE,-4*UNIT_SIZE,-4*UNIT_SIZE,
			4*UNIT_SIZE,-4*UNIT_SIZE,-4*UNIT_SIZE,
			4*UNIT_SIZE,4*UNIT_SIZE,-4*UNIT_SIZE,
			-4*UNIT_SIZE,4*UNIT_SIZE,4*UNIT_SIZE,
			-4*UNIT_SIZE,-4*UNIT_SIZE,4*UNIT_SIZE,
			4*UNIT_SIZE,-4*UNIT_SIZE,4*UNIT_SIZE,
			4*UNIT_SIZE,4*UNIT_SIZE,4*UNIT_SIZE,
			0,0,0
		};
		vCount=vertices.length/3;
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);
		vbb.order(ByteOrder.nativeOrder());
		myVertexBuffer=vbb.asIntBuffer();
		myVertexBuffer.put(vertices);
		myVertexBuffer.position(0);
		final int one=65535;
		int[] colors={
			
			one,one,0,0,
			0,one,one,0,
			one,0,one,0,
			one,one,one,0,
			one,one,one,0,
			one,one,0,0,
			0,one,one,0,
			one,0,one,0,
			/*
			one/2,one/2,one/2,0,
			one/2,one/2,one/2,0,
			one/2,one/2,one/2,0,
			one/2,one/2,one/2,0,
			one/2,one/2,one/2,0,
			one/2,one/2,one/2,0,
			one/2,one/2,one/2,0,
			one/2,one/2,one/2,0,*/
		};
		ByteBuffer cbb=ByteBuffer.allocateDirect(colors.length*4);
		cbb.order(ByteOrder.nativeOrder());
		myColorBuffer=cbb.asIntBuffer();
		myColorBuffer.put(colors);
		myColorBuffer.position(0);
		byte[] indices={
			0,2,1,0,3,2,
			5,6,7,5,7,4,
			0,5,4,0,1,5,
			2,3,7,2,7,6,
			0,4,7,0,7,3,
			5,1,2,5,2,6
		};
		iCount=indices.length;
		myIndexBuffer=ByteBuffer.allocateDirect(indices.length);
		myIndexBuffer.order(ByteOrder.nativeOrder());
		myIndexBuffer.put(indices);
		myIndexBuffer.position(0);
	}
	
	public void draw(GL10 gl){
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glRotatef(yAngle,0,1,0);
		gl.glRotatef(zAngle,0,0,1);
		gl.glVertexPointer(
			3,
			GL10.GL_FIXED,
			0,
			myVertexBuffer
		);
		gl.glColorPointer(
			4,
			GL10.GL_FIXED,
			0,
			myColorBuffer
		);
		gl.glDrawElements(
			GL10.GL_TRIANGLES,
			iCount,
			GL10.GL_UNSIGNED_BYTE,
			myIndexBuffer
		);
	}
}
