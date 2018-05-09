package com.edplan.framework.utils;

public class BitUtil
{
	public static boolean match(int raw,int matcher){
		return (raw&matcher)==matcher;
	}
}
