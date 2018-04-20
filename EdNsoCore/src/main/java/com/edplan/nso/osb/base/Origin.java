package com.edplan.nso.osb.base;

public enum Origin
{
	TopLeft(0,0),
	TopCenter(0.5f,0),
	TopRight(1,0),
	CentreLeft(0,0.5f),
	Centre(0.5f,0.5f),
	CentreRight(1,0.5f),
	BottomLeft(0,1),
	BottomCentre(0.5f,1),
	BottomRight(1,1);
	
	public final float x;
	
	public final float y;
	
	Origin(float x,float y){
		this.x=x;
		this.y=y;
	}
	
	public static Origin fromName(String n){
		
		return valueOf(n);
	}
}
