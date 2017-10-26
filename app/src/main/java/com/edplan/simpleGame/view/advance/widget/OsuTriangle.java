package com.edplan.simpleGame.view.advance.widget;
import android.graphics.Paint;
import com.edplan.simpleGame.math.PointF;
import android.graphics.Canvas;
import android.graphics.Path;

public class OsuTriangle
{
	public static final float SIN30=(float)Math.sin(Math.PI/6);
	
	public static final float COS30=(float)Math.cos(Math.PI/6);
	
	public Paint paint;
	
	public PointF centerPoint;
	
	public float radius;
	
	public OsuTriangle(){
		centerPoint=new PointF(0,0);
	}

	public float getTop(){
		return getCenterPoint().y-getRadius();
	}
	
	public float getLeft(){
		return getCenterPoint().x-getRadius()*COS30;
	}
	
	public float getBottom(){
		return getCenterPoint().y+getRadius()*SIN30;
	}
	
	public float getRight(){
		return getCenterPoint().x+getRadius()*COS30;
	}
	
	public void drawOnCanvas(Canvas canvas){
		Path p=makeTrianglePath(this);
		canvas.drawPath(p,getPaint());
	}
	
	public static Path makeTrianglePath(OsuTriangle t){
		Path p=new Path();
		p.moveTo(t.getCenterPoint().x,t.getTop());
		p.lineTo(t.getRight(),t.getBottom());
		p.lineTo(t.getLeft(),t.getBottom());
		p.close();
		
		return p;
	}
	
	public OsuTriangle setRadius(float radius){
		this.radius=radius;
		return this;
	}

	public float getRadius(){
		return radius;
	}

	public OsuTriangle setPaint(Paint paint){
		this.paint=paint;
		return this;
	}

	public Paint getPaint(){
		if(paint==null)paint=new Paint();
		return paint;
	}

	public OsuTriangle setCenter(float x,float y){
		this.centerPoint.set(x,y);
		return this;
	}

	public PointF getCenterPoint(){
		return centerPoint;
	}
	
	
}
