package com.edplan.framework.view;
import com.edplan.framework.math.Vec2;
import android.graphics.Canvas;

public class Actor
{
	
	public Operater operater;
	
	public Vec2 basePoint;
	public float height;
	public float width;
	
	public float rotation;
	
	public boolean visible=true;
	
	public Actor(){
		basePoint=new Vec2(0,0);
	}

	public void setVisible(boolean visible){
		this.visible=visible;
	}

	public boolean isVisible(){
		return visible;
	}
	
	
	public void draw(Canvas canvas){
		
	}
	
	
	public void move(float dx,float dy){
		basePoint.move(dx,dy);
	}
	
	public int getStatuCode(){
		return -1;
	}
	
	public int getGroupCode(){
		return -1;
	}
	
	public void setBasePoint(float x,float y){
		basePoint.set(x,y);
	}

	public void setCenter(float x,float y){
		setBasePoint(x-getWidth()/2,y-getHeight()/2);
	}

	public void setCenterX(float x){
		setBasePoint(x-getWidth()/2,basePoint.y);
	}

	public void setCenterY(float y){
		setBasePoint(basePoint.x,y-getHeight()/2);
	}

	public void setBottom(float y){
		setBasePoint(basePoint.x,y-getHeight());
	}

	public void setHeight(float height)
	{
		this.height = height;
	}

	public float getHeight()
	{
		return height;
	}

	public void setWidth(float width)
	{
		this.width = width;
	}

	public float getWidth()
	{
		return width;
	}

	public float getTop(){
		return basePoint.y;
	}

	public float getBottom(){
		return basePoint.y+getHeight();
	}

	public float getLeft(){
		return basePoint.x;
	}

	public float getRight(){
		return basePoint.x+getWidth();
	}
	
	public float getCenterX(){
		return basePoint.x+getWidth()/2;
	}
	
	public float getCenterY(){
		return basePoint.y+getHeight()/2;
	}
	
	public boolean inArea(float x,float y){
		return (x>=basePoint.x)&&(x<=basePoint.x+getWidth())&&(y>=basePoint.y)&&(y<=basePoint.y+getHeight());
	}
}
