package com.edplan.framework.graphics.opengl.drawui;


/**
 *一个抽象类，描述如何在一个Layer上进行绘制
 */
public abstract class GLDrawable
{
	public abstract void prepareForDraw();
	
	public abstract void render(DrawInfo info);
}
