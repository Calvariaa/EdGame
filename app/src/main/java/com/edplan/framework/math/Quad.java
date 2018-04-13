package com.edplan.framework.math;
import com.edplan.framework.ui.Anchor;
import java.util.Stack;

public class Quad implements IQuad
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

	public void setTopLeft(Vec2 topLeft) {
		this.topLeft.set(topLeft);
	}

	@Override
	public Vec2 getTopLeft() {
		return topLeft.copy();
	}

	public void setTopRight(Vec2 topRight) {
		this.topRight.set(topRight);
	}

	@Override
	public Vec2 getTopRight() {
		return topRight.copy();
	}

	public void setBottomLeft(Vec2 bottomLeft) {
		this.bottomLeft.set(bottomLeft);
	}

	@Override
	public Vec2 getBottomLeft() {
		return bottomLeft.copy();
	}

	public void setBottomRight(Vec2 bottomRight) {
		this.bottomRight.set(bottomRight);
	}

	@Override
	public Vec2 getBottomRight() {
		return bottomRight.copy();
	}

	@Override
	public Vec2 getPoint(float x,float y) {
		// TODO: Implement this method
		return mapPoint(topLeft,topRight,bottomLeft,bottomRight,x,y);
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
	
	public void rotate(float ox,float oy,float ang){
		for(Vec2 v:vertexs){
			v.rotate(ox,oy,ang);
		}
	}
	
	public void rotate(Vec2 o,float ang){
		rotate(o.x,o.y,ang);
	}
	
	public void rotate(Anchor anchor,float ang){
		rotate(getPoint(anchor.x(),anchor.y()),ang);
	}
	
	public void translate(float tx,float ty){
		for(Vec2 v:vertexs){
			v.move(tx,ty);
		}
	}
	
	/**
	 *通过射线法判断p是否在范围内
	 */
	public boolean inArea(Vec2 p){
		return Polygon.inPolygon(p,vertexs);
	}
	
	public static Vec2 mapPoint(Vec2 ptl,Vec2 ptr,Vec2 pbl,Vec2 pbr,float vx,float vy){
		float x=((ptl.x+pbl.x)*(1-vx)+(ptr.x+pbr.x)*vx)/2;
		float y=((ptl.y+ptr.y)*(1-vy)+(pbl.y+pbr.y)*vy)/2;
		return new Vec2().set(x,y);
	}
}
