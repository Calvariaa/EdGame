package com.edplan.framework.graphics.line;
import com.edplan.framework.math.Vec2;
import java.util.List;
import java.util.ArrayList;
import com.edplan.framework.math.RectF;

public class LinePath
{
	private List<Vec2> positions;
	
	private float maxX,minX,maxY,minY;
	
	private float width;
	
	private float hwidth;
	
	public LinePath(List<Vec2> ps,float width){
		positions=ps;
		this.width=width;
		this.hwidth=width/2;
		recomputeBounds();
	}
	
	public LinePath(float width){
		positions=new ArrayList<Vec2>();
		this.width=width;
		this.hwidth=width/2;
	}
	
	public LinePath(){
		positions=new ArrayList<Vec2>();
	}

	public int size(){
		return positions.size();
	}
	
	public Vec2 get(int index){
		return positions.get(index);
	}
	
	public Vec2 getLast(){
		return get(size()-1);
	}
	
	public List<Vec2> getAll(){
		return positions;
	}
	
	public RectF getDrawArea(){
		return new RectF(minX,minY,getAreaWidth(),getAreaHeight());
	}
	
	public float getAreaWidth(){
		return maxX-minX;
	}
	
	public float getAreaHeight(){
		return maxY-minY;
	}

	public void setWidth(float width) {
		this.width=width;
		this.hwidth=width/2;
		recomputeBounds();
	}

	public float getWidth() {
		return width;
	}
	
	public float getHWidth(){
		return hwidth;
	}
	
	public void set(List<Vec2> ps){
		positions=ps;
		recomputeBounds();
	}
	
	public void clearPositionData(){
		positions.clear();
		resetBounds();
	}
	
	public void add(Vec2 v){
		positions.add(v);
		expandBounds(v);
	}
	
	private void resetBounds(){
		minX=minY=maxX=maxY=0;
	}
	
	private void expandBounds(Vec2 v){
		if(v.x-hwidth<minX)minX=v.x-hwidth;
		if(v.x+hwidth>maxX)maxX=v.x+hwidth;
		if(v.y-hwidth<minY)minY=v.y-hwidth;
		if(v.y+hwidth>maxY)minX=v.y+hwidth;
	}
	
	private void recomputeBounds(){
		resetBounds();
		for(Vec2 v:positions){
			expandBounds(v);
		}
	}
	
	
	
	public LinePath cutPath(float start,float end){
		return null;
	}
	
}
