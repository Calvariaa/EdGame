package com.edplan.framework.inputs;

public interface BaseInputsHandler
{
	public boolean onMove(float x,float y,int pointer) ;
	
	public boolean onDown(float x,float y,int pointer);
	
	public boolean onUp(float x,float y,int pointer);
	
	public boolean onKeyDown(int keyCode);
	
	public boolean onKeyUp(int keyCode);
}
