package com.edplan.mygame;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
	SurfaceHolder mHolder;
	DrawThread mDrawThread;
	Canvas mCanvas;
	
	public GameSurfaceView(Context context,AttributeSet attr){
		super(context,attr);
		initial();
	}
	
	public GameSurfaceView(Context context){
		super(context);
		initial();
	}
	
	public void initial(){
		mHolder=getHolder();
		mHolder.addCallback(this);
		mDrawThread=new DrawThread();
	}
	
	public void mDraw(Canvas c){
		
	}
	
	
	@Override
	public void surfaceCreated(SurfaceHolder p1)
	{
		// TODO: Implement this method
		mDrawThread.start();
		Log.v("surface","created");
	}

	@Override
	public void surfaceChanged(SurfaceHolder p1, int p2, int p3, int p4)
	{
		// TODO: Implement this method
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder p1)
	{
		// TODO: Implement this method
		if(mDrawThread!=null)mDrawThread.setFlag(DrawThread.Flag.Stopped);
	}
	
	public class DrawThread extends Thread
	{
		public enum Flag{
			Drawing,Waiting,Stopped
		}
		
		public Flag flag;
		
		public boolean running;
		
		public DrawThread(){
			flag=Flag.Waiting;
			running=false;
		}
		
		long t;
		@Override
		public void run()
		{
			// TODO: Implement this method
			while(flag!=Flag.Stopped){
				if(flag==Flag.Waiting){
					//等待
					try
					{
						sleep(30);
					}
					catch (InterruptedException e)
					{}
				}else{
					//正式绘图
					t=System.currentTimeMillis();
					draw();
					if(System.currentTimeMillis()-t<4){
						try
						{
							sleep(4);
						}
						catch (InterruptedException e)
						{}
					}
				}
			}
		}
		
		public void draw(){
			try{
				mCanvas=mHolder.lockCanvas();
				if(mCanvas!=null)GameSurfaceView.this.mDraw(mCanvas);
			}finally{
				if(mCanvas!=null){
					mHolder.unlockCanvasAndPost(mCanvas);
				}
			}
		}

		@Override
		public void start()
		{
			// TODO: Implement this method
			super.start();
			running=true;
			setFlag(Flag.Drawing);
		}
		
		public void setFlag(Flag f){
			flag=f;
		}
		
		
		
	}

}
