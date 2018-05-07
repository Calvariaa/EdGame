package com.edplan.framework.ui.layout;

public class EdMeasureSpec{
	public static final int SHIFT_SIZE=32;
	public static final long SIZE_MASK=1<<SHIFT_SIZE-1;
	public static final long MODE_MASK=3<<SHIFT_SIZE;

	public static final int MODE_NONE=1;
	public static final int MODE_AT_MOST=2;
	public static final int MODE_DEFINEDED=3;


	public static long intToLongMode(int mode){
		return ((long)mode)<<SHIFT_SIZE;
	}

	public static long makeupMeasureSpec(float size,int mode){
		return intToLongMode(mode)|Float.floatToRawIntBits(size);
	}

	public static float getSize(long spec){
		return Float.intBitsToFloat((int)(spec&SIZE_MASK));
	}

	public static int getMode(long spec){
		return (int)((spec&MODE_MASK)<<SHIFT_SIZE);
	}

	public static long adjustSize(long raw,float add){
		return makeupMeasureSpec(getSize(raw)+add,getMode(raw));
	}

	public static long setSize(long raw,float size){
		return makeupMeasureSpec(size,getMode(raw));
	}
}
