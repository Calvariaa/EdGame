package com.edplan.simpleGame.view;
import android.graphics.Canvas;

public class MFlatButton extends MButton
{
	public MFlatButton(){
		super();
	}

	@Override
	public void drawButton(Canvas canvas){
		// TODO: Implement this method
		//super.drawButton(canvas);
		canvas.drawRoundRect(getLeft(),getTop(),getRight(),getBottom(),getRound(),getRound(),getButtonPaint());
	}

	@Override
	public void drawShadow(Canvas canvas){
		// TODO: Implement this method
		//super.drawShadow(canvas);
	}
	
}
