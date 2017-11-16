package com.edplan.framework.graphics.opengl.objs;

public class Color4
{
	public float r,g,b,a;

	public Color4(){

	}

	public Color4(float ar,float ag,float ab,float aa){
		set(ar,ag,ab,aa);
	}

	public Color4(Color4 c){
		set(c.r,c.g,c.b,c.a);
	}

	public Color4 set(float r,float g,float b,float a){
		this.r=r;
		this.g=g;
		this.b=b;
		this.a=a;
		return this;
	}

	public Color4 copyNew(){
		return new Color4(this);
	}
}
