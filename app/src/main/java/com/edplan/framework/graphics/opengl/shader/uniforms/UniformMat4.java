package com.edplan.framework.graphics.opengl.shader.uniforms;
import com.edplan.framework.graphics.opengl.shader.DataUniform;
import com.edplan.framework.graphics.opengl.shader.GLProgram;
import com.edplan.framework.math.Mat4;
import android.opengl.GLES20;

public class UniformMat4 implements DataUniform<Mat4>
{
	private int handle;
	
	private GLProgram program;

	@Override
	public void loadData(Mat4 t) {
		// TODO: Implement this method
		GLES20.glUniformMatrix4fv(getHandle(),1,false,t.data,0);
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
	
	public static UniformMat4 findUniform(GLProgram program,String name){
		UniformMat4 um=new UniformMat4();
		um.handle=GLES20.glGetUniformLocation(program.getProgramId(),name);
		um.program=program;
		return um;
	}
}
