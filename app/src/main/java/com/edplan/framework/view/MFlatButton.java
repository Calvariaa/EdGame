package com.edplan.framework.view;
import android.graphics.Canvas;
import com.edplan.framework.MContext;

public class MFlatButton extends MButton
{
	public MFlatButton(MContext con){
		super(con);
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
