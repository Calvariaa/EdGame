package com.edplan.framework.ui;
import com.edplan.framework.math.Vec2;

public class Anchor
{
	public static final Anchor TopLeft=new Anchor(0,0);
	public static final Anchor TopCenter=new Anchor(0.5f,0);
	public static final Anchor TopRight=new Anchor(1,0);
	public static final Anchor CenterLeft=new Anchor(0,0.5f);
	public static final Anchor Center=new Anchor(0.5f,0.5f);
	public static final Anchor CenterRight=new Anchor(1,0.5f);
	public static final Anchor BottomLeft=new Anchor(0,1);
	public static final Anchor BottomCenter=new Anchor(0.5f,1);
	public static final Anchor BottomRight=new Anchor(1,1);
	
	
	private Vec2 value;
	
	public Anchor(float x,float y){
		value=new Vec2(x,y);
	}
	
	public float x(){
		return value.x;
	}
	
	public float y(){
		return value.y;
	}
}
