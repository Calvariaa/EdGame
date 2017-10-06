package com.edplan.simpleGame.math;

public class PointF
{
	public static final PointF BASE_POINT=new PointF(0,0);
	
	public float x,y;
	
	public PointF(float x,float y){
		this.x=x;
		this.y=y;
	}
	
	public PointF(PointF res){
		this.x=res.x;
		this.y=res.y;
	}
	
	public PointF set(float x,float y){
		this.x=x;
		this.y=y;
		return this;
	}
	
	public PointF move(float offsetX,float offsetY){
		x+=offsetX;
		y+=offsetY;
		
		return this;
	}
	
	public PointF zoom(float ox,float oy,float zoomTimesX,float zoomTimesY){
		this.x=zoomTimesX*(x-ox)+ox;
		this.y=zoomTimesY*(y-oy)+oy;
		return this;
	}
	
	public PointF zoom(PointF o,float zoomTimesX,float zoomTimesY){
		return zoom(o.x,o.y,zoomTimesX,zoomTimesY);
	}
	
	//顺时针，弧度
	public PointF rotate(PointF o,float r){
		float c=(float)Math.cos(r);
		float s=(float)Math.sin(r);
		float xr=x-o.x;
		float yr=y-o.y;
		x=o.x+xr*c-yr*s;
		y=o.y+yr*c+xr*s;
		return this;
	}
	
	public PointF copy(){
		return new PointF(this);
	}
	
	public float distance(){
		return distance(x,y);
	}
	
	public static float distance(float x,float y){
		return (float)Math.sqrt(x*x+y*y);
	}
	
	public static float distance(PointF p1,PointF p2){
		return distance(p1.x-p2.x,p1.y-p2.y);
	}
}
