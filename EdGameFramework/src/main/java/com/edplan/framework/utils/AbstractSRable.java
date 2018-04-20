package com.edplan.framework.utils;
import com.edplan.framework.interfaces.Copyable;
import java.util.Stack;
import com.edplan.framework.interfaces.Recycleable;

public abstract class AbstractSRable<T extends Copyable> implements Recycleable
{
	private Stack<T> saves;
	
	public T currentData;
	
	private int currentDataIndex=0;
	
	public abstract void onSave(T t);
	
	public abstract void onRestore(T now,T pre);
	
	public abstract T getDefData();
	
	public void initial(){
		saves=new Stack<T>();
		currentData=getDefData();
		currentDataIndex=0;
		//onSave(currentData);
	}
	
	public T getData(){
		return currentData;
	}
	
	public void setCurrentData(T t){
		currentData=t;
	}
	
	@SuppressWarnings({"unchecked"})
	public int save(){
		onSave(currentData);
		saves.push(currentData);
		currentData=(T)currentData.copy();
		currentDataIndex++;
		return currentDataIndex-1;
	}
	
	public void restore(){
		T popData=saves.pop();
		T pre=currentData;
		onRestore(popData,pre);
		currentData=popData;
		currentDataIndex--;
	}
	
	public void restoreToCount(int idx){
		while(idx>=0&&idx!=currentDataIndex){
			restore();
		}
	}

	@Override
	public void recycle() {
		// TODO: Implement this method
		if(saves!=null)saves.clear();
		saves=null;
	}
}