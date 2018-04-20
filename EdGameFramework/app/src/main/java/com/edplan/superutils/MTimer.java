package com.edplan.superutils;

public class MTimer
{
	public boolean hasInitial;
	
	public long startTime;
	
	public long nowTime;
	
	public double deltaTime;
	
	public double runnedTime;
	
	public MTimer(){
		hasInitial=false;
	}
	
	public boolean hasInitial(){
		return hasInitial;
	}
	
	public void initial(){
		initial(System.currentTimeMillis());
	}
	
	public void initial(long s){
		hasInitial=true;
		startTime=s;
		nowTime=s;
		deltaTime=0;
		runnedTime=0;
	}
	
	public long nowTime(){
		return nowTime;
	}
	
	public double getDeltaTime(){
		return deltaTime;
	}
	
	public void refresh(double _deltaTime){
		deltaTime=_deltaTime;
		nowTime+=_deltaTime;
		runnedTime+=_deltaTime;
	}
	
	public void refresh(){
		refresh((int)(System.currentTimeMillis()-nowTime));
	}
}
