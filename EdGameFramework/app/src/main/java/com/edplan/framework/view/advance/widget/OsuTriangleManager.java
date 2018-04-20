package com.edplan.framework.view.advance.widget;
import java.util.List;
import android.graphics.Canvas;
import java.util.Set;

public interface OsuTriangleManager
{
	public void setWidth(float w);
	
	public void setHeight(float h);
	
	public float getWidth();
	
	public float getHeight();
	
	public void add(OsuTriangle t);
	
	public void add();
	
	public void measure(Canvas c,int deltaTime);
	
	public Set<OsuTriangle> getTriangles();
}
