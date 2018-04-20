package com.edplan.framework.graphics.opengl.shader;

public interface DataUniform<T>
{
	public void loadData(T t);
	
	public int getHandle();
	
	public GLProgram getProgram();
}
