package com.edplan.framework.math;

public class FMath
{
	public static final float Pi=(float)Math.PI;
	
	public static final float Pi2=(float)(Math.PI*2);
	

	public static final float PiHalf=(float)(Math.PI*0.5);
	
	public static boolean inInterval(float x1,float x2,float t){
		return t>x1&&t<x2;
	}
	
	public static float sin(double v){
		return (float)Math.sin(v);
	}
	
	public static float cos(double v){
		return (float)Math.cos(v);
	}
	
	public static float tan(double v){
		return (float)Math.tan(v);
	}
	
	public static float atan2(double x,double y){
		return (float)Math.atan2(x,y);
	}
	
	public static boolean allmostEqual(float f1,float f2,float t){
		return Math.abs(f1-f2)<=t;
	}
	
	public static float sqrt(double v){
		return (float)Math.sqrt(v);
	}
	
	public static float max(float a,float b){
		return a>b?a:b;
	}
	
	public static float min(float a,float b){
		return a>b?b:a;
	}
	
	public static float clamp(float value,float max,float min){
		return min(max(value,min),max);
	}
	
	public static float linear(float progress,float bottom,float top){
		return bottom*progress+top*(1-progress);
	}
}
