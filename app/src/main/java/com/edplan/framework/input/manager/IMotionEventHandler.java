package com.edplan.framework.input.manager;
import com.edplan.framework.input.EdMotionEvent;
import com.edplan.framework.ui.uiobjs.Area2D;

public interface IMotionEventHandler
{
	public boolean handlerMotionEvent(EdMotionEvent event);
	
	public Area2D getArea();
}
