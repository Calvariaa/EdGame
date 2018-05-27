package com.edplan.osulab.ui.scenes;
import com.edplan.framework.ui.widget.FrameContainer;
import com.edplan.framework.MContext;

public class Scenes extends FrameContainer
{
	
	
	public Scenes(MContext c){
		super(c);
	}
	
	public BaseScene getCurrentScene(){
		return (BaseScene)getCurrentPage();
	}
	
}
