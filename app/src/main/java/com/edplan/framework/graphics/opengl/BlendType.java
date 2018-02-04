package com.edplan.framework.graphics.opengl;
import android.opengl.GLES20;

public enum BlendType
{
	Normal(
		GLES20.GL_ONE,
		GLES20.GL_ONE_MINUS_SRC_ALPHA,
		true),
	Additive(
		GLES20.GL_ONE,
		GLES20.GL_ONE,
		false)
	;
	public final int srcType;
	public final int dstType;
	public final boolean needPreMultiple;
	BlendType(int src,int dst,boolean prm){
		srcType=src;
		dstType=dst;
		needPreMultiple=prm;
	}
}
