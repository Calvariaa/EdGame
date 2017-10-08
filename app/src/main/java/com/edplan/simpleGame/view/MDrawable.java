package com.edplan.simpleGame.view;
import android.graphics.drawable.Drawable;
import android.graphics.ColorFilter;
import android.graphics.Canvas;

public interface MDrawable
{
	public void draw(Canvas canvas);

	public MDrawable setAlpha(int a);

	public MDrawable setColorFilter(ColorFilter p1);

	public int getOpacity();
}
