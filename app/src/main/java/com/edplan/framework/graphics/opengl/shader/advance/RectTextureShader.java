package com.edplan.framework.graphics.opengl.shader.advance;
import com.edplan.framework.graphics.opengl.shader.GLProgram;
import com.edplan.framework.graphics.opengl.shader.VertexAttrib;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformFloat;
import com.edplan.framework.graphics.opengl.shader.Unif;
import com.edplan.framework.graphics.opengl.shader.Attr;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformMat2;
import com.edplan.framework.math.RectF;
import com.edplan.framework.graphics.opengl.buffer.Vec2Buffer;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformColor4;
import com.edplan.framework.math.Vec4;

public class RectTextureShader extends Texture3DShader
{
	private VertexAttrib vRectPosition;
	
	private UniformColor4 uPadding;
	
	private UniformColor4 uInnerRect;
	
	private UniformColor4 uDrawingRect;
	
	protected RectTextureShader(GLProgram p){
		super(p);
		uPadding=UniformColor4.findUniform(this,Unif.Padding);
		uInnerRect=UniformColor4.findUniform(this,Unif.InnerRect);
		uDrawingRect=UniformColor4.findUniform(this,Unif.DrawingRect);
		vRectPosition=VertexAttrib.findAttrib(this,Attr.RectPosition,VertexAttrib.Type.VEC2);
	}
	
	public void loadRectData(RectF drawingRect,Vec4 padding){
		uPadding.loadData(padding);
		uDrawingRect.loadRect(drawingRect);
		uInnerRect.loadRect(drawingRect,padding);
	}
	
	public void loadRectPositions(Vec2Buffer buffer){
		vRectPosition.loadData(buffer);
	}
	
	public static RectTextureShader create(String vs,String fs){
		return new RectTextureShader(GLProgram.createProgram(vs,fs));
	}
}
