package com.edplan.framework.ui.animation;

public class ComplexAnimationBuilder
{
	
	/**
	 *进入分割队列，单独计算关键帧时间
	 */
	public ComplexAnimationBuilder beginQuery(){
		
		return this;
	}
	
	public ComplexAnimationBuilder endQuery(){
		
		return this;
	}
	
	//设置第一个动画，并以此动画开始时间为关键帧
	public ComplexAnimationBuilder first(AbstractAnimation anim){
		
		return this;
	}
	
	/**
	 *添加一个和当前关键帧动画平行进行的动画，可能在进入下一关键帧的时候仍在进行
	 *@param offset: 与关键帧开始时间的时间差
	 */
	public ComplexAnimationBuilder together(AbstractAnimation anim,double offset){
		
		return this;
	}
	
	/**
	 *添加一个在当前动画结束后offset ms后开始的动画，并平移关键帧到此动画开始时间
	 *@param offset: 与上一关键帧结束时间的时间差
	 */
	public ComplexAnimationBuilder then(AbstractAnimation anim,double offset){
		
		return this;
	}
	
	/**
	 *平移当前帧到当前动画结束位置
	 */
	public ComplexAnimationBuilder next(){
		
		return this;
	}
	
	/**
	 *平移关键帧
	 */
	public ComplexAnimationBuilder delay(double time){
		
		return this;
	}
}
