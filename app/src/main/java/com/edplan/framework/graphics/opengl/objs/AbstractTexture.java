package com.edplan.framework.graphics.opengl.objs;
import com.edplan.framework.math.Vec2;

public abstract class AbstractTexture
{
	public abstract int getTextureId();
	
	public abstract GLTexture getTexture();
	
	public abstract int getHeight();
	
	public abstract int getWidth();
	
	public abstract Vec2 toTexturePosition(float x,float y);
}
