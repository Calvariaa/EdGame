package com.edplan.superutils.classes.advance;
import com.edplan.superutils.classes.MLooper;
import com.edplan.superutils.classes.SafeList;
import com.edplan.superutils.interfaces.Loopable;
import java.util.Iterator;
import com.edplan.superutils.interfaces.AbstractLooper;

public class RunnableHandler extends Loopable implements IRunnableHandler
{
	private AbstractLooper looper;
	
	private SafeList<DelayedRunnable> bufferedRunnables;
	
	private Loopable.Flag flag=Loopable.Flag.Run;
	
	public RunnableHandler(){
		bufferedRunnables=new SafeList<DelayedRunnable>();
	}
	
	@Override
	public void post(Runnable r,int delayMS){
		bufferedRunnables.add(new DelayedRunnable(r,delayMS));
	}
	
	@Override
	public void post(Runnable r){
		post(r,0);
	}
	
	public void stop(){
		flag=Loopable.Flag.Stop;
	}
	
	public void block(){
		flag=Loopable.Flag.Skip;
	}
	
	@Override
	public void setLooper(AbstractLooper lp) {
		// TODO: Implement this method
		this.looper=lp;
	}

	@Override
	public void onRemove() {
		// TODO: Implement this method
		bufferedRunnables.clear();
		flag=Loopable.Flag.Stop;
	}

	@Override
	public void onLoop(int deltaTime) {
		// TODO: Implement this method
		bufferedRunnables.startIterate();
		Iterator<DelayedRunnable> iter=bufferedRunnables.iterator();
		DelayedRunnable tmp;
		while(iter.hasNext()){
			tmp=iter.next();
			tmp.delay-=deltaTime;
			if(tmp.delay<=0){
				tmp.r.run();
				iter.remove();
			}
		}
		bufferedRunnables.endIterate();
	}

	@Override
	public Loopable.Flag getFlag() {
		// TODO: Implement this method
		return flag;
	}
	
	private class DelayedRunnable{
		public Runnable r;
		public int delay;
		
		public DelayedRunnable(Runnable r,int delay){
			this.r=r;
			this.delay=delay;
		}
	}

}
