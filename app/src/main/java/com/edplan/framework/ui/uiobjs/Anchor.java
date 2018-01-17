package com.edplan.framework.ui.uiobjs;

import com.edplan.framework.math.Vec2;

public class Anchor 
{
	public static final int FILL_PARENT=0x00000001;
	
	public static final int WRAP_CONTEXT=0x00000002;
	
	public static final int MASK_LAYER_OPTION=0x0000000F;
	
	private int stateX=WRAP_CONTEXT;
	
	private int stateY=WRAP_CONTEXT;
	
	public int getLayoutOptionX(){
		return maskLayoutOption(stateX);
	}
	
	public int getLayoutOptionY(){
		return maskLayoutOption(stateY);
	}
	
	private int maskLayoutOption(int s){
		return s&MASK_LAYER_OPTION;
	}
	
	
}
