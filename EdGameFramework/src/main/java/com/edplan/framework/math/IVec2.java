package com.edplan.framework.math;

public abstract class IVec2
{
	public abstract float x();
	
	public abstract float y();
	
	public abstract void set(float x,float y);
	
	public abstract IVec2 add(float ax,float ay);
	
	public abstract IVec2 divide(float dx,float dy);

	public abstract IVec2 multiple(float mx,float my);
	
	public abstract IVec2 copy();
	
	public IVec2 add(IVec2 v){
		return add(v.x(),v.y());
	}

	public IVec2 minus(IVec2 v){
		return minus(v.x(),v.y());
	}

	public IVec2 minus(float dx,float dy){
		return add(-dx,-dy);
	}

	public IVec2 move(float offsetX,float offsetY){
		return add(offsetX,offsetY);
	}

	public IVec2 multiple(IVec2 v){
		return multiple(v.x(),v.y());
	}

	public IVec2 divide(float d){
		return divide(d,d);
	}

	public IVec2 zoom(float zt){
		return multiple(zt,zt);
	}

	public IVec2 zoom(float ox,float oy,float zoomTimesX,float zoomTimesY){
		set(zoomTimesX*(x()-ox)+ox,zoomTimesY*(y()-oy)+oy);
		return this;
	}

	public IVec2 zoom(IVec2 o,float zoomTimesX,float zoomTimesY){
		return zoom(o.x(),o.y(),zoomTimesX,zoomTimesY);
	}

	public IVec2 rotate(float r){
		float c=(float)Math.cos(r);
		float s=(float)Math.sin(r);
		set(x()*c-y()*s,y()*c+x()*s);
		return this;
	}

	//顺时针，弧度
	public IVec2 rotate(IVec2 o,float r){
		return rotate(o.x(),o.y(),r);
	}

	public IVec2 rotate(float ox,float oy,float r){
		float c=FMath.cos(r);
		float s=FMath.sin(r);
		float xr=x()-ox;
		float yr=y()-oy;
		set(ox+xr*c-yr*s,oy+yr*c+xr*s);
		return this;
	}


	public IVec2 postMatrix(Mat2 m){
		set(
			x()*m.get(0,0)+y()*m.get(1,0),
			x()*m.get(1,0)+y()*m.get(1,1)
		);
		return this;
	}

	public IVec2 toNormal(){
		return divide(length());
	}

	public IVec2 toOrthogonalDirectionNormal(){
		return toNormal().rotate(FMath.PiHalf);
	}

	public float dot(IVec2 v){
		return this.x()*v.x()+this.y()*v.y();
	}

	public float length(){
		return length(x(),y());
	}

	public float lengthSquared(){
		return lengthSquared(x(),y());
	}

	public float theta(){
		return FMath.atan2(y(),x());
	}
	
	public static float lengthSquared(float x,float y){
		return x*x+y*y;
	}

	public static float lengthSquared(Vec2 v1,Vec2 v2){
		return lengthSquared(v1.x-v2.x,v1.y-v2.y);
	}

	public static float length(float x,float y){
		return (float)Math.sqrt(lengthSquared(x,y));
	}

	public static float length(Vec2 p1,Vec2 p2){
		return length(p1.x-p2.x,p1.y-p2.y);
	}
}
