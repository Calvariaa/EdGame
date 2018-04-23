package com.edplan.framework;

public class Framework
{
	/**
	 *获取相对的精确时间
	 */
	public static double relativePreciseTimeMillion(){
		return System.nanoTime()/1000000d;
	}
	
	public static long absoluteTimeMillion(){
		return System.currentTimeMillis();
	}
}
