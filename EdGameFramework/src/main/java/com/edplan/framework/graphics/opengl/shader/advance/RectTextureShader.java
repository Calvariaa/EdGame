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
import com.edplan.framework.graphics.opengl.objs.TextureVertex3D;
import com.edplan.framework.graphics.opengl.batch.Texture3DBatch;
import com.edplan.framework.graphics.opengl.batch.RectVertexBatch;
import com.edplan.framework.graphics.opengl.batch.BaseBatch;
import com.edplan.framework.graphics.opengl.batch.base.IHasRectPosition;
import java.nio.FloatBuffer;

public class RectTextureShader extends Texture3DShader
{
	private VertexAttrib vRectPosition;
	
	private UniformColor4 uPadding;
	
	private UniformColor4 uInnerRect;
	
	private UniformColor4 uDrawingRect;
	
	protected RectTextureShader(GLProgram p){
		super(p);
	}

	@Override
	public void setUp() {
		// TODO: Implement this method
		super.setUp();
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
	
	public void loadRectPositions(FloatBuffer buffer){
		vRectPosition.loadData(buffer);
	}

	@Override
	public boolean loadBatch(BaseBatch batch) {
		// TODO: Implement this method
		if(super.loadBatch(batch)){
			if(batch instanceof IHasRectPosition){
				loadRectPositions(((IHasRectPosition)batch).makeRectPositionBuffer());
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public static final RectTextureShader createRTS(String vs,String fs){
		RectTextureShader s=new RectTextureShader(GLProgram.createProgram(vs,fs));
		s.setUp();
		return s;
	}
}
