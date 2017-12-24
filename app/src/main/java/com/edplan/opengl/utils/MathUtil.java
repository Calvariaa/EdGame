package com.edplan.opengl.utils;

public class MathUtil
{
	public static int log2(int i){
		if(i==1){
			return 0;
		}else{
			return 1+log2(i/2);
		}
	}
	
	public static int power(int i,int n){
		if(n==0){
			return 1;
		}else{
			return i*power(i,n-1);
		}
	}
}
