package com.edplan.simpleGame.view;

import com.edplan.simpleGame.math.PointF;
import android.view.MotionEvent;
import android.graphics.drawable.Drawable;
import android.graphics.ColorFilter;
import android.graphics.Canvas;
import com.edplan.simpleGame.inputs.Pointer;

public class BaseWidget extends Drawable
{
	
	public PointF basePoint;
	public float height;
	public float width;
	
	public boolean visible=true;
	
	public boolean clipCanvas=true;
	public boolean clipTouch=false;
	
	public BaseWidget(){
		basePoint=new PointF(0,0);
	}
	
	public BaseWidget(float w,float h){
		this();
		width=w;
		height=h;
	}
	
	public BaseWidget(float w,float h,String dw){
		this();
		switch(dw){
			case "dp":
				w=BaseDatas.dpToPixel(w);
				h=BaseDatas.dpToPixel(h);
				break;
		}
		width=w;
		height=h;
	}
	
	public boolean catchPointer(Pointer p){
		return false;
	}

	public void setClipTouch(boolean clipTouch)
	{
		this.clipTouch = clipTouch;
	}

	public boolean isClipTouch()
	{
		return clipTouch;
	}

	public void setClipCanvas(boolean clipCanvas)
	{
		this.clipCanvas = clipCanvas;
	}

	public boolean isClipCanvas()
	{
		return clipCanvas;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public boolean ifVisible()
	{
		return visible;
	}
	
	public void setOnTouchListener(MOnTouchListener l){
		
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
	
	public boolean onTouch(MotionEvent event){
		return false;
	}
	
	public boolean inWidget(float x,float y){
		//if(getWidth()==0&&getHeight()==0)return true;
		return (x>=basePoint.x)&&(x<=basePoint.x+getWidth())&&(y>=basePoint.y)&&(y<=basePoint.y+getHeight());
	}
	
	public Canvas clipCanvasToThis(Canvas c){
		c.clipRect(getLeft(),getTop(),getRight(),getBottom());
		c.translate(basePoint.x,basePoint.y);
		return c;
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		// TODO: Implement this method
	}

	@Override
	public void setAlpha(int a)
	{
		// TODO: Implement this method
	}

	@Override
	public void setColorFilter(ColorFilter p1)
	{
		// TODO: Implement this method
	}

	@Override
	public int getOpacity()
	{
		// TODO: Implement this method
		return 0;
	}
	
	public interface MOnTouchListener{
		public boolean onTouch(MotionEvent event);
	}
	
	public interface MOnClickListener{
		public void onClick(BaseWidget view);
	}
	
}
