package com.edplan.framework.input.manager;
import com.edplan.framework.input.EdMotionEvent;

public interface IMotionManager<H extends IMotionEventHandler,K>
{
	public void unregisterHandler(H area);
	
	public void unregisterHandler(K key);
	
	/**
	 *注册一个区域
	 */
	public void registerHandler(H area,K key);
	
	/**
	 *释放所有的控制
	 */
	public void release();
	
	public boolean postEvent(EdMotionEvent e);
}
