package com.edplan.opengl;

public class GlVec2
{
	public float x=0,y=0;
	
	public GlVec2(){
		
	}
	
	public GlVec2(float x,float y){
		this.x=x;
		this.y=y;
	}
	
	public GlVec2 minus(GlVec2 v){
		x-=v.x;
		y-=v.y;
		return this;
	}
	
	public GlVec2 normalize(){
		return divide(length());
	}
	
	public float length(){
		return (float)Math.hypot(x,y);
	}
	
	public GlVec2 add(GlVec2 v){
		x+=v.x;
		y+=v.y;
		return this;
	}
	
	public GlVec2 add(float ax,float ay){
		x+=ax;
		y+=ay;
		return this;
	}
	
	public GlVec2 divide(GlVec2 v){
		x/=v.x;
		y/=v.y;
		return this;
	}
	
	public GlVec2 divide(float d){
		x/=d;
		y/=d;
		return this;
	}
	
	public GlVec2 multiple(float m){
		x*=m;
		y*=m;
		return this;
	}
	
	public GlVec2 multiple(float mx,float my){
		x*=mx;
		y*=my;
		return this;
	}
	
	public GlVec2 multiple(GlVec2 v){
		return multiple(v.x,v.y);
	}
	
	public float dot(GlVec2 v){
		return x*v.x+y*v.y;
	}
	
	public GlVec2 postMat(GlMat2 m){
		float tmp=x;
		x=x*m.d[0][0]+y*m.d[0][1];
		y=tmp*m.d[1][0]+y*m.d[1][1];
		return this;
	}
	
	public GlVec2 rotate(float dgr){
		float tmp=x;
		float cos=(float)Math.cos(dgr);
		float sin=(float)Math.sin(dgr);
		x=x*cos-y*sin;
		y=tmp*sin+y*cos;
		return this;
	}
	
	public GlVec2 copyNew(){
		return new GlVec2(x,y);
	}
	
}
