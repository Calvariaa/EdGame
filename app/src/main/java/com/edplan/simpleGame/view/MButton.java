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
import com.edplan.simpleGame.MContext;

public class MButton extends BaseWidget
{
	public String text="";
	
	public int textColor=Color.argb(255,250,250,250);
	
	public TextPaint textPaint;
	
	public MOnClickListener onClickListener;
	
	public int alpha;
	
	public float padding=BaseDatas.dpToPixel(2);
	public float round=BaseDatas.dpToPixel(6);
	
	public int color=0xFF0BFFF5;
	public Paint buttonPaint;
	public Paint shadowPaint;
	
	public boolean isTouching=false;
	public Pointer cPointer;
	
	public boolean clickable=true;
	
	public MButton(MContext con){
		super(con);
		buttonPaint=new Paint();
		buttonPaint.setAntiAlias(true);
		buttonPaint.setStyle(Paint.Style.FILL);
		shadowPaint=new Paint();
		shadowPaint.setColor(0xBB333333);
		shadowPaint.setAlpha(150);
		shadowPaint.setStyle(Paint.Style.FILL);
		textPaint=new TextPaint();
		textPaint.setAntiAlias(true);
		textPaint.setFakeBoldText(true);
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setTextSize(BaseDatas.dpToPixel(20));
		textPaint.setColor(getTextColor());
		alpha=255;
		basePoint=new PointF(0,0);
		setWidth(BaseDatas.dpToPixel(150));
		setHeight(BaseDatas.dpToPixel(35));
	}

	public MButton setTextColor(int textColor){
		this.textColor=textColor;
		return this;
	}

	public int getTextColor(){
		return textColor;
	}

	public MButton setIgnoreTextAlpha(boolean ignoreTextAlpha){
		this.ignoreTextAlpha=ignoreTextAlpha;
		return this;
	}

	public boolean ifIgnoreTextAlpha(){
		return ignoreTextAlpha;
	}

	public MButton setText(String text){
		this.text=text;
		return this;
	}

	public String getText(){
		return text;
	}

	public MButton setColor(int color){
		this.color=color;
		return this;
	}

	public int getColor(){
		return color;
	}

	public void setRound(float round){
		this.round=round;
	}

	public float getRound(){
		return round;
	}

	public void setClickable(boolean clickable){
		this.clickable=clickable;
	}

	public boolean isClickable(){
		return clickable;
	}

	public void setButtonPaint(Paint buttonPaint){
		this.buttonPaint=buttonPaint;
	}

	public Paint getButtonPaint(){
		return buttonPaint;
	}

	public void setShadowPaint(Paint shadowPaint){
		this.shadowPaint=shadowPaint;
	}

	public Paint getShadowPaint(){
		return shadowPaint;
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		// TODO: Implement this method
		
		
		//textPaint.setColor(makeTextColor());
		makeShadowPaint();
		drawShadow(canvas);
		makeButtonPaint();
		drawButton(canvas);
		makeTextPaint();
		drawText(canvas);
	}
	
	public void makeTextPaint(){
		textPaint.setColor(makeTextColor());
	}
	
	public void drawText(Canvas canvas){
		canvas.drawText(text,padding+(isTouching()?2:0)+getWidth()/2,padding+(isTouching()?2:0)+getHeight()/2-(textPaint.descent()+textPaint.ascent())/2,textPaint);
	}
	
	public void makeButtonPaint(){
		buttonPaint.setColor(isTouching()?makeTouchColor():makeColor());
	}
	
	public void drawButton(Canvas canvas){
		canvas.drawRoundRect(new RectF(padding+(isTouching()?2:0),padding+(isTouching()?2:0),-padding+(isTouching()?2:0)+getWidth(),-padding+(isTouching()?2:0)+getHeight()),
							 getRound(),getRound(),buttonPaint);
	}
	
	public void makeShadowPaint(){
		shadowPaint.setAlpha(getAlpha()*getAlpha()/255*(isTouching()?200:140)/200);
	}
	
	public void drawShadow(Canvas canvas){
		canvas.drawRoundRect(new RectF(padding+(isTouching()?5:6),padding+(isTouching()?5:6),-padding+(isTouching()?5:6)+getWidth(),-padding+(isTouching()?5:6)+getHeight()),
							 getRound()+5,getRound()+5,shadowPaint);
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
						public void onEnd(Pointer p){
							// TODO: Implement this method
							cPointer=null;
						}


						@Override
						public void onCancel(Pointer p){
							// TODO: Implement this method
							
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
	
	boolean ignoreTextAlpha=false;
	
	public int makeTextColor(){
		return Color.argb(ifIgnoreTextAlpha()?255:(Color.alpha(getTextColor())*getAlpha()/255),Color.red(getTextColor()),Color.green(getTextColor()),Color.blue(getTextColor()));
	}
	
	public int makeColor(){
		return Color.argb(Color.alpha(color)*getAlpha()/255,Color.red(color),Color.green(color),Color.blue(color));
	}
	
	public int makeTouchColor(){
		int a=getAlpha()*Color.alpha(color)/255;
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

	public int getAlpha(){
		// TODO: Implement this method
		return alpha;
	}

	@Override
	public MButton setAlpha(int a)
	{
		// TODO: Implement this method
		alpha=a;
		buttonPaint.setAlpha(alpha);
		textPaint.setAlpha(alpha);
		return this;
	}

	@Override
	public MButton setColorFilter(ColorFilter p1)
	{
		// TODO: Implement this method
		return this;
	}

	@Override
	public int getOpacity()
	{
		// TODO: Implement this method
		return 0;
	}
	
}
