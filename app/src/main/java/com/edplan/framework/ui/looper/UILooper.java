package com.edplan.framework.ui.looper;

import com.edplan.superutils.classes.MLooper;
import com.edplan.superutils.classes.advance.IRunnableHandler;
import com.edplan.superutils.classes.advance.RunnableHandler;
import com.edplan.superutils.interfaces.Loopable;

public class UILooper extends StepLooper implements IRunnableHandler 
{
	
	/**
	 *循环开始时最先执行，处理各种post的预处理
	 *所有会影响layout的设置应该都在这里进行，
	 *这个Loopable跑完了之后layout应该被确定了
	 */
	private RunnableHandler runnableHandler;

	//处理所有动画
	//private AnimationHandler;

	//绘制操作
	//private UIDrawer

	public UILooper(){
		runnableHandler=new RunnableHandler();
		//addLoopable(runnableHandler);
	}

	@Override
	public void post(Runnable r){
		runnableHandler.post(r);
	}

	@Override
	public void post(Runnable r,int delayMS) {
		// TODO: Implement this method
		runnableHandler.post(r,delayMS);
	}

}
