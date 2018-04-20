package com.edplan.nso.inputs;

import com.edplan.framework.inputs.MAction;

public class OsuAction implements MAction
{
	public enum Type{
		Cursor,Key
	}
	
	//private Type type;
	
	private float x=0;
	
	private float y=0;
	
	private int pointerId=-1;
	
	private int keyCode=0;
	
	private int action=0;
}
