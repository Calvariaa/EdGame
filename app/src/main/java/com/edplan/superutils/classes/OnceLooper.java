package com.edplan.superutils.classes;
import com.edplan.superutils.interfaces.Loopable;
import com.edplan.superutils.interfaces.Loopable.Flag;

public abstract class OnceLooper implements Loopable,Runnable
{
	private Flag flag=Flag.Run;
	
	@Override
	public void onRecycle(){
		// TODO: Implement this method
	}

	@Override
	public void onLoop(int deltaTime){
		// TODO: Implement this method
		run();
		flag=Flag.Stop;
	}

	@Override
	public Loopable.Flag getFlag(){
		// TODO: Implement this method
		return flag;
	}

}
