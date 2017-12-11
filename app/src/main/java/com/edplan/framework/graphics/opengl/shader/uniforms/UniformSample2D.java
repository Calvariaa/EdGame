package com.edplan.framework.graphics.opengl.shader.uniforms;
import com.edplan.framework.graphics.opengl.shader.DataUniform;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.graphics.opengl.shader.GLProgram;
import android.opengl.GLES20;

public class UniformSample2D implements DataUniform<GLTexture>
{
	private int textureIndex;
	
	private int handle;
	
	private GLProgram program;
	
	@Override
	public void loadData(GLTexture t) {
		// TODO: Implement this method
		t.bind(textureIndex);
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
	
	public static UniformSample2D findUniform(GLProgram program,String name,int index){
		UniformSample2D u=new UniformSample2D();
		u.program=program;
		u.textureIndex=index;
		u.handle=GLES20.glGetUniformLocation(program.getProgramId(),name);
		return u;
	}
}
