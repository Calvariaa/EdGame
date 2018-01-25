package com.edplan.framework.math;
import com.edplan.framework.interfaces.Copyable;
import com.edplan.framework.ui.uiobjs.Area2D;

public class RectF implements Copyable,Area2D
{
	private Vec2 basePoint=new Vec2();
	
	private float width;
	
	private float height;
	
	public RectF(){
		
	}
	
	public RectF(RectF r){
		basePoint=r.basePoint.copy();
		this.width=r.width;
		this.height=r.height;
	}
	
	public RectF(float l,float t,float w,float h){
		setBasePoint(l,t);
		this.width=w;
		this.height=h;
	}
	
	public RectF set(RectF rect){
		setBasePoint(rect.getLeft(),rect.getTop());
		width=rect.getWidth();
		height=rect.getHeight();
		return this;
	}
	
	public RectF setLTRB(float l,float t,float r,float b){
		basePoint.set(l,t);
		height=b-t;
		width=r-l;
		return this;
	}
	
	public Vec2 getBasePoint(){
		return basePoint;
	}
	
	public void setBasePoint(float x,float y){
		getBasePoint().set(x,y);
	}

	public Vec2 getPoint(float x,float y){
		return basePoint.copy().add(x*width,y*height);
	}
	
	public float getX1(){
		return basePoint.x;
	}
	
	public float getX2(){
		return basePoint.x+width;
	}
	
	public float getY1(){
		return basePoint.y;
	}
	
	public float getY2(){
		return basePoint.y+height;
	}
	
	public float getTop(){
		return basePoint.y;
	}
	
	public float getLeft(){
		return basePoint.x;
	}
	
	public float getBottom(){
		return getTop()+getHeight();
	}
	
	public float getRight(){
		return getLeft()+getWidth();
	}
	
	public void setWidth(float width) {
		this.width=width;
	}

	public float getWidth() {
		return width;
	}

	public void setHeight(float height) {
		this.height=height;
	}

	public float getHeight() {
		return height;
	}
	
	public void move(float dx,float dy){
		getBasePoint().add(dx,dy);
	}
	
	public RectF padding(float padding){
		move(padding,padding);
		width-=2*padding;
		height-=2*padding;
		return this;
	}
	
	public RectF padding(float pl,float pt,float pr,float pb){
		move(pl,pt);
		width-=pl+pr;
		height-=pt+pb;
		return this;
	}
	
	public RectF padding(Vec4 p){
		return padding(p.r,p.g,p.b,p.a);
	}
	
	@Override
	public RectF copy(){
		return new RectF(this);
	}
	
	@Override
	public boolean inArea(Vec2 v) {
		// TODO: Implement this method
		Vec2 tmp=v.copy().minus(basePoint);
		return tmp.x>=0&&tmp.x<=width&&tmp.y>=0&&tmp.y<=height;
	}

	@Override
	public void fixRect(float l,float t,float r,float b) {
		// TODO: Implement this method
		this.setLTRB(l,t,r,b);
	}

	@Override
	public RectF boundRect() {
		// TODO: Implement this method
		return this.copy();
	}
	
	public static RectF ltrb(float l,float t,float r,float b){
		return (new RectF()).setLTRB(l,t,r,b);
	}
}
