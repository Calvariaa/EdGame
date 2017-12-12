package com.edplan.framework.graphics.opengl.shader.uniforms;
import com.edplan.framework.graphics.opengl.shader.DataUniform;
import com.edplan.framework.graphics.opengl.shader.GLProgram;
import android.opengl.GLES20;
import com.edplan.framework.math.Mat2;

public class UniformMat2 implements DataUniform<Mat2>
{
	private String name;
	
	private int handle;
	
	private GLProgram program;
	
	public UniformMat2(String name,GLProgram program){
		this.program=program;
		this.name=name;
		
	}
	
	@Override
	public void loadData(Mat2 mat){
		GLES20.glUniformMatrix2fv(getHandle(),1,false,mat.data,0);
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
}