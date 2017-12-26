package com.edplan.framework.graphics.line;
import java.util.List;
import java.util.ArrayList;
import com.edplan.framework.math.Vec2;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import com.edplan.framework.math.FMath;

public class PathMeasurer
{
	private LinePath path;
	
	private List<Float> lengthes;
	
	private Vec2 endNormal;
	
	private Vec2 endPoint;
	
	public PathMeasurer(LinePath path){
		this.path=path;
	}
	
	public void clear(){
		lengthes.clear();
		path=null;
	}
	
	private void measureLengthes(){
		if(lengthes==null){
			lengthes=new ArrayList<Float>(path.size());
		}else{
			lengthes.clear();
		}
		float l=0;
		List<Vec2> list=path.getAll();
		lengthes.add(l);
		Vec2 pre=list.get(0);
		for(int i=1;i<list.size();i++){
			l+=Vec2.length(pre,list.get(i));
			lengthes.add(l);
		}
	}
	
	private void measureEndNormal(){
		endPoint=path.getLast();
		endNormal=path.getLast().copy().minus(path.get(path.size()-2)).toNormal();
	}
	
	public void measure(){
		measureLengthes();
		measureEndNormal();
	}
	
	public float maxLength(){
		return lengthes.get(lengthes.size()-1);
	}
	
	/**
	 *
	 */
	public Vec2 atLength(float l){
		if(l>=maxLength()){
			return endPoint.copy().add(endNormal.copy().zoom(l-maxLength()));
		}else{
			int s=binarySearch(l);
			float ls=lengthes.get(s);
			return Vec2.onLineLength(path.get(s),path.get(s+1),l-ls);
		}
	}
	
	public int binarySearch(float l){
		return binarySearch(l,0,lengthes.size()-1);
	}
	
	//l>=0&&l<maxLength
	public int binarySearch(float l,int start,int end){
		if(end-start<=1){
			return start;
		}else{
			int m=(start+end)/2;
			if(lengthes.get(m)<l){
				return binarySearch(l,start,m);
			}else{
				return binarySearch(l,m,end);
			}
		}
	}
}
