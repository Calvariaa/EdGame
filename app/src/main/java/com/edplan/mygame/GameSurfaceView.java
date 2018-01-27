package com.edplan.mygame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.edplan.framework.MContext;
import com.edplan.framework.inputs.Pointer;
import com.edplan.framework.inputs.TouchEventHelper;
import com.edplan.framework.view.BaseView;
import com.edplan.superutils.classes.MLooperThread;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
	private int RENDER_WARNING_TIME=40;
	
	public int surfaceWidth=0;
	public int surfaceHeight=0;
	
	SurfaceHolder mHolder;
	DrawThread mDrawThread;
	Canvas mCanvas;
	
	public int clearColor=0xFFFFFFFF;
	
	TouchEventHelper touchHelper;
	
	BaseView content;
	
	private MContext context;
	
	private boolean drawDetails=true;
	
	public GameSurfaceView(Context context,AttributeSet attr){
		super(context,attr);
		initial();
	}
	
	public GameSurfaceView(Context context){
		super(context);
		initial();
	}
	
	

	public void setClearColor(int clearColor){
		this.clearColor=clearColor;
	}

	public int getClearColor(){
		return clearColor;
	}

	public void setSurfaceWidth(int surfaceWidth){
		this.surfaceWidth=surfaceWidth;
		content.setWidth(getSurfaceWidth());
	}

	public int getSurfaceWidth(){
		return surfaceWidth;
	}

	public void setSurfaceHeight(int surfaceHeight){
		this.surfaceHeight=surfaceHeight;
		content.setHeight(getSurfaceHeight());
	}

	public int getSurfaceHeight(){
		return surfaceHeight;
	}

	public void setContent(BaseView content){
		this.content=content;
	}

	public BaseView getContent(){
		return content;
	}
	
	public void initial(){
		mHolder=getHolder();
		mHolder.addCallback(this);
		mDrawThread=new DrawThread();
		mDrawThread.setFramTimeMillions(5);
		touchHelper=new TouchEventHelper();
		context=new MContext(getContext().getApplicationContext());
		context.setLoopThread(mDrawThread);
	}
	
	public MContext getGameContext(){
		return context;
	}
	
	public void setFlag(MLooperThread.TFlag flag){
		mDrawThread.setFlag(flag);
	}
	
	
	
	int[] timelist=new int[20];
	public void mDraw(Canvas c){
		c.drawColor(getClearColor());
		content.draw(c);
		if(drawDetails){
			Paint tp=new Paint();
			Paint fp=new Paint();
			tp.setARGB(255,255,255,255);
			tp.setTextSize(30);
			tp.setStrokeWidth(7);
			fp.setARGB(255,0,0,0);
			c.save();
			c.clipRect(c.getWidth()-200,c.getHeight()-200,c.getWidth(),c.getHeight());
			c.translate(c.getWidth()-200,c.getHeight()-200);
			c.drawARGB(255,0,0,0);
			c.drawText("DeltaTime: "+context.getFrameDeltaTime(),10,30,tp);
			c.drawLine(10,50,10+18*6,50,tp);
			c.drawLine(10,60,10+context.getFrameDeltaTime()*6,60,tp);
			for(int i=timelist.length-1;i>0;i--){
				timelist[i]=timelist[i-1];
			}
			timelist[0]=context.getFrameDeltaTime();
			for(int i=0;i<timelist.length;i++){
				c.drawLine(10,55+5*i,10+timelist[i]*6,55+5*i,tp);
			}
			c.restore();
		}
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
		//Log.v("surface","created");
	}

	@Override
	public void surfaceChanged(SurfaceHolder p1, int f, int w, int h)
	{
		// TODO: Implement this method
		//Log.v("surface","changed "+f+"|"+w+"|"+h);
		setSurfaceWidth(w);
		setSurfaceHeight(h);
		content.sized(w,h);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder p1)
	{
		// TODO: Implement this method
		if(mDrawThread!=null)mDrawThread.setFlag(MLooperThread.TFlag.Stop);
	}
	
	private void idraw(){
		try{
			mCanvas=mHolder.lockCanvas();
			if(mCanvas!=null)GameSurfaceView.this.mDraw(mCanvas);
		}finally{
			if(mCanvas!=null){
				mHolder.unlockCanvasAndPost(mCanvas);
			}
		}
	}
	
	public class DrawThread extends MLooperThread{
		
		@Override
		public void onFrame(int time){
			// TODO: Implement this method
			super.onFrame(time);
			idraw();
			if(time>RENDER_WARNING_TIME){
				Log.w("m_render","render cost time : "+time);
			}
		}
	}
	
	public class DrawThread_old extends Thread
	{
		public enum Flag{
			Drawing,Waiting,Stopped
		}
		
		public Flag flag;
		
		public boolean running;
		
		public DrawThread_old(){
			flag=Flag.Waiting;
			running=false;
		}
		
		long latestTime=0;
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
					latestTime=System.currentTimeMillis();
				}else{
					//正式绘图
					t=System.currentTimeMillis();
					latestTime=System.currentTimeMillis();
					
					if(System.currentTimeMillis()-t<4){
						try
						{
							sleep(2);
						}
						catch (InterruptedException e)
						{}
					}else{
						int dt=(int)(System.currentTimeMillis()-t);
						if(dt>30){
							
						}else if(dt<14){
							//try{
							//	sleep(14-dt);
							//}
							//catch(InterruptedException e){}
						}
					}
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
