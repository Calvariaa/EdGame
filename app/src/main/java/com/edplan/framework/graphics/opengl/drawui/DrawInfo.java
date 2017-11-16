package com.edplan.framework.graphics.opengl.drawui;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.graphics.opengl.objs.Color4;

public abstract class DrawInfo
{
	
	public abstract void toLayerPosition(Vec2 v);
	
	public abstract Color4 getMaskColor(Vec2 position);
	
}
