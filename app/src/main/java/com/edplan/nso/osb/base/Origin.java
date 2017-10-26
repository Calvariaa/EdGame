package com.edplan.nso.osb.base;

public enum Origin
{
	TopLeft(0,0),
	;
	public final float x;
	public final float y;
	public Origin(float x,float y){
		this.x=x;
		this.y=y;
	}
}
