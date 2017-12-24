package com.edplan.framework.math;
import java.util.List;

public class Vec2
{
	public static final Vec2 BASE_POINT=new Vec2(0,0);
	
	public float x,y;
	
	public Vec2(){
		
	}
	
	public Vec2(float x,float y){
		this.x=x;
		this.y=y;
	}
	
	public Vec2(Vec2 res){
		this.x=res.x;
		this.y=res.y;
	}
	
	public Vec2 set(float x,float y){
		this.x=x;
		this.y=y;
		return this;
	}
	
	public Vec2 add(Vec2 v){
		return add(v.x,v.y);
	}

	public Vec2 add(float ax,float ay){
		this.x+=ax;
		this.y+=ay;
		return this;
	}

	public Vec2 minus(Vec2 v){
		return minus(v.x,v.y);
	}

	public Vec2 minus(float dx,float dy){
		this.x-=dx;
		this.y-=dy;
		return this;
	}
	
	public Vec2 move(float offsetX,float offsetY){
		x+=offsetX;
		y+=offsetY;
		
		return this;
	}
	
	public Vec2 divide(float dx,float dy){
		this.x/=dx;
		this.y/=dy;
		return this;
	}
	
	public Vec2 divide(float d){
		return divide(d,d);
	}
	
	public Vec2 zoom(float zt){
		return zoom(0,0,zt,zt);
	}
	
	public Vec2 zoom(float ox,float oy,float zoomTimesX,float zoomTimesY){
		this.x=zoomTimesX*(x-ox)+ox;
		this.y=zoomTimesY*(y-oy)+oy;
		return this;
	}
	
	public Vec2 zoom(Vec2 o,float zoomTimesX,float zoomTimesY){
		return zoom(o.x,o.y,zoomTimesX,zoomTimesY);
	}
	
	public Vec2 rotate(float r){
		float c=(float)Math.cos(r);
		float s=(float)Math.sin(r);
		float tmpX=x;
		x=x*c-y*s;
		y=y*c+tmpX*s;
		return this;
	}
	
	//顺时针，弧度
	public Vec2 rotate(Vec2 o,float r){
		float c=(float)Math.cos(r);
		float s=(float)Math.sin(r);
		float xr=x-o.x;
		float yr=y-o.y;
		x=o.x+xr*c-yr*s;
		y=o.y+yr*c+xr*s;
		return this;
	}
	
	public Vec2 postMatrix(Mat2 m){
		float tmpX=x;
		x=x*m.get(0,0)+y*m.get(1,0);
		y=tmpX*m.get(1,0)+y*m.get(1,1);
		return this;
	}
	
	public Vec2 toNormal(){
		return divide(length());
	}
	
	public Vec2 toOrthogonalDirectionNormal(){
		return toNormal().rotate(FMath.PiHalf);
	}
	
	public float dot(Vec2 v){
		return this.x*v.x+this.y*v.y;
	}
	
	public float length(){
		return length(x,y);
	}
	
	public float lengthSquared(){
		return lengthSquared(x,y);
	}
	
	public float theta(){
		return FMath.atan2(y,x);
	}
	
	//public float thetaX(){
	//	return -theta();
	//}
	
	public Vec2 copy(){
		return new Vec2(this);
	}
	
	public Vec3 toVec3(float z){
		return new Vec3(this,z);
	}
	
	public static Vec2 lineOthNormal(Vec2 ps,Vec2 pe){
		Vec2 v=pe.copy().minus(ps).toOrthogonalDirectionNormal();
		return v;
	}
	
	public static float lengthSquared(float x,float y){
		return x*x+y*y;
	}
	
	public static float length(float x,float y){
		return (float)Math.sqrt(lengthSquared(x,y));
	}
	
	public static float length(Vec2 p1,Vec2 p2){
		return length(p1.x-p2.x,p1.y-p2.y);
	}
	
	public static Vec2 atCircle(float ang){
		return new Vec2((float)Math.cos(ang),(float)Math.sin(ang));
	}
	
	public static float calTheta(Vec2 start,Vec2 end){
		return FMath.atan2(end.y-start.y,end.x-start.x);
	}

	@Override
	public boolean equals(Object obj) {
		// TODO: Implement this method
		if(obj instanceof Vec2){
			Vec2 v=(Vec2)obj;
			return v.x==x&&v.y==y;
		}else{
			return false;
		}
	}

	@Override
	public String toString() {
		// TODO: Implement this method
		return (new StringBuilder("(")).append(x).append(",").append(y).append(")").toString();
	}
}
