package com.edplan.framework.input.manager;
import com.edplan.framework.input.EdMotionEvent;
import com.edplan.framework.ui.uiobjs.Area2D;

public class MotionManager<H extends IMotionEventHandler,K> implements IMotionManager<H,K>
{
	@Override
	public void unregisterHandler(H area) {
		// TODO: Implement this method
	}

	@Override
	public void unregisterHandler(K key) {
		// TODO: Implement this method
	}

	@Override
	public void registerHandler(H area,K key) {
		// TODO: Implement this method
	}

	@Override
	public void release() {
		// TODO: Implement this method
	}

	@Override
	public boolean postEvent(EdMotionEvent e) {
		// TODO: Implement this method
		return false;
	}
	
	
	private class HandlerNode{
		public K key;
		public H handler;
		
		
		
	}
}
