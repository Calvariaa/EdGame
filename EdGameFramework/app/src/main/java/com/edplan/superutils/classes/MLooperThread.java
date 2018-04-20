package com.edplan.superutils.classes;
import com.edplan.superutils.interfaces.Loopable;
import com.edplan.superutils.MTimer;
import java.util.List;
import java.util.ArrayList;

public class MLooperThread extends Thread
{

	private MLooper looper;
	
	private int framTimeMillions=17;
	
	private MTimer timer;
	
	private SafeList<Runnable> tasks;
	
	private TFlag flag;
	
	public MLooperThread(MLooper _looper,Loopable loophead){
		looper=_looper;
		_looper.addLoopable(loophead);
		initial();
	}
	
	public MLooperThread(MLooper _looper){
		looper=_looper;
		initial();
	}
	
	public MLooperThread(Loopable loophead){
		this(new MLooper(),loophead);
	}
	
	public MLooperThread(){
		this(new MLooper());
	}

	public void setFramTimeMillions(int framTimeMillions){
		this.framTimeMillions=framTimeMillions;
	}

	public int getFramTimeMillions(){
		return framTimeMillions;
	}
	
	private void initial(){
		timer=new MTimer();
		tasks=new SafeList<Runnable>();
		flag=TFlag.Waiting;
	}
	
	public MTimer getTimer(){
		return timer;
	}
	
	
	public void setFlag(TFlag flag){
		this.flag=flag;
	}

	public TFlag getFlag(){
		return flag;
	}
	
	public void setLooper(MLooper looper){
		this.looper=looper;
	}

	public MLooper getLooper(){
		return looper;
	}
	
	public void doTasks(){
		tasks.startIterate();
		for(Runnable r:tasks){
			r.run();
		}
		tasks.endIterate();
	}
	
	public void onFrame(double time){
		looper.loop(time);
	}
	
	public void onSleep(){
		try{
			sleep(
				(timer.getDeltaTime()>framTimeMillions)?
					0:(int)(framTimeMillions-timer.getDeltaTime())
			);
		}
		catch(InterruptedException e){}
	}
	
	public void postRunnable(Runnable r){
		tasks.add(r);
	}

	@Override
	public void start(){
		// TODO: Implement this method
		super.start();
		setFlag(TFlag.Running);
	}

	@Override
	public void run(){
		// TODO: Implement this method
		super.run();
		timer.initial();
		getLooper().prepare();
		w:while(true){
			timer.refresh();
			switch(getFlag()){
				case Waiting:
					onSleep();
					break;
				case Running:
					doTasks();
					if(timer.getDeltaTime()!=0){
						onFrame(timer.getDeltaTime());
					}
					onSleep();
					break;
				case Stop:
					break w;
			}
		}
	}
	


	public enum TFlag{
		Waiting,Running,Stop
	}
	
	
}
