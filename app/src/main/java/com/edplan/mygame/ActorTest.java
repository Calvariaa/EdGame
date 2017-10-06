package com.edplan.mygame;
import com.edplan.simpleGame.view.Actor;
import com.edplan.simpleGame.view.Operater;
import android.graphics.Paint;
import android.graphics.Canvas;

public class ActorTest extends Actor
{
	Paint paint;
	
	public ActorTest(){
		setWidth(100);
		setHeight(100);
		paint=new Paint();
		paint.setAntiAlias(true);
		paint.setARGB(255,80,80,80);
		operater=new OperaterTest();
	}

	@Override
	public void draw(Canvas canvas)
	{
		// TODO: Implement this method
		super.draw(canvas);
		canvas.drawRoundRect(getLeft(),getTop(),getRight(),getBottom(),20,20,paint);
	}
	
	public class OperaterTest extends Operater{
		public OperaterTest(){
			Thread t=new Thread(new Runnable(){
				int d=1;
				@Override
				public void run()
				{
					// TODO: Implement this method
					while(true){
						try
						{
							Thread.currentThread().sleep(20);
						}
						catch (InterruptedException e)
						{}
						move(8*d,0);
						if(getRight()>500){
							d=-1;
						}else if(getLeft()<0){
							d=1;
						}
					}
				}
			});
			t.start();
		}
	}
	
}
