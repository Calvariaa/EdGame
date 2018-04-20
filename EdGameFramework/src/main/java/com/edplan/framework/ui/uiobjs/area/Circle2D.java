package com.edplan.framework.ui.uiobjs.area;
import com.edplan.framework.ui.uiobjs.Area2D;
import com.edplan.framework.math.Vec2;
import com.edplan.framework.math.RectF;
import com.edplan.framework.math.FMath;
import com.edplan.framework.ui.Anchor;

public class Circle2D implements Area2D,IHasAreaSize
{

	private Vec2 origin;
	
	private float radius;
	
	private float radiusSquared;
	
	public Circle2D(Vec2 origin,float radius){
		this.origin=origin;
		this.radius=radius;
		compute();
	}
	
	public Circle2D(float ox,float oy,float radius){
		this.origin=new Vec2(ox,oy);
		this.radius=radius;
		compute();
	}

	public void setOrigin(float ox,float oy){
		this.origin.set(ox,oy);
	}
	
	/**
	 *不会改变本对象原点的对象引用
	 */
	public void setOrigin(Vec2 origin) {
		this.origin.set(origin);
	}

	public Vec2 getOrigin() {
		return origin;
	}

	public void setRadius(float radius) {
		this.radius=radius;
		compute();
	}

	public float getRadius() {
		return radius;
	}
	
	public void compute(){
		this.radiusSquared=radius*radius;
	}

	@Override
	public float getAreaSize() {
		// TODO: Implement this method
		return FMath.Pi*radiusSquared;
	}
	
	@Override
	public boolean inArea(Vec2 v) {
		// TODO: Implement this method
		return v.copy().minus(origin).lengthSquared()<=radiusSquared;
	}


	@Override
	public float maxX() {
		// TODO: Implement this method
		return origin.x+radius;
	}

	@Override
	public float maxY() {
		// TODO: Implement this method
		return origin.y+radius;
	}

	@Override
	public float minX() {
		// TODO: Implement this method
		return origin.x-radius;
	}

	@Override
	public float minY() {
		// TODO: Implement this method
		return origin.y-radius;
	}
	
}
