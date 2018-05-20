package com.edplan.framework.ui.layout;

/**
 *描述组件布局朝向
 */
public class Orientation
{
	public static final int DIRECTION_LEFT_BITS=0;
	
	public static final int HORIZON_MASK=(1<<DIRECTION_LEFT_BITS)-1;
	
	public static final int VERTICAL_MASK=HORIZON_MASK<<DIRECTION_LEFT_BITS;

	public static final int DIRECTION_BIG2SMALL=1;

	public static final int DIRECTION_NONE=0;

	public static final int DIRECTION_SMALL2BIG=2;
	
	public static final int DIRECTION_L2R=DIRECTION_BIG2SMALL;

	public static final int DIRECTION_R2L=DIRECTION_SMALL2BIG;

	public static final int DIRECTION_NONE_HORIZON=DIRECTION_NONE;

	public static final int DIRECTION_T2B=DIRECTION_BIG2SMALL<<DIRECTION_LEFT_BITS;

	public static final int DIRECTION_B2T=DIRECTION_SMALL2BIG<<DIRECTION_LEFT_BITS;

	public static final int DIRECTION_NONE_VERTICAL=DIRECTION_NONE<<DIRECTION_LEFT_BITS;
	
	
}
