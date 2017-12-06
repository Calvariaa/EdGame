package com.edplan.framework.graphics.line;
import com.edplan.framework.math.Vec2;
import java.util.List;
import java.util.ArrayList;

public class LinePath
{
	private List<Vec2> positions;
	
	private float maxX,minX,maxY,minY;
	
	private float width;
	
	public LinePath(List<Vec2> ps,float width){
		positions=ps;
		recomputeBounds();
	}
	
	public LinePath(float width){
		positions=new ArrayList<Vec2>();
		this.width=width;
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

	public void setWidth(float width) {
		this.width=width;
		recomputeBounds();
	}

	public float getWidth() {
		return width;
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
		if(v.x-width<minX)minX=v.x-width;
		if(v.x+width>maxX)maxX=v.x+width;
		if(v.y-width<minY)minY=v.y-width;
		if(v.y+width>maxY)minX=v.y+width;
	}
	
	private void recomputeBounds(){
		resetBounds();
		for(Vec2 v:positions){
			expandBounds(v);
		}
	}
	
}
