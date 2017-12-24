package com.edplan.opengl;
import java.util.List;
import java.util.ArrayList;

public class GlBuffer
{
	private int bpp=3;
	private List<Float> dataList;
	
	public GlBuffer(List<Float> d){
		dataList=d;
	}
	
	public GlBuffer(int buf){
		this(new ArrayList<Float>(buf));
	}
	
	public GlBuffer(){
		this(0);
	}
	
	public GlBuffer add2(GlVec2 v){
		dataList.add(v.x);
		dataList.add(v.y);
		return this;
	}
	
	public GlBuffer add2(float x,float y){
		return add2(new GlVec2(x,y));
	}
	
	public GlBuffer add3(GlVec2 v){
		return add3(v.x,v.y,0f);
	}
	
	public GlBuffer add3(GlVec2 v,float d){
		return add3(v.x,v.y,d);
	}
	
	public GlBuffer add3(float x,float y,float z){
		dataList.add(x);
		dataList.add(y);
		dataList.add(z);
		if(bpp==4)dataList.add(0f);
		return this;
	}
	
	public GlBuffer add4(float r,float g,float b,float a){
		dataList.add(r);
		dataList.add(g);
		dataList.add(b);
		dataList.add(a);
		return this;
	}
	
	public GlBuffer add4(GlVec4 v){
		return add4(v.r,v.g,v.b,v.a);
	}
	
	public float[] toArray(){
		float[] l=new float[dataList.size()];
		for(int i=0;i<dataList.size();i++){
			l[i]=dataList.get(i);
		}
		return l;
	}
}
