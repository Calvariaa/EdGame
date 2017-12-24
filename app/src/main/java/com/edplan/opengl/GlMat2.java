package com.edplan.opengl;

public class GlMat2
{
	public float[][] d;
	
	public GlMat2(){
		d= new float[2][2];
	}
	
	public GlMat2(float p11,float p12,float p21,float p22){
		d=new float[][]{{p11,p12},{p21,p22}}; 
	}
	
	public GlMat2 copyNew(){
		return new GlMat2(d[0][0],d[0][1],d[1][0],d[1][1]);
	}
	
}
