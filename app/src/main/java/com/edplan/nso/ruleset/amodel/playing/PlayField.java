package com.edplan.nso.ruleset.amodel.playing;

/**
 *主要的游戏绘制，不包括各种ui，只绘制HitObject以及附加物件
 *基础大小为640x480，绘制时有(x,y)=(64,48)的padding
 *因此实际绘制时的canvas大小为512x384
 */
public class PlayField
{
	public static final float BASE_X=640;
	public static final float BASE_Y=480;
	public static final float PADDING_X=64;
	public static final float PADDING_Y=48;
	public static final float CANVAS_SIZE_X;
	public static final float CANVAS_SIZE_Y;
	static{
		CANVAS_SIZE_X=BASE_X-2*PADDING_X;
		CANVAS_SIZE_Y=BASE_Y-2*PADDING_Y;
	}
	
}
