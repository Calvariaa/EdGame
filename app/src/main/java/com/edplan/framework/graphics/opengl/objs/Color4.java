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
	
	public Color4 multiple(float fr,float fg,float fb,float fa){
		this.r*=fr;
		this.g*=fg;
		this.b*=fb;
		this.a*=fa;
		return this;
	}
	
	public Color4 multiple(float f){
		return multiple(f,f,f,f);
	}
	
	public Color4 add(Color4 c){
		return add(c.r,c.g,c.b,c.a);
	}
	
	public Color4 add(float ra,float ga,float ba,float aa){
		return this.addRed(ra).addGreen(ga).addBlue(ba).addAlpha(aa);
	}
	
	public Color4 addRed(float a){
		this.r+=a;
		return this;
	}
	
	public Color4 addGreen(float a){
		this.g+=a;
		return this;
	}
	
	public Color4 addBlue(float a){
		this.b+=a;
		return this;
	}
	
	public Color4 addAlpha(float a){
		this.a+=a;
		return this;
	}
	
	public Color4 copyNew(){
		return new Color4(this);
	}
	
	public static Color4 mix(Color4 c1,Color4 c2,float fac){
		return c1.copyNew().multiple(1-fac).add(c2.copyNew().multiple(fac));
	}
	
	public static Color4 mixByAlpha(Color4 dst,Color4 rsc){
		return mix(rsc,dst,dst.a);
	}

}
