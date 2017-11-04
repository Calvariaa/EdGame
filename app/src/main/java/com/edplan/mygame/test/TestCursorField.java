package com.edplan.mygame.test;
import com.edplan.simpleGame.view.BaseWidget;
import android.graphics.Canvas;
import android.graphics.RectF;
import com.edplan.simpleGame.inputs.Pointer;
import com.edplan.simpleGame.math.PointF;
import android.graphics.Paint;
import com.edplan.simpleGame.MContext;
import android.graphics.Bitmap;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff;

public class TestCursorField extends BaseWidget
{
	private RectF touchField;
	
	private RectF canvasField;
	
	private Pointer pointer;
	
	private PointF cursorPoint;
	
	private Paint touchFieldPaint;
	
	private Paint cursorPaint;
	
	private Bitmap buffered;
	
	private Bitmap nowBitmap;
	
	public TestCursorField(MContext con){
		super(con);
		touchField=new RectF();
		canvasField=new RectF();
		cursorPoint=new PointF(0,0);
		touchFieldPaint=new Paint();
		touchFieldPaint.setARGB(100,240,100,100);
		cursorPaint=new Paint();
		cursorPaint.setARGB(200,200,210,220);
		cursorPaint.setStrokeWidth(10);
	}
	
	private void updateCursor(){
		if(pointer!=null)
			reflectToCanvas(pointer.getX(),pointer.getY(),touchField,canvasField,cursorPoint);
	}

	@Override
	public boolean catchPointer(Pointer p){
		// TODO: Implement this method
		if(pointer==null){
			pointer=p;
			pointer.addCallback(new Pointer.Callback(){
					@Override
					public void onMove(Pointer p){
						// TODO: Implement this method
						updateCursor();
					}

					@Override
					public void onUp(Pointer p){
						// TODO: Implement this method
					}

					@Override
					public void onCancel(Pointer p){
						// TODO: Implement this method
					}

					@Override
					public void onEnd(Pointer p){
						// TODO: Implement this method
						pointer=null;
					}
				});
			return true;
		}
		return false;
	}
	
	public void updateTouchField(){
		
		touchField.set(canvasField);
		/*
		touchField.set(
			canvasField.left+canvasField.width()*0.5f,
			canvasField.top+canvasField.height()*0.5f,
			canvasField.left+canvasField.width()*0.9f,
			canvasField.top+canvasField.height()*0.9f
		);*/
	}

	@Override
	public BaseWidget setBasePoint(float x, float y){
		// TODO: Implement this method
		
		return super.setBasePoint(x, y);
	}

	@Override
	public BaseWidget setHeight(float height){
		// TODO: Implement this method
		super.setHeight(height);
		canvasField.set(canvasField.left,canvasField.top,canvasField.right,canvasField.top+height);
		updateTouchField();
		return this;
	}

	@Override
	public BaseWidget setWidth(float width){
		// TODO: Implement this method
		super.setWidth(width);
		canvasField.set(canvasField.left,canvasField.top,canvasField.left+width,canvasField.bottom);
		updateTouchField();
		return this;
	}
	
	private void reflectToCanvas(float x,float y,RectF raw,RectF target,PointF p){
		float xr=(raw.width()!=0)?((x-raw.left)/raw.width()):0;
		float yr=(raw.height()!=0)?((y-raw.top)/raw.height()):0;
		p.set(target.left+target.width()*xr,target.top+target.height()*yr);
	}

	@Override
	public void draw(Canvas canvas){
		// TODO: Implement this method
		super.draw(canvas);
		if(buffered==null||buffered.getHeight()!=canvas.getHeight()||buffered.getWidth()!=canvas.getWidth()){
			buffered=Bitmap.createBitmap(canvas.getWidth(),canvas.getHeight(),Bitmap.Config.ARGB_8888);
			nowBitmap=Bitmap.createBitmap(canvas.getWidth(),canvas.getHeight(),Bitmap.Config.ARGB_8888);
		}
		Canvas bc=new Canvas(buffered);
		Canvas nc=new Canvas(nowBitmap);
		
		Paint p=new Paint();
		p.setAlpha(200);
		
		Paint p2=new Paint();
		p2.setAlpha(255);
		
		Paint pc=new Paint();
		pc.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		
		nc.drawRect(0,0,nc.getWidth(),nc.getHeight(),pc);
		nc.drawBitmap(buffered,0,0,p);
		nc.drawCircle(cursorPoint.x,cursorPoint.y,20,cursorPaint);
		
		bc.drawRect(0,0,bc.getWidth(),bc.getHeight(),pc);
		bc.drawBitmap(nowBitmap,0,0,p2);
		
		canvas.drawBitmap(nowBitmap,0,0,p2);
		//canvas.drawRect(touchField,touchFieldPaint);
	}
}
