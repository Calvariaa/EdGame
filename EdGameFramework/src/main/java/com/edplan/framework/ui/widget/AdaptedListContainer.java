package com.edplan.framework.ui.widget;
import com.edplan.framework.MContext;
import com.edplan.framework.ui.layout.Orientation;

public class AdaptedListContainer extends ScrollContainer
{
	public AdaptedListContainer(MContext c){
		super(c);
		setOrientation(Orientation.DIRECTION_T2B);
	}
	
	
}
