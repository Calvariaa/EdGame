package com.edplan.superutils.classes;
import com.edplan.superutils.MTimer;
import com.edplan.superutils.interfaces.AbstractLooper;
import com.edplan.superutils.interfaces.Loopable;
import java.util.ArrayList;
import java.util.List;

public class MLooper implements AbstractLooper
{
	private List<Loopable> loopables;
	
	private boolean isIterating=false;
	
	private List<Loopable> removeList;
	
	private List<Loopable> addList;
	
	private MTimer timer;
	
	public MLooper(){
		loopables=new ArrayList<Loopable>();
		removeList=new ArrayList<Loopable>();
		addList=new ArrayList<Loopable>();
		timer=new MTimer();
	}

	public void setTimer(MTimer timer){
		this.timer=timer;
	}

	public MTimer getTimer(){
		return timer;
	}

	@Override
	public void prepare(){
		// TODO: Implement this method
		getTimer().initial();
	}

	@Override
	public void addLoopable(Loopable l){
		// TODO: Implement this method
		l.setLooper(this);
		if(isIterating){
			addList.add(l);
		}else{
			loopables.add(l);
		}
	}
	
	@Override
	public void loop(int deltaTime){
		timer.refresh(deltaTime);
		for(Loopable l:addList){
			loopables.add(l);
		}
		addList.clear();
		
		isIterating=true;
		for(Loopable l:loopables){
			switch(l.getFlag()){
				case Run:
					l.onLoop(timer.getDeltaTime());
					break;
				case Skip:
					break;
				case Stop:
					removeList.add(l);
					break;
				default:
					throw new NullPointerException("Loopable.getFlag() can't return null");
			}
		}
		isIterating=false;
		
		for(Loopable l:removeList){
			l.onRecycle();
			loopables.remove(l);
		}
		removeList.clear();
	}
}
