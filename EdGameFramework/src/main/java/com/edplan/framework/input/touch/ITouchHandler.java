package com.edplan.framework.input.touch;

public interface ITouchHandler
{
	/**
	 *当一个点被点击时被调用
	 *@param x:点击点x坐标
	 *@param y:点击点y坐标
	 *@param pointer：点的id
	 *@param event:点击事件的上下文
	 */
	public boolean onDown(float x,float y,int pointer,TouchEvent event);
	
	/**
	 *一个点移动时调用
	 */
	public boolean onMove(float x,float y,int pointer,TouchEvent event);
	
	/**
	 *一个点抬起的时候被调用。这个方法不一定会被调用。
	 */
	public boolean onUp(float x,float y,int pointer,TouchEvent event);
	
	/**
	 *一个点操作被取消的时候被调用。这个方法不一定会被调用。
	 */
	public boolean onCancel(float x,float y,int pointer,TouchEvent event);
	
	/**
	 *当一个点被回收时调用，对于每一个pointer必定被调用
	 */
	public boolean onEnd(float x,float y,int pointer,TouchEvent event);
}
