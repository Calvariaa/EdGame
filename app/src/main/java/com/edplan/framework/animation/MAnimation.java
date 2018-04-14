package com.edplan.framework.animation;
import com.edplan.superutils.interfaces.Loopable;
import com.edplan.superutils.MTimer;
import com.edplan.superutils.interfaces.Loopable.Flag;
import com.edplan.framework.animation.interpolator.LinearInterpolator;
import com.edplan.superutils.classes.MLooperThread;
import com.edplan.superutils.classes.MLooper;
import com.edplan.framework.interfaces.Setter;
import com.edplan.superutils.interfaces.AbstractLooper;

public class MAnimation extends Loopable
{
	private AnimaInterpolator interpolator;
	
	private Setter<Float> setter;
	
	private Callback callback;
	
	private boolean hasFinished=false;
	
	private boolean hasStart=false;
	
	private float callbackRate=0;

	private float nextcallback=0;
	
	private float progress;
	
	private double timepassed;

	private double duration;
	
	public MAnimation(){
		initial();
	}
	
	public MAnimation(Setter<Float> setter){
		this();
		this.setter=setter;
	}

	public MAnimation setInterpolator(AnimaInterpolator interpolator){
		this.interpolator=interpolator;
		return this;
	}

	public AnimaInterpolator getInterpolator(){
		return interpolator;
	}
	
	public boolean ifHasStart(){
		return hasStart;
	}

	public MAnimation setCallbackRate(float callbackRate){
		this.callbackRate=callbackRate;
		this.nextcallback=callbackRate;
		return this;
	}

	public float getCallbackRate(){
		return callbackRate;
	}
	
	public MAnimation runOnLoopThread(MLooperThread t){
		return runOnLooper(t.getLooper());
	}
	
	public MAnimation runOnLooper(MLooper l){
		l.addLoopable(this);
		return this;
	}
	
	public void creatThreadAndRun(){
		MLooperThread t=new MLooperThread();
		t.start();
		runOnLoopThread(t);
	}
	
	public void stop(){
		setFlag(Flag.Stop);
	}
	
	public double getTimepassed(){
		return timepassed;
	}

	public MAnimation setDuration(double duration){
		this.duration=duration;
		return this;
	}

	public double getDuration(){
		return duration;
	}

	private void setProgress(float progress){
		this.progress=progress;
		setter.set(interpolator.getInterpolation(getProgress()));
	}

	public float getProgress(){
		return progress;
	}

	public MAnimation setCallback(Callback callback){
		this.callback=callback;
		return this;
	}

	public Callback getCallback(){
		return callback;
	}

	private void initial(){
		setFlag(Flag.Skip);
	}

	public void checkProperty(){
		if(setter==null){
			throw new NullPointerException("MAnimation.setter shouldn't be null");
		}else if(getLooper()==null){
			throw new NullPointerException("MAnimation.looper shouldn't be null");
		}else if(duration<=0){
			throw new IllegalArgumentException("MAnimation.duration should >0");
		}else{
			if(interpolator==null){
				interpolator=LinearInterpolator.getDefInterpolator();
			}
		}
	}

	public MAnimation setSetter(Setter<Float> setter){
		this.setter=setter;
		return this;
	}

	public Setter<Float> getSetter(){
		return setter;
	}

	public void start(){
		checkProperty();
		setFlag(Flag.Run);
	}
	
	protected void onProgress(){
		if(callback!=null){
			callback.onProgress(this);
		}
	}
	
	protected void onStart(){
		if(callback!=null){
			callback.onStart(this);
		}
	}
	
	protected void onEnd(){
		if(callback!=null){
			callback.onEnd(this);
		}
	}
	
	protected void onFinish(){
		if(callback!=null){
			callback.onFinnish(this);
		}
	}
	
	@Override
	public void onLoop(double deltaTime){
		// TODO: Implement this method
		if(!ifHasStart()){
			hasStart=true;
			onStart();
		}
		timepassed+=deltaTime;
		if(timepassed>=duration){
			timepassed=duration;
		}
		setProgress((float)(timepassed/duration));
		
		if(callbackRate==0){
			onProgress();
		}else if(callbackRate<0){
			
		}else{
			if(getProgress()>nextcallback){
				onProgress();
				while(getProgress()>nextcallback){
					nextcallback+=callbackRate;
				}
			}
		}
		
		if(timepassed>=duration){
			//anima end
			hasFinished=true;
			setFlag(Flag.Stop);
			onFinish();
			onEnd();
		}
	}


	@Override
	public void onRemove(){
		// TODO: Implement this method
		if(!hasFinished){
			onEnd();
		}
	}
	
	public MAnimation copy(){
		MAnimation anim=new MAnimation(getSetter());
		anim.setDuration(getDuration());
		anim.setInterpolator(getInterpolator());
		return anim;
	}
	
	public interface Callback{
		public void onStart(MAnimation a);
		
		public void onProgress(MAnimation a);
		
		public void onFinnish(MAnimation a);
		
		public void onEnd(MAnimation a);
	}
}
