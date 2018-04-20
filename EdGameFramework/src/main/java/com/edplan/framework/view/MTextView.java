package com.edplan.framework.view;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.edplan.framework.MContext;

public class MTextView extends BaseView{
	
	public float textFieldWidth=100;
	public StaticLayout textLayout;
	public String text="";
	public Layout.Alignment alignment=Layout.Alignment.ALIGN_NORMAL;
	public TextPaint textPaint;
	public Bitmap textBuffered;
	
	public MTextView(MContext con){
		this(con,"");
	}
	
	public MTextView(MContext con,String _text){
		super(con);
		text=_text;
		textPaint=new TextPaint();
		textPaint.setAntiAlias(true);
		textPaint.setTextSize(40);
		textPaint.setARGB(255,80,80,80);
		update();
		setClipCanvas(true);
		setHeight(100);
		setWidth(100);
	}
	
	public void setTextLayout(StaticLayout textLayout){
		this.textLayout=textLayout;
	}

	public StaticLayout getTextLayout(){
		return textLayout;
	}
	
	public MTextView setTextColor(int color){
		getTextPaint().setColor(color);
		return this;
	}
	
	public MTextView setTextSize(float size){
		getTextPaint().setTextSize(size);
		return this;
	}
	
	@Override
	public MTextView setWidth(float textFieldWidth){
		super.setWidth(textFieldWidth);
		this.textFieldWidth=textFieldWidth;
		return this;
	}
	
	public float neededHeight(){
		return textLayout.getHeight();
	}
	
	public void update(){
		textLayout=buildTextLayout();
	}

	public float getTextFieldWidth(){
		return textFieldWidth;
	}

	public void setAlignment(Layout.Alignment alignment){
		this.alignment=alignment;
	}

	public Layout.Alignment getAlignment(){
		return alignment;
	}

	public void setTextPaint(TextPaint textPaint){
		this.textPaint=textPaint;
	}

	public TextPaint getTextPaint(){
		return textPaint;
	}
	
	public StaticLayout buildTextLayout(){
		StaticLayout s=new StaticLayout(
			getText(),
			getTextPaint(),
			(int)getTextFieldWidth(),
			getAlignment(),
			1.01f,
			0,
			false
		);
		//Log.v("test",s.getHeight()+"");
		return s;
	}

	@Override
	public void draw(Canvas canvas){
		// TODO: Implement this method
		super.draw(canvas);
		textLayout.draw(canvas);
	}
	
	public MTextView setText(String text){
		this.text=text;
		textLayout=buildTextLayout();
		return this;
	}

	public String getText(){
		return text;
	}

}

//layout = new StaticLayout(content, paint, (int) (400),
//                    Layout.Alignment.ALIGN_CENTER, 1F, 0, false);
         

