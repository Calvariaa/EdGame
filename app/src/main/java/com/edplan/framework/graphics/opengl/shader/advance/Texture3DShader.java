package com.edplan.framework.graphics.opengl.shader.advance;
import com.edplan.framework.graphics.opengl.shader.GLProgram;
import com.edplan.framework.graphics.opengl.shader.GLShader;
import com.edplan.framework.graphics.opengl.shader.advance.interfaces.ITexturedVertex3DShader;
import com.edplan.framework.graphics.batch.Texture3DBatch;

public class Texture3DShader extends GLProgram implements ITexturedVertex3DShader
{
	protected Texture3DShader(GLProgram program){
		super(program.getVertexShader(),program.getFragmentShader(),program.getProgramId());
	}
	
	@Override
	public void loadBatch(Texture3DBatch batch) {
		// TODO: Implement this method
	}
	
	public static Texture3DShader create(){
		return null;
	}
}
