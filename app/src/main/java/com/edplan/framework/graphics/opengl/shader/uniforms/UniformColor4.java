package com.edplan.framework.graphics.opengl.shader.uniforms;

import android.opengl.GLES20;
import com.edplan.framework.graphics.opengl.GLException;
import com.edplan.framework.graphics.opengl.objs.Color4;
import com.edplan.framework.graphics.opengl.shader.DataUniform;
import com.edplan.framework.graphics.opengl.shader.GLProgram;

public class UniformColor4  implements DataUniform<Color4>
{
	private String name;

	private int handle;

	private GLProgram program;

	public UniformColor4(){

	}

	@Override
	public void loadData(Color4 t) {
		// TODO: Implement this method
		GLES20.glUniform4f(getHandle(),t.r,t.g,t.b,t.a);
	}

	@Override
	public int getHandle() {
		// TODO: Implement this method
		return handle;
	}

	@Override
	public GLProgram getProgram() {
		// TODO: Implement this method
		return program;
	}

	public static UniformColor4 findUniform(GLProgram program,String name){
		UniformColor4 um=new UniformColor4();
		um.handle=GLES20.glGetUniformLocation(program.getProgramId(),name);
		um.program=program;
		um.name=name;
		if(um.handle==-1)throw new GLException("handle "+name+" NOT found");
		return um;
	}
}
