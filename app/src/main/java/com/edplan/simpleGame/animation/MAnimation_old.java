package com.edplan.simpleGame.animation;
import com.edplan.superutils.interfaces.Loopable;

public class MAnimation_old{
	
	public int duration;

	public int deltaTime;

	public AnimaAdapter adapter;

	public AnimaCallback callback;

	public AnimaInterpolator interpolator;

	public AnimaThread thread;

	public MAnimation_old(){

	}

	//@Override
	public void onLoop(){
		// TODO: Implement this method
	}

	public MAnimation_old setDeltaTime(int deltaTime){
		this.deltaTime=deltaTime;
		return this;
	}

	public int getDeltaTime(){
		return deltaTime;
	}

	public void start(){
		thread=new AnimaThread();
		thread.start();
	}

	public void skip(){
		setFlag(Flag.SKIP);
	}
	
	public void setFlag(Flag f){
		if(thread!=null){
			thread.setFlag(f);
		}
	}

	public MAnimation_old setDuration(int duration){
		this.duration=duration;
		setDeltaTime(Math.min(5,duration/100));
		return this;
	}

	public int getDuration(){
		return duration;
	}

	public MAnimation_old setAdapter(AnimaAdapter adapter){
		this.adapter=adapter;
		return this;
	}

	public AnimaAdapter getAdapter(){
		return adapter;
	}

	public MAnimation_old setCallback(AnimaCallback callback){
		this.callback=callback;
		return this;
	}

	public AnimaCallback getCallback(){
		return callback;
	}

	public MAnimation_old setInterpolator(AnimaInterpolator interpolator){
		this.interpolator=interpolator;
		return this;
	}

	public AnimaInterpolator getInterpolator(){
		return interpolator;
	}

	public enum Flag{
		SKIP,STOP,PAUSE,RUN
		}

	public class AnimaThread extends Thread{

		Flag flag=Flag.RUN;

		public float progress=0;

		public int runnedTime;

		public long startTime;

		public long latestTime;

		public void setFlag(Flag flag){
			this.flag=flag;
		}

		public Flag getFlag(){
			return flag;
		}

		@Override
		public void run(){
			// TODO: Implement this method
			super.run();
			doAnimation();
		}

		private void doAnimation(){
			runnedTime=0;
			startTime=System.currentTimeMillis();
			latestTime=System.currentTimeMillis();
			if(callback!=null)callback.onStart();
			setProgress(0);
			while(true){
				switch(flag){
					case RUN:
						try{
							sleep(deltaTime);
							runnedTime=(int)(System.currentTimeMillis()-startTime);
							progress+=((float)(System.currentTimeMillis()-latestTime))/duration;
							latestTime=System.currentTimeMillis();
							if(progress>=1){
								progress=1;
								setProgress(progress);
								if(callback!=null){
									callback.onFinish();
									callback.onEnd();
								}
								return;
							}else{
								setProgress(progress);
							}
						}
						catch(InterruptedException e){}
						break;
					case SKIP:
						setProgress(1);
						if(callback!=null){
							callback.onFinish();
							callback.onEnd();
						}
						return;
					case STOP:
						if(callback!=null){
							callback.onStop(progress);
							callback.onEnd();
						}
						return;
					case PAUSE:
						try{
							sleep(10);
						}
						catch(InterruptedException e){}
						latestTime=System.currentTimeMillis();
						break;
				}
			}
		}

		private void setProgress(float p){
			if(adapter!=null){
				adapter.setProgress((interpolator!=null)?interpolator.getInterpolation(p):p);
			}
			if(callback!=null)callback.onProgress(p);
		}

	}
}
