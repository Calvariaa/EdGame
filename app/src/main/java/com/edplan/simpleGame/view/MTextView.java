package com.edplan.simpleGame.view;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.text.StaticLayout;
import android.text.Layout;

public class MTextView extends BaseWidget{
	
	public float textWidth=100;
	public StaticLayout textLayout;
	public String text="";
	public Layout.Alignment alignment=Layout.Alignment.ALIGN_NORMAL;
	public TextPaint textPaint;
	
	public MTextView(){
		this("");
	}
	
	public MTextView(String _text){
		text=_text;
		textPaint=new TextPaint();
		textPaint.setAntiAlias(true);
		textPaint.setTextSize(50);
		textPaint.setARGB(255,80,80,80);
		update();
		setClipCanvas(true);
		setHeight(100);
		setWidth(100);
	}

	@Override
	public void setWidth(float textWidth){
		super.setWidth(textWidth);
		this.textWidth=textWidth;
		update();
	}
	
	public void update(){
		textLayout=buildTextLayout();
	}

	public float getTextWidth(){
		return textWidth;
	}

	public void setAlignment(Layout.Alignment alignment){
		this.alignment=alignment;
		update();
	}

	public Layout.Alignment getAlignment(){
		return alignment;
	}

	public void setTextPaint(TextPaint textPaint){
		this.textPaint=textPaint;
		update();
	}

	public TextPaint getTextPaint(){
		return textPaint;
	}
	
	public StaticLayout buildTextLayout(){
		StaticLayout s=new StaticLayout(
			getText(),
			getTextPaint(),
			(int)getTextWidth(),
			getAlignment(),
			1.2f,
			0,
			false
		);
		return s;
	}

	@Override
	public void draw(Canvas canvas){
		// TODO: Implement this method
		super.draw(canvas);
		textLayout.draw(canvas);
	}
	
	public void setText(String text){
		this.text=text;
		textLayout=buildTextLayout();
	}

	public String getText(){
		return text;
	}

}

//layout = new StaticLayout(content, paint, (int) (400),
//                    Layout.Alignment.ALIGN_CENTER, 1F, 0, false);
         

