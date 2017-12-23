package com.edplan.framework.graphics.opengl.shader.uniforms;
import android.opengl.GLES20;
import android.util.Log;
import com.edplan.framework.graphics.opengl.GLException;
import com.edplan.framework.graphics.opengl.objs.GLTexture;
import com.edplan.framework.graphics.opengl.shader.DataUniform;
import com.edplan.framework.graphics.opengl.shader.GLProgram;

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
		if(u.handle==-1){
			throw new GLException("handle "+name+" NOT found");
		}
		return u;
	}
}
