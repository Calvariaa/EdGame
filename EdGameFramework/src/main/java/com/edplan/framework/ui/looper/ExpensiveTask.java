package com.edplan.framework.ui.looper;
import com.edplan.framework.MContext;

public class ExpensiveTask
{
	private Runnable task;
	
	private MContext context;
	
	private boolean needNotify=false;
	
	public ExpensiveTask(MContext c,Runnable task){
		this.task=task;
		this.context=c;
	}

	public boolean isNeedNotify(){
		return needNotify;
	}

	public void setTask(Runnable task){
		this.task=task;
	}

	public Runnable getTask(){
		return task;
	}
	
	
	public void startAndWait() throws InterruptedException{
		needNotify=true;
		synchronized(this){
			post();
			wait();
		}
	}
	
	public void start(){
		needNotify=false;
		post();
	}
	
	protected void post(){
		context.getUiLooper().addExpensiveTask(this);
	}
	
}
