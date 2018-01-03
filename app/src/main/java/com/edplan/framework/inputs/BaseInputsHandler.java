package com.edplan.framework.inputs;

public interface BaseInputsHandler
{
	public boolean onMove(float x,float y,int pointer) default false ;
	
	public boolean onDown(float x,float y,int pointer) default false;
	
	public boolean onUp(float x,float y,int pointer) default false;
	
	public boolean onKeyDown(int keyCode) default false;
	
	public boolean onKeyUp(int keyCode) default false;
}
