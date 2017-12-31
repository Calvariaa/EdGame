package com.edplan.framework.graphics.opengl;
import com.edplan.framework.graphics.opengl.objs.Color4;

public class GLPaint
{
	public Color4 mixColor=new Color4(1,1,1,1);
	
	public Color4 varyingColor=new Color4(0,0,0,0);
	
	public float colorMixRate=0;
	
	public float finalAlpha=1;
	
	public float padding=0;
	
	public float roundedRadius=0;
	
	public float glowFactor=0.5f;
	
	public Color4 glowColor=new Color4(1, 1, 1, 0.2f);

	public void setRoundedRadius(float roundedRadius) {
		this.roundedRadius=roundedRadius;
	}

	public float getRoundedRadius() {
		return roundedRadius;
	}

	public void setGlowFactor(float glowFactor) {
		this.glowFactor=glowFactor;
	}

	public float getGlowFactor() {
		return glowFactor;
	}

	public void setGlowColor(Color4 glowColor) {
		this.glowColor=glowColor;
	}

	public Color4 getGlowColor() {
		return glowColor;
	}

	public void setPadding(float padding) {
		this.padding=padding;
	}

	public float getPadding() {
		return padding;
	}

	public void setMixColor(Color4 mixColor) {
		this.mixColor=mixColor;
	}

	public Color4 getMixColor() {
		return mixColor;
	}

	public void setVaryingColor(Color4 varyingColor) {
		this.varyingColor=varyingColor;
	}

	public Color4 getVaryingColor() {
		return varyingColor;
	}

	public void setColorMixRate(float colorMixRate) {
		this.colorMixRate=colorMixRate;
	}

	public float getColorMixRate() {
		return colorMixRate;
	}

	public void setFinalAlpha(float finalAlpha) {
		this.finalAlpha=finalAlpha;
	}

	public float getFinalAlpha() {
		return finalAlpha;
	}}
