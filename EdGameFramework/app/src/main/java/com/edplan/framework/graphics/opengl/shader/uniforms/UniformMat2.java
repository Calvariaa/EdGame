package com.edplan.framework.graphics.opengl.shader.uniforms;
import android.opengl.GLES20;
import com.edplan.framework.graphics.opengl.GLException;
import com.edplan.framework.graphics.opengl.shader.DataUniform;
import com.edplan.framework.graphics.opengl.shader.GLProgram;
import com.edplan.framework.math.Mat2;
import com.edplan.framework.math.RectF;

public class UniformMat2 implements DataUniform<Mat2>
{
	private String name;
	
	private int handle;
	
	private GLProgram program;
	
	@Override
	public void loadData(Mat2 mat){
		GLES20.glUniformMatrix2fv(getHandle(),1,false,mat.data,0);
	}
	
	public void loadData(RectF rect){
		GLES20.glUniformMatrix2fv(getHandle(),1,false,new float[]{rect.getX1(),rect.getY1(),rect.getX2(),rect.getY2()},0);
	}
	
	public void loadData(RectF rect,float padding){
		GLES20.glUniformMatrix2fv(getHandle(),1,false,new float[]{rect.getX1()+padding,rect.getY1()+padding,rect.getX2()-padding,rect.getY2()-padding},0);
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
	
	public static UniformMat2 findUniform(GLProgram program,String name){
		UniformMat2 um=new UniformMat2();
		um.handle=GLES20.glGetUniformLocation(program.getProgramId(),name);
		um.program=program;
		//if(um.handle==-1)throw new GLException("handle "+name+" NOT found");
		return um;
	}
}
