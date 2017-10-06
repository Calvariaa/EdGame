package com.edplan.mygame;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.edplan.simpleGame.inputs.Pointer;
import com.edplan.simpleGame.view.BaseWidget;
import com.edplan.simpleGame.inputs.TouchEventHelper;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
	public int surfaceWidth=0;
	public int surfaceHeight=0;
	
	SurfaceHolder mHolder;
	DrawThread mDrawThread;
	Canvas mCanvas;
	
	TouchEventHelper touchHelper;
	
	BaseWidget content;
	
	public GameSurfaceView(Context context,AttributeSet attr){
		super(context,attr);
		initial();
	}
	
	public GameSurfaceView(Context context){
		super(context);
		initial();
	}

	public void setSurfaceWidth(int surfaceWidth){
		this.surfaceWidth=surfaceWidth;
	}

	public int getSurfaceWidth(){
		return surfaceWidth;
	}

	public void setSurfaceHeight(int surfaceHeight){
		this.surfaceHeight=surfaceHeight;
	}

	public int getSurfaceHeight(){
		return surfaceHeight;
	}

	public void setContent(BaseWidget content){
		this.content=content;
	}

	public BaseWidget getContent(){
		return content;
	}
	
	public void initial(){
		mHolder=getHolder();
		mHolder.addCallback(this);
		mDrawThread=new DrawThread();
		touchHelper=new TouchEventHelper();
	}
	
	public void mDraw(Canvas c){
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		// TODO: Implement this method
		if(touchHelper.sendEvent(event)){
			//已被捕捉或直接发送给了pointer
			Pointer p=touchHelper.getCatchedPointer();
			if(p!=null)if(content!=null&&content.ifVisible()){
				if(content.catchPointer(p)){
					touchHelper.catchPointer(p);
				}
			}
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder p1)
	{
		// TODO: Implement this method
		mDrawThread.start();
		Log.v("surface","created");
	}

	@Override
	public void surfaceChanged(SurfaceHolder p1, int f, int w, int h)
	{
		// TODO: Implement this method
		Log.v("surface","changed "+f+"|"+w+"|"+h);
		setSurfaceWidth(w);
		setSurfaceHeight(h);
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
					}else{
						if(System.currentTimeMillis()-t>30){
							Log.w("m_render","render cost time : "+(System.currentTimeMillis()-t));
						}
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
