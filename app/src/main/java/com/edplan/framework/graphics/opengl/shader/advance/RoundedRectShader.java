package com.edplan.framework.graphics.opengl.shader.advance;
import com.edplan.framework.graphics.opengl.shader.GLProgram;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformFloat;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformMat2;
import com.edplan.framework.graphics.opengl.shader.Unif;
import com.edplan.framework.math.RectF;
import com.edplan.framework.graphics.opengl.shader.uniforms.UniformColor4;
import com.edplan.framework.graphics.opengl.objs.Color4;

public class RoundedRectShader extends RectShader
{
	public static final float DEFAULT_GLOW_FACTOR=0.5f;
	
	private UniformFloat uRoundedRidaus;
	
	private UniformColor4 uRoundedInner;
	
	private UniformFloat uGlowFactor;
	
	private UniformColor4 uGlowColor;
	
	protected RoundedRectShader(GLProgram p){
		super(p);
		uRoundedRidaus=UniformFloat.findUniform(this,Unif.RoundedRidaus);
		uRoundedInner=UniformColor4.findUniform(this,Unif.RoundedInner);
		uGlowFactor=UniformFloat.findUniform(this,Unif.GlowFactor);
		uGlowColor=UniformColor4.findUniform(this,Unif.GlowColor);
	}
	
	public void loadRectData(RectF rect,float padding,float radius,Color4 glowColor,float glowFactor){
		super.loadRectData(rect,padding);
		uRoundedRidaus.loadData(radius);
		uRoundedInner.loadRect(rect,padding+radius);
		uGlowFactor.loadData(glowFactor);
		uGlowColor.loadData(glowColor);
	}

	@Override
	public void loadRectData(RectF drawingRect,float padding) {
		// TODO: Implement this method
		loadRectData(drawingRect, padding,0,new Color4(0,0,0,0),DEFAULT_GLOW_FACTOR);
	}
	
	public static RoundedRectShader create(String vs,String fs){
		return new RoundedRectShader(GLProgram.createProgram(vs,fs));
	}
}
