package com.edplan.framework.graphics.opengl.objs;
import com.edplan.framework.math.Vec4;
import android.graphics.Color;

public class Color4
{
	public static final Color4 ONE=Color4.rgba(1,1,1,1);
	
	public static final Color4 White=Color4.rgb(1,1,1);
	
	public static final Color4 Alpha=Color4.rgba(0,0,0,0);
	
	public static final Color4 Black=Color4.rgb(0,0,0);
	
	public static final Color4 Blue=Color4.rgb(0,0,1);
	
	public float r,g,b,a;
	
	public boolean premultiple=false;

	public Color4(){

	}

	public Color4(float ar,float ag,float ab,float aa){
		set(ar,ag,ab,aa);
	}

	public Color4(Color4 c){
		set(c);
	}
	
	public Color4 set(Color4 c){
		set(c.r,c.g,c.b,c.a);
		premultiple=c.premultiple;
		return this;
	}

	public Color4 toPremultipled(){
		if(premultiple){
			return this.copyNew();
		}else{
			Color4 c=new Color4(r*a,g*a,b*a,a);
			c.premultiple=true;
			return c;
		}
	}

	public Color4 set(float r,float g,float b,float a){
		this.r=r;
		this.g=g;
		this.b=b;
		this.a=a;
		return this;
	}
	
	public Color4 multiple(Color4 c){
		return multiple(c.r,c.g,c.b,c.a);
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
	
	public Vec4 toVec4(){
		return new Vec4(this);
	}
	
	public int getA255(){
		return (int)(a*255);
	}
	
	public int getR255(){
		return (int)(r*255);
	}
	
	public int getG255(){
		return (int)(g*255);
	}
	
	public int getB255(){
		return (int)(b*255);
	}
	
	public int toIntBit(){
		return Color.argb(getA255(),getR255(),getG255(),getB255());
	}

	@Override
	public String toString() {
		// TODO: Implement this method
		return "(r,g,b,a)=("+r+","+g+","+b+","+a+")";
	}
	
	public static Color4 max(Color4 c1,Color4 c2){
		return new Color4(Math.max(c1.r,c2.r),Math.max(c1.g,c2.g),Math.max(c1.b,c2.b),Math.max(c1.a,c2.a));
	}
	
	public static Color4 min(Color4 c1,Color4 c2){
		return new Color4(Math.min(c1.r,c2.r),Math.min(c1.g,c2.g),Math.min(c1.b,c2.b),Math.min(c1.a,c2.a));
	}
	
	
	public static Color4 mix(Color4 c1,Color4 c2,float fac){
		return c1.copyNew().multiple(1-fac).add(c2.copyNew().multiple(fac));
	}
	
	public static Color4 mixByAlpha(Color4 dst,Color4 rsc){
		return mix(rsc,dst,dst.a);
	}
	
	public static Color4 rgb(float r,float g,float b){
		return rgba(r,g,b,1);
	}
	
	public static Color4 rgba(float r,float g,float b,float a){
		return new Color4(r,g,b,a);
	}

	public static Color4 rgba255(int r,int g,int b,int a){
		return new Color4(r/255f,g/255f,b/255f,a/255f);
	}
	
	public static Color4 argb255(int a,int r,int g,int b){
		return new Color4(r/255f,g/255f,b/255f,a/255f);
	}
	
	public static Color4 argb255(int v){
		return rgba255(Color.red(v),Color.green(v),Color.blue(v),Color.alpha(v));
	}
	
	public static Color4 gray(float g){
		return rgb(g,g,g);
	}
	
	public static Color4 alphaMultipler(float a){
		return Color4.rgba(1,1,1,a);
	}
}
