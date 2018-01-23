package com.edplan.framework.ui.animation.precise;
import com.edplan.framework.ui.animation.AbstractAnimation;
import com.edplan.framework.ui.animation.LoopType;

/**
 *在PreciseTimeline上使用的动画，
 *适应音游环境下对帧与帧之间误差的调整
 *默认只能为LoopType.None
 */
public abstract class AbstractPreciseAnimation extends AbstractAnimation
{
	/**
	 *在Timeline上的开始时间，
	 *在动画实际开始的时候（对应的帧时间）会通过这个参数进行ms级别的调整
	 */
	public abstract int getStartTimeAtTimeline();
	
	/**
	 *默认只能为LoopType.None
	 */
	@Override
	public LoopType getLoopType() {
		// TODO: Implement this method
		return LoopType.None;
	}

	@Override
	public int getLoopCount() {
		// TODO: Implement this method
		return 0;
	}

	@Override
	public void addLoopCount() {
		// TODO: Implement this method
	}
}