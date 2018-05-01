package com.edplan.framework.graphics.opengl.shader;

import android.opengl.GLES20;
import com.edplan.framework.graphics.opengl.GLException;
import com.edplan.framework.graphics.opengl.buffer.Color4Buffer;
import com.edplan.framework.graphics.opengl.buffer.Vec2Buffer;
import com.edplan.framework.graphics.opengl.buffer.Vec3Buffer;
import java.nio.Buffer;
import com.edplan.framework.utils.MLog;
import android.util.Log;
import com.edplan.framework.test.performance.Tracker;

public class VertexAttrib 
{
	public enum Type{
		VEC2(2,2*4,GLES20.GL_FLOAT),
		VEC3(3,3*4,GLES20.GL_FLOAT),
		VEC4(4,4*4,GLES20.GL_FLOAT);
		
		private final int dataCount;
		
		private final int step;
		
		private final int dataType;
		
		Type(int dc,int st,int dt){
			dataCount=dc;
			step=st;
			dataType=dt;
		}
	}
	
	private int handle;
	
	private Type type;
	
	private String name;
	
	private GLProgram program;

	VertexAttrib(){
		
	}
	
	public int getHandle() {
		return handle;
	}
	
	public Type getType(){
		return type;
	}
	
	public GLProgram getProgram(){
		return program;
	}
	
	public String getName(){
		return name;
	}
	
	public void loadData(Buffer datas){
		if(getHandle()==-1)return;
		//Tracker.InjectData.watch();
		GLES20.glVertexAttribPointer(
			getHandle(),   
			getType().dataCount, 
			getType().dataType, 
			false,
			getType().step,   
			datas
		);
		GLES20.glEnableVertexAttribArray(getHandle());
		//Tracker.InjectData.end();
	}
	
	public void loadData(Vec2Buffer buffer){
		if(getType()!=Type.VEC2){
			throw new GLException("only Type.VEC2 can use loadData(Vec2Buffer)");
		}else{
			loadData(buffer.makeBuffer());
		}
	}
	
	public void loadData(Vec3Buffer buffer){
		if(getType()!=Type.VEC3){
			throw new GLException("only Type.VEC3 can use loadData(Vec3Buffer)");
		}else{
			loadData(buffer.makeBuffer());
		}
	}
	
	public void loadData(Color4Buffer buffer){
		if(getType()!=Type.VEC4){
			throw new GLException("only Type.VEC4 can use loadData(Color4Buffer)");
		}else{
			loadData(buffer.makeBuffer());
		}
	}
	
	public static VertexAttrib findAttrib(GLProgram program,String name,Type type){
		VertexAttrib va=new VertexAttrib();
		va.handle=GLES20.glGetAttribLocation(program.getProgramId(),name);
		va.type=type;
		va.program=program;
		return va;
	}
}
