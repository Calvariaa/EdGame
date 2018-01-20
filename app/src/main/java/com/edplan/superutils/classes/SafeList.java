package com.edplan.superutils.classes;
import java.util.ArrayList;
import java.util.Iterator;

public class SafeList<T> extends ArrayList<T>
{
	private ArrayList<T> bufferList;
	
	private boolean iterating=false;
	
	public SafeList(){
		super();
		bufferList=new ArrayList<T>();
	}

	@Override
	public boolean add(T e){
		// TODO: Implement this method
		if(iterating){
			return bufferList.add(e);
		}else{
			return super.add(e);
		}
	}
	
	public void startIterate(){
		iterating=true;
	}
	
	public void endIterate(){
		iterating=false;
		for(T t:bufferList){
			super.add(t);
		}
		bufferList.clear();
	}
}
