package com.edplan.framework.input.manager;
import com.edplan.framework.math.Area2D;
import com.edplan.framework.ui.inputs.EdMotionEvent;

public interface IMotionEventHandler
{
	public boolean handlerMotionEvent(EdMotionEvent event);
	
	public Area2D getArea();
}
