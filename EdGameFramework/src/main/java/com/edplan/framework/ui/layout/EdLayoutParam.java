package com.edplan.framework.ui.layout;

public class EdLayoutParam
{
	public static final long DEFAULT_SIZE_PARAM;
	
	static{
		DEFAULT_SIZE_PARAM=Param.makeupParam(0,Param.WRAP_CONTENT);
	}
	
	
	public long width;
	public long height;
	
	public EdLayoutParam(){
		
	}
	
	public EdLayoutParam(EdLayoutParam l){
		width=l.width;
		height=l.height;
	}
}
