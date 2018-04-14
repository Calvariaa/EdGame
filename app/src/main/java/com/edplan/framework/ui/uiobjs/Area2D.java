package com.edplan.framework.ui.uiobjs;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.math.RectF;

public interface Area2D
{
	public boolean inArea(Vec2 v);
	
	public void fixRect(float l,float t,float r,float b);
	
	public RectF boundRect();
}
