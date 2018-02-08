package com.edplan.framework.math;

public class Quad
{
	public Vec2 topLeft=new Vec2();
	public Vec2 topRight=new Vec2();
	public Vec2 bottomLeft=new Vec2();
	public Vec2 bottomRight=new Vec2();
	public Vec2[] vertexs;

	public Quad(){
		initial();
	}
	
	public Quad(RectF r){
		initial();
		set(r);
	}
	
	private void initial(){
		vertexs=new Vec2[]{topLeft,topRight,bottomRight,bottomLeft};
	}
	
	public Quad set(Quad res){
		topLeft.set(res.topLeft);
		topRight.set(res.topRight);
		bottomLeft.set(res.bottomLeft);
		bottomRight.set(res.bottomRight);
		return this;
	}
	
	public Quad set(Vec2 tl,Vec2 tr,Vec2 bl,Vec2 br){
		topLeft.set(tl);
		topRight.set(tr);
		bottomLeft.set(bl);
		bottomRight.set(br);
		return this;
	}
	
	public Quad set(RectF r){
		return set(r.getTopLeft(),r.getTopRight(),r.getBottomLeft(),r.getBottomRight());
	}
	
	/**
	 *通过射线法判断p是否在范围内
	 */
	public boolean inArea(Vec2 p){
		return Polygon.inPolygon(p,vertexs);
	}
	
}
