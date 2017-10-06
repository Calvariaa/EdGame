package com.edplan.simpleGame.view;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.view.MotionEvent;
import com.edplan.simpleGame.math.PointF;
import com.edplan.simpleGame.inputs.Pointer;

public class MButton extends BaseWidget
{
	public String text="";
	
	public TextPaint textPaint;
	
	public MOnClickListener onClickListener;
	
	public int alpha;
	
	public float padding=4;
	public float round=15;
	
	public int color=0xFF0BFFF5;
	public Paint paint;
	public Paint shadowPaint;
	
	public boolean isTouching=false;
	public Pointer cPointer;
	
	public MButton(){
		paint=new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		shadowPaint=new Paint();
		shadowPaint.setColor(0xBB333333);
		shadowPaint.setAlpha(150);
		shadowPaint.setStyle(Paint.Style.FILL);
		textPaint=new TextPaint();
		textPaint.setAntiAlias(true);
		textPaint.setFakeBoldText(true);
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setTextSize(BaseDatas.dpToPixel(20));
		textPaint.setARGB(255,250,250,250);
		alpha=255;
		basePoint=new PointF(0,0);
		setWidth(BaseDatas.dpToPixel(150));
		setHeight(BaseDatas.dpToPixel(35));
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		// TODO: Implement this method
		
		paint.setColor(isTouching()?makeTouchColor():makeColor());
		//textPaint.setColor(makeTextColor());
		shadowPaint.setAlpha(alpha*alpha/255*(isTouching()?200:140)/200);
		drawButton(canvas);
		drawText(canvas);
		drawText(canvas);
	}
	
	public void drawText(Canvas canvas){
		canvas.drawText(text,padding+(isTouching()?2:0)+getWidth()/2,padding+(isTouching()?2:0)+getHeight()/2-(textPaint.descent()+textPaint.ascent())/2,textPaint);
	}
	
	public void drawButton(Canvas canvas){
		canvas.drawRoundRect(new RectF(padding+(isTouching()?5:6),padding+(isTouching()?5:6),-padding+(isTouching()?5:6)+getWidth(),-padding+(isTouching()?5:6)+getHeight()),
							 round+5,round+5,shadowPaint);
		canvas.drawRoundRect(new RectF(padding+(isTouching()?2:0),padding+(isTouching()?2:0),-padding+(isTouching()?2:0)+getWidth(),-padding+(isTouching()?2:0)+getHeight()),
							 round,round,paint);
		
	}
	
	public void setOnClickListener(MOnClickListener l){
		onClickListener=l;
	}
	
	public boolean isTouching(){
		return cPointer!=null;
	}

	@Override
	public boolean catchPointer(Pointer p){
		// TODO: Implement this method
		if(cPointer==null){
			if(inWidget(p.getX(),p.getY())){
				cPointer=p;
				cPointer.addCallback(new Pointer.Callback(){

						@Override
						public void onCancel(Pointer p){
							// TODO: Implement this method
							cPointer=null;
						}

						@Override
						public void onMove(Pointer p){
							// TODO: Implement this method
						}

						@Override
						public void onUp(Pointer p){
							// TODO: Implement this method
							if(inWidget(p.getX(),p.getY())){
								if(onClickListener!=null)onClickListener.onClick(MButton.this);
							}
							cPointer=null;
						}
					});
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	
	
	@Override
	public boolean onTouch(MotionEvent event)
	{
		// TODO: Implement this method
		
		return super.onTouch(event);
	}
	
	
	
	public int makeTextColor(){
		return Color.argb(Color.alpha(color)*alpha/255,Color.blue(color),Color.red(color),Color.green(color));
	}
	
	public int makeColor(){
		return Color.argb(Color.alpha(color)*alpha/255,Color.red(color),Color.green(color),Color.blue(color));
	}
	
	public int makeTouchColor(){
		int a=alpha*Color.alpha(color)/255;
		int r=Color.red(color);
		int g=Color.green(color);
		int b=Color.blue(color);
		r-=10;
		if(r<0)r+=20;
		g-=10;
		if(g<0)g+=20;
		b-=10;
		if(b<0)b+=20;
		/*
		r+=10;
		if(r>255)r-=20;
		g+=10;
		if(g>255)g-=20;
		b+=10;
		if(b>255)b-=20;*/
		return Color.argb(a,r,g,b);
	}

	@Override
	public void setAlpha(int a)
	{
		// TODO: Implement this method
		alpha=a;
		paint.setAlpha(alpha);
		textPaint.setAlpha(alpha);
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
	
}
