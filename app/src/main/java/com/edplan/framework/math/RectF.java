package com.edplan.framework.math;

public class RectF
{
	private Vec2 basePoint=new Vec2();
	
	private float width;
	
	private float height;
	
	public RectF(){
		
	}
	
	public RectF(float l,float t,float w,float h){
		setBasePoint(l,t);
		setWidth(w);
		setHeight(h);
	}
	
	public Vec2 getBasePoint(){
		return basePoint;
	}
	
	public void setBasePoint(float x,float y){
		getBasePoint().set(x,y);
	}

	public Vec2 getPoint(float x,float y){
		return basePoint.copy().add(x*width,y*height);
	}
	
	public float getX1(){
		return basePoint.x;
	}
	
	public float getX2(){
		return basePoint.x+width;
	}
	
	public float getY1(){
		return basePoint.y;
	}
	
	public float getY2(){
		return basePoint.y+width;
	}
	
	public float getTop(){
		return basePoint.y;
	}
	
	public float getLeft(){
		return basePoint.x;
	}
	
	public float getBottom(){
		return getTop()+getWidth();
	}
	
	public float getRight(){
		return getLeft()+getWidth();
	}
	
	public void setWidth(float width) {
		this.width=width;
	}

	public float getWidth() {
		return width;
	}

	public void setHeight(float height) {
		this.height=height;
	}

	public float getHeight() {
		return height;
	}
	
	public void move(float dx,float dy){
		getBasePoint().add(dx,dy);
	}
}
